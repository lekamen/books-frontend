package com.comunity.demo.books.view.books.model;

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
public class BookSupportedLanguage {

  Language language;
  boolean enabled;

  public String getName() {
    return language != null ? language.getName() : null;
  }

  public String getCode() {
    return language != null ? language.getCode() : null;
  }

  public Long getId() {
    return language != null ? language.getId() : null;
  }

  public static BookSupportedLanguage from(Language language, boolean enabled) {
    return BookSupportedLanguage.builder()
        .language(language)
        .enabled(enabled)
        .build();
  }

  public BookSupportedLanguage copy() {
    return BookSupportedLanguage.builder()
        .language(language.copy())
        .enabled(enabled)
        .build();
  }
}
