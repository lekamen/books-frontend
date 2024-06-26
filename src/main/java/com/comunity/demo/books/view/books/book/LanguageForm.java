package com.comunity.demo.books.view.books.book;

import com.comunity.demo.books.service.BookService;
import com.comunity.demo.books.service.LanguageService;
import com.comunity.demo.books.view.books.model.BookSupportedLanguage;
import com.comunity.demo.books.view.books.model.Language;
import com.comunity.demo.books.view.books.model.LanguageDto;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class LanguageForm extends VerticalLayout {

  private final Grid<BookSupportedLanguage> grid;
  private final Button saveChanges;

  private Language originalLanguage;
  private List<BookSupportedLanguage> initialList = new ArrayList<>();
  private List<BookSupportedLanguage> list = new ArrayList<>();

  private List<Language> allLanguages;
  private final BookService bookService;
  private final LanguageService languageService;

  private Long id;

  public LanguageForm(BookService bookService, LanguageService languageService) {
    this.bookService = bookService;
    this.languageService = languageService;


    grid = new Grid<>();
    grid.setSelectionMode(SelectionMode.NONE);
    grid.setAllRowsVisible(true);

    grid.addColumn(BookSupportedLanguage::getName).setHeader("Language");
    grid.addColumn(BookSupportedLanguage::getCode).setHeader("Code");
    grid.addComponentColumn(this::createCheckbox).setHeader("Enabled");

    add(new H4("Supported languages"));
    add(grid);

    saveChanges = new Button("Save changes");
    saveChanges.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
    saveChanges.addClickListener(this::saveLanguages);

    add(saveChanges);
  }

  public void setLanguages(Long id, Language originalLanguage) {
    this.id = id;
    this.originalLanguage = originalLanguage;

    Mono.zip(
        languageService.getLanguages(),
        bookService.getSupportedLanguages(id))
        .subscribe(tuple -> getUI().ifPresent(ui -> ui.access(() -> {
          this.allLanguages = tuple.getT1().stream().map(Language::fromDto).toList();
          setLanguages(tuple.getT2().supportedLanguages());
        })));
  }

  private void setLanguages(List<LanguageDto> supportedLanguages) {
    List<BookSupportedLanguage> bookSupportedLanguages = new ArrayList<>(allLanguages.stream()
        .map(language -> BookSupportedLanguage.from(language,
            supportedLanguages.stream().anyMatch(supportedLanguage -> supportedLanguage.id().equals(language.getId()))))
        .toList());
    bookSupportedLanguages.removeIf(bsl -> bsl.getId().equals(originalLanguage.getId()));
    grid.setItems(bookSupportedLanguages);

    this.initialList = bookSupportedLanguages.stream().map(BookSupportedLanguage::copy).toList();
    this.list = bookSupportedLanguages.stream().map(BookSupportedLanguage::copy).toList();
    saveChanges.setEnabled(false);
  }

  private void saveLanguages(ClickEvent<Button> event) {
    List<Long> languages = list.stream()
        .filter(BookSupportedLanguage::isEnabled)
        .map(BookSupportedLanguage::getId)
        .toList();

    bookService.updateSupportedLanguages(id, languages)
        .subscribe(response -> getUI().ifPresent(ui -> ui.access(this::handleSuccess)),
            this::handleError);
  }

  private void handleSuccess() {
    Notification.show("Successfully updated languages").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    this.initialList = list.stream().map(BookSupportedLanguage::copy).toList();
    saveChanges.setEnabled(!listsEqual());
  }

  private void handleError(Throwable e) {
    log.error("Error while trying to update supported languages for book {}, message: {}", id, e.getMessage(), e);

    getUI().ifPresent(ui -> ui.access(() ->
        Notification.show("Failed to update languages, refreshing state").addThemeVariants(NotificationVariant.LUMO_ERROR)));
    setLanguages(id, originalLanguage);
  }

  private Checkbox createCheckbox(BookSupportedLanguage language) {
    Checkbox checkbox = new Checkbox(language.isEnabled());

    checkbox.addClickListener(event -> {
      boolean enabled = checkbox.getOptionalValue().orElse(false);

      updateList(language.getId(), enabled);
      saveChanges.setEnabled(!listsEqual());
    });

    return checkbox;
  }

  private void updateList(Long id, boolean enabled) {
    BookSupportedLanguage languageForUpdate = list.stream()
        .filter(language -> language.getId().equals(id))
        .findFirst()
        .orElseThrow(RuntimeException::new);

    languageForUpdate.setEnabled(enabled);
  }

  private boolean listsEqual() {
    return list.stream()
        .allMatch(language -> initialList.stream()
            .filter(initialLanguage -> initialLanguage.getId().equals(language.getId()))
            .map(initialLanguage -> initialLanguage.isEnabled() == language.isEnabled())
            .findFirst()
            .orElse(false));
  }
}