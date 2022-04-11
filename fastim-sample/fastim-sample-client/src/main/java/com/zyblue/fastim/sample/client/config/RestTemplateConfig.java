package com.zyblue.fastim.sample.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfig {

    @Value("${http.logging.switch}")
    private boolean logging;

    @Value("${http.read.timeout}")
    private int read;

    @Value("${http.connect.timeout}")
    private int connect;

    private class NoOpRestTemplateCustomer implements RestTemplateCustomizer {
        @Override
        public void customize(RestTemplate restTemplate) {
            restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(simpleClientHttpRequestFactory()));
            restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
                @Override
                public void handleError(@NonNull ClientHttpResponse response) throws IOException {
                }
            });
            if(logging){
                restTemplate.getInterceptors().add(new LoggingClientHttpRequestInterceptor());
            }
        }
    }

    @Bean
    public RestTemplate noOpRestTemplate(RestTemplateBuilder restTemplateBuilder) throws Exception {
        return restTemplateBuilder.customizers(new NoOpRestTemplateCustomer()).build();
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(read);
        factory.setConnectTimeout(connect);
        return factory;
    }
}
