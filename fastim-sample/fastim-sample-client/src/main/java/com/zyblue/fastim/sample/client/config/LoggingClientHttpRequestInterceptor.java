package com.zyblue.fastim.sample.client.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LoggingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(LoggingClientHttpRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        response(response);
        return response;    }

    private void request(HttpRequest request, byte[] body) {
        logger.info("===========================request begin================================================");
        logger.info("URI         : {}", request.getURI());
        logger.info("Method      : {}", request.getMethod());
        logger.info("Headers     : {}", request.getHeaders());
        logger.info("Request body: {}", new String(body, StandardCharsets.UTF_8));
        logger.info("==========================request end================================================");
    }

    private void response(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }
        }
        logger.info("============================response begin==========================================");
        logger.info("Status code  : {}", response.getStatusCode());
        logger.info("Status text  : {}", response.getStatusText());
        logger.info("Headers      : {}", response.getHeaders());
        // WARNING: comment out in production to improve performance
        logger.info("Response body: {}", inputStringBuilder.toString());
        logger.info("=======================response end=================================================");
    }
}
