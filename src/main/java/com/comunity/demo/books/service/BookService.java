package com.comunity.demo.books.service;

import com.comunity.demo.books.config.BackendConfig;
import com.comunity.demo.books.view.model.Book;
import com.comunity.demo.books.view.model.BookDto;
import com.comunity.demo.books.view.model.SupportedLanguagesDto;
import com.comunity.demo.books.view.model.UpdateBookDto;
import com.comunity.demo.books.view.model.UpdateLanguagesDto;
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

  public Mono<BookDto> updateBook(Long id, Book book) {
    return client.put()
        .uri(uri -> uri.path(config.getUpdateBookEndpoint()).build(id))
        .bodyValue(UpdateBookDto.builder()
            .name(book.getName())
            .author(book.getAuthor())
            .publisher(book.getPublisher())
            .originalLanguageId(book.getOriginalLanguage().getId())
            .isPublished(book.isPublished())
            .isInternational(book.isInternational())
            .build())
        .retrieve()
        .bodyToMono(BookDto.class);
  }

  public Mono<BookDto> updateSupportedLanguages(Long id, List<Long> languages) {
    return client.put()
        .uri(uri -> uri.path(config.getUpdateSupportedLanguagesEndpoint()).build(id))
        .bodyValue(UpdateLanguagesDto.builder()
            .languageIds(languages)
            .build())
        .retrieve()
        .bodyToMono(BookDto.class);
  }
}
