package com.comunity.demo.books.view.book;

import com.comunity.demo.books.exception.ValidationException;
import com.comunity.demo.books.service.BookService;
import com.comunity.demo.books.service.LanguageService;
import com.comunity.demo.books.view.model.Book;
import com.comunity.demo.books.view.model.BookDto;
import com.comunity.demo.books.view.model.Language;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class BookInfo extends VerticalLayout {

  private final Binder<Book> binder;
  private final BookService bookService;
  private Book book;

  public BookInfo(boolean readOnly, Button saveButton, LanguageService languageService, BookService bookService) {
    this.bookService = bookService;
    setWidthFull();

    TextField bookIdText = new TextField("Book Id");
    TextField bookNameText = new TextField("Book Name");
    bookNameText.setMinWidth("350px");

    Select<Language> originalLanguageSelect = new Select<>();
    originalLanguageSelect.setLabel("Original language");
    List<Language> languages = Optional.ofNullable(languageService.getLanguages().block())
        .orElseThrow()
        .stream()
        .map(Language::fromDto)
        .toList();
    originalLanguageSelect.setItems(languages);
    originalLanguageSelect.setItemLabelGenerator(language -> String.format("%s (%s)", language.getName(), language.getCode()));

    Checkbox publishedCheck = new Checkbox("Is book published");
    Checkbox internationalCheck = new Checkbox("Is book international");

    add(bookIdText, bookNameText, originalLanguageSelect, publishedCheck, internationalCheck);
    binder = new Binder<>();
    if (readOnly) {
      binder.bind(bookIdText, id -> String.valueOf(id.getId()), null);
      binder.bind(bookNameText, Book::getName, null);
      binder.bind(originalLanguageSelect, Book::getOriginalLanguage, null);
      binder.bind(publishedCheck, Book::isPublished, null);
      binder.bind(internationalCheck, Book::isInternational, null);
    }
    else {
      binder.bind(bookIdText, id -> String.valueOf(id.getId()), null);
      binder.bind(bookNameText, Book::getName, Book::setName);
      binder.forField(originalLanguageSelect).bind(Book::getOriginalLanguage, Book::setOriginalLanguage);
      binder.bind(publishedCheck, Book::isPublished, Book::setPublished);
      binder.bind(internationalCheck, Book::isInternational, Book::setInternational);

      HorizontalLayout buttonLayout = new HorizontalLayout(saveButton);
      buttonLayout.setSpacing(true);

      add(buttonLayout);
    }


    setSpacing(true);
  }

  public void setBook(Book book) {
    binder.readBean(book);
    this.book = book;
  }

  public Mono<BookDto> onSave() {
    boolean success = binder.writeBeanIfValid(book);

    if (!success) {
      return Mono.error(new ValidationException("There are validation errors"));
    }

    return bookService.updateBook(book.getId(), book);
  }

  public void doOnSuccess(BookDto updated) {
    // section for refreshing data
    book = Book.fromDto(updated);
    setBook(book);

    Notification.show("Successfully updated book details").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
  }

  public void doOnError(Throwable throwable) {
    if (throwable instanceof ValidationException) {
      Notification.show(throwable.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
      return;
    }

    log.error("Couldn't update book {} because of {}", book.getId(), throwable.getMessage(), throwable);
    getUI().ifPresent(ui -> ui.access(() ->
        Notification.show("Error happened while trying to update book, please refresh page").addThemeVariants(NotificationVariant.LUMO_ERROR)));
  }
}
