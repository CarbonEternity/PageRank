package com.popova.service.page;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class PageContentLoaderService {

    private final int TIMEOUT_SEC = 3;
    private final RequestConfig config = RequestConfig.custom()
            .setConnectTimeout(1000 * TIMEOUT_SEC)
            .setConnectionRequestTimeout(1000 * TIMEOUT_SEC)
            .setSocketTimeout(1000 * TIMEOUT_SEC).build();
    private final CloseableHttpClient httpClient = HttpClientBuilder
            .create()
            .setDefaultRequestConfig(config)
            .build();

    public String loadPageContent(String startUrl) {
        HttpGet request = new HttpGet(startUrl);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity httpEntity = response.getEntity();
            InputStream inputStream = httpEntity.getContent();

            return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
