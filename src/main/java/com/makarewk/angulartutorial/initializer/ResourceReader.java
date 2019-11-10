package com.makarewk.angulartutorial.initializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ResourceReader {

    @Value("classpath:products.json")
    private  Resource product;

    public Resource getProduct() {
        return product;
    }
}
