package com.example.catalog.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Runnable smoke-test host for the generated Catalog HTTP Interface client. */
@SpringBootApplication
public class CatalogConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogConsumerApplication.class, args);
    }
}
