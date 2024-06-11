package com.comunity.demo.books.view.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = false)
@Builder(builderClassName = "Builder", toBuilder = true)
@EqualsAndHashCode
public class Book {

  Long id;
  String name;
  String author;
  String publisher;
  boolean isPublished;
  boolean isInternational;

  public static Book fromDto(BookDto dto) {
    return Book.builder()
        .id(dto.id())
        .name(dto.name())
        .author(dto.author())
        .publisher(dto.publisher())
        .isPublished(dto.isPublished())
        .isInternational(dto.isInternational())
        .build();
  }
}
