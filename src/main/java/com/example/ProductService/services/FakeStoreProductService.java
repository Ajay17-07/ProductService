package com.example.ProductService.services;


import com.example.ProductService.clients.IClientProductDto;
import com.example.ProductService.clients.fakestore.dto.FakeStoreProductDto;
import com.example.ProductService.clients.fakestore.dto.client.FakeStoreClient;
import com.example.ProductService.dtos.ProductDto;
import com.example.ProductService.dtos.SendEmailMessageDto;
import com.example.ProductService.models.Categories;
import com.example.ProductService.models.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    private RestTemplateBuilder restTemplateBuilder;
    private FakeStoreClient fakeStoreClient;
    private RedisTemplate<String, Long> redisTemplate;
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;

    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder, FakeStoreClient fakeStoreClient,
                                   RedisTemplate<String, Long> redisTemplate, KafkaTemplate<String, String> kafkaTemplate,
                                   ObjectMapper objectMapper) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreClient = fakeStoreClient;
        this.redisTemplate  = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }
    @Override
    public List<Product> getAllProducts(){
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        ResponseEntity<ProductDto[]> productDtos = restTemplate.getForEntity("https://fakestoreapi.com/products",ProductDto[].class);
//        //here we use array instead of list for productDto as generics cant identify List<>
        List<Product> productList = new ArrayList<>();
        List<FakeStoreProductDto> fakeStoreProductDtos = fakeStoreClient.getAllProducts();

        for(FakeStoreProductDto productDto : fakeStoreProductDtos){
            Product product = new Product();
            product.setId(productDto.getId());
            product.setTitle(productDto.getTitle());
            product.setPrice(productDto.getPrice());
            Categories category = new Categories();
            category.setName(productDto.getCategory());
            product.setCategory(category);
            product.setImageUrl(productDto.getImage());
            product.setDescription(productDto.getDescription());
            productList.add(product);
        }
        return productList;
    }


    @Override
    public Product getSingleProduct(Long productId) {

        SendEmailMessageDto message = new SendEmailMessageDto();
        message.setFrom("deepajay66@gmail.com");
        message.setTo("srimickey0511@gmail.com");
        message.setSubject("I am Batman");
        message.setBody("Hey Jondya!!!...This is Batman I will miss You lot in month of feb");
        try{
            kafkaTemplate.send(
                    "sendEmail",
                    objectMapper.writeValueAsString(message)
            );
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if(redisTemplate.opsForHash().hasKey("PRODUCTS", productId)){
            return (Product) redisTemplate.opsForHash().get("PRODUCTS", productId);
        }
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> productDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}",
                                                    FakeStoreProductDto.class, productId);
        //ProductDto productDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}",ProductDto.class,productId).getBody();
        Product product = getProduct(productDto.getBody());
        redisTemplate.opsForHash().put("PRODUCTS", productId, product);


        return product;
    }

    @Override
    public Product addNewProduct(IClientProductDto productDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.postForEntity("https://fakestoreapi.com/products",productDto, ProductDto.class);
        Product product = getProduct((FakeStoreProductDto) productDto);

        return product;
    }

    @Override
    public Product addNewProduct(Product productDto) {
        return null;
    }

    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request,
                                                   Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();
        RequestCallback requestCallback =restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }


    @Override
    public Product updateProduct(Long productId, Product product){
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(HttpMethod.PATCH,
                "https://fakestoreapi.com/products/{id}",fakeStoreProductDto,FakeStoreProductDto.class,productId);

        FakeStoreProductDto fakeStoreProductDto1 = fakeStoreProductDtoResponseEntity.getBody();
        return getProduct(fakeStoreProductDto1);
    }


    @Override
    public String deleteProduct(Long productId){
        return null;
    }

    private static Product getProduct(FakeStoreProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Categories category = new Categories();
        category.setName(productDto.getCategory());
        product.setCategory(category);
        product.setImageUrl(productDto.getImage());
        product.setDescription(productDto.getDescription());
        return product;
    }
}
