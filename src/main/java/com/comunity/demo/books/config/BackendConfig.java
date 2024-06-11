package com.comunity.demo.books.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ConfigurationProperties(prefix = "books")
@Getter
@Setter
public class BackendConfig {

  private String baseUrl;
  private String languagesEndpoint;
  private String booksEndpoint;
  private String bookByIdEndpoint;
  private String supportedLanguagesEndpoint;
  private String updateSupportedLanguagesEndpoint;
  private String createBookEndpoint;
  private String updateBookEndpoint;

  @Bean
  public WebClient backendWebClient() {
    return WebClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }
}
