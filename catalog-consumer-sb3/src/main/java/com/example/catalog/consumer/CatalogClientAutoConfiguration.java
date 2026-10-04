package com.example.catalog.consumer;

import com.example.catalog.api.DefaultApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/** Creates the generated Spring HTTP Interface proxy for the catalog API. */
@AutoConfiguration
public class CatalogClientAutoConfiguration {

    @Bean
    DefaultApi catalogApi(
            RestClient.Builder restClientBuilder,
            @Value("${catalog.client.base-url:http://localhost:8080}") String baseUrl) {
        RestClient restClient = restClientBuilder.baseUrl(baseUrl).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(DefaultApi.class);
    }
}
