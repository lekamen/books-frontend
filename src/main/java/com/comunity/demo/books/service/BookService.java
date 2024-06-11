package com.comunity.demo.books.service;

import com.comunity.demo.books.config.BackendConfig;
import com.comunity.demo.books.view.model.BookDto;
import com.comunity.demo.books.view.model.SupportedLanguagesDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BookService {

  private final WebClient client;
  private final BackendConfig config;

  private final ParameterizedTypeReference<List<BookDto>> booksTypeReference;

  public BookService(BackendConfig config, WebClient backendWebClient) {
    this.client = backendWebClient;
    this.config = config;

    booksTypeReference = new ParameterizedTypeReference<>() {};
  }

  //TODO: lazy loading?
  public Mono<List<BookDto>> getBooks() {
    return client.get()
        .uri(config.getBooksEndpoint())
        .retrieve()
        .bodyToMono(booksTypeReference);
  }

  public Mono<BookDto> getById(Long id) {
    return client.get()
        .uri(uri -> uri.path(config.getBookByIdEndpoint()).build(id))
        .retrieve()
        .bodyToMono(BookDto.class);
  }

  public Mono<SupportedLanguagesDto> getSupportedLanguages(Long id) {
    return client.get()
        .uri(uri -> uri.path(config.getSupportedLanguagesEndpoint()).build(id))
        .retrieve()
        .bodyToMono(SupportedLanguagesDto.class);
  }
}
