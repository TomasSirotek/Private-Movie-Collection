package com.movie_collection.bll.services.api;


import com.movie_collection.dal.dao.MovieDAO;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiKeyRequestInterceptor implements RequestInterceptor {

    private static final String CONFIG_FILE = "/config.cfg";
    private final String apiKey;
    Logger logger = LoggerFactory.getLogger(ApiKeyRequestInterceptor.class);

    public ApiKeyRequestInterceptor() {
        this.apiKey = tryLoadConfig();
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.query("apikey", apiKey);
    }

    /**
     * this method load the api key from config file in resources where needs to be placed
     */
    private String tryLoadConfig() {
        Properties props = new Properties();
        try (InputStream input = getClass().getResourceAsStream(CONFIG_FILE);) {
            props.load(input);
            return props.getProperty("API_KEY");
        } catch (IOException e) {
            logger.error("Could not load API key. You are probably missing config file (best guess)" + e);
        }
        return "";
    }
}
