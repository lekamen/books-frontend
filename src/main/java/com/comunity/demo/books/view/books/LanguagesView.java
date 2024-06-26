package com.comunity.demo.books.view.books;

import com.comunity.demo.books.service.LanguageService;
import com.comunity.demo.books.view.books.model.LanguageDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

@Route(value = "languages", layout = BooksMainLayout.class)
@PageTitle("Languages")
@Slf4j
public class LanguagesView extends VerticalLayout {

  public LanguagesView(LanguageService languageService) {
    setSizeFull();
    Grid<LanguageDto> grid = new Grid<>();
    grid.setAllRowsVisible(true);
    grid.addColumn(LanguageDto::name).setHeader("Language");
    grid.addColumn(LanguageDto::code).setHeader("Code");
    add(grid);

    // blocking way
    grid.setItems(languageService.getLanguages().block());
  }
}
