package com.siddhartha.Alumini_Searcher.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;


@Configuration
@ConfigurationProperties(prefix = "phantom")
public class phantomproperties {

    private String baseUrl;
    private String apiKey;
    private String agentId;

    // getters & setters
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }
}
