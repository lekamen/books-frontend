package com.comunity.demo.books.service;

import com.comunity.demo.books.config.BackendConfig;
import com.comunity.demo.books.view.books.model.LanguageDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class LanguageService {

  private final WebClient client;
  private final BackendConfig config;

  private final ParameterizedTypeReference<List<LanguageDto>> languagesTypeReference;

  public LanguageService(BackendConfig config, WebClient backendWebClient) {
    this.client = backendWebClient;
    this.config = config;

    languagesTypeReference = new ParameterizedTypeReference<>() {};
  }

  public Mono<List<LanguageDto>> getLanguages() {
    return client.get()
        .uri(config.getLanguagesEndpoint())
        .retrieve()
        .bodyToMono(languagesTypeReference);
  }
}
