package com.comunity.demo.books.view.books.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@AllArgsConstructor
@Accessors(fluent = true, chain = false)
@Builder(builderClassName = "Builder", toBuilder = true)
@EqualsAndHashCode
public class BookDto {

  @JsonProperty("id")
  Long id;

  @JsonProperty("name")
  String name;

  @JsonProperty("author")
  String author;

  @JsonProperty("publisher")
  String publisher;

  @JsonProperty("originalLanguage")
  LanguageDto originalLanguage;

  @JsonProperty("isPublished")
  boolean isPublished;

  @JsonProperty("isInternational")
  boolean isInternational;
}
