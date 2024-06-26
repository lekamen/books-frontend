package com.comunity.demo.books.view.books.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@AllArgsConstructor
@Accessors(fluent = true, chain = false)
@Builder(builderClassName = "Builder", toBuilder = true)
public class SupportedLanguagesDto {

  @JsonProperty("supportedLanguages")
  List<LanguageDto> supportedLanguages;
}
