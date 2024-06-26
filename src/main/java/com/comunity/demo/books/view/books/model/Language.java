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
public class Language {

  Long id;
  String name;
  String code;

  public static Language fromDto(LanguageDto dto) {
    return Language.builder()
        .id(dto.id())
        .name(dto.name())
        .code(dto.code())
        .build();
  }

  public Language copy() {
    return Language.builder()
        .id(id)
        .name(name)
        .code(code)
        .build();
  }
}
