package com.movie_collection.bll.services.api;


import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApiKeyRequestInterceptor implements RequestInterceptor {

    private final String apiKey;
    private static final String CONFIG_FILE = "config.cfg";
    public ApiKeyRequestInterceptor() {
        this.apiKey = tryLoadConfig();
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.query("apikey", apiKey);
    }

    /**
     * Getting the secrets to run API
     */
    private String tryLoadConfig(){
        Properties props = new Properties();
        try {
            props.load(new FileReader(CONFIG_FILE));
            return props.getProperty("API_KEY");
        } catch (IOException e) {
           throw new RuntimeException(e); // add logger
        }
    }
}
