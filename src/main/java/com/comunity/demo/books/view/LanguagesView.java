package com.comunity.demo.books.view;

import com.comunity.demo.books.service.LanguageService;
import com.comunity.demo.books.view.model.LanguageDto;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

@Route(value = "languages", layout = MainLayout.class)
@PageTitle("Languages")
@CssImport("./styles/highlight.css")
@Slf4j
public class LanguagesView extends VerticalLayout {

  public LanguagesView(LanguageService languageService) {
    setSizeFull();
    Grid<LanguageDto> grid = new Grid<>();
    grid.setAllRowsVisible(true);
    grid.addColumn(LanguageDto::name).setHeader("Language");
    grid.addColumn(LanguageDto::code).setHeader("Code");
    add(grid);

    //TODO: try blocking way also for demonstration
    languageService.getLanguages()
        .subscribe(languages -> getUI().ifPresent(ui -> ui.access(() -> grid.setItems(languages))));
  }
}
