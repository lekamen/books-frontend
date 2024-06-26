package com.comunity.demo.books.view.books;

import com.comunity.demo.books.service.BookService;
import com.comunity.demo.books.service.LanguageService;
import com.comunity.demo.books.view.books.book.BookInfo;
import com.comunity.demo.books.view.books.book.BookView;
import com.comunity.demo.books.view.books.model.Book;
import com.comunity.demo.books.view.books.model.BookDto;
import com.comunity.demo.books.view.books.model.LanguageDto;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import java.util.List;

public class BookForm extends VerticalLayout {

  public BookForm(BookDto book, BookService bookService, LanguageService languageService) {

    setSizeFull();
    BookInfo bookInfo = new BookInfo(true, null, languageService, bookService);
    SupportedLanguages supportedLanguages = new SupportedLanguages();
    HorizontalLayout basicInfo = new HorizontalLayout(bookInfo, supportedLanguages);
    basicInfo.setMinWidth("80%");

    Icon icon = VaadinIcon.EXTERNAL_LINK.create();
    icon.getElement().getStyle().set("height", "70%");

    RouterLink bookView = new RouterLink(BookView.class, book.id());
    bookView.add(icon);

    HorizontalLayout title = new HorizontalLayout(new H2(new Span("Book info"), new Span(bookView)));
    title.setSpacing(true);

    add(title);
    add(basicInfo);

    bookInfo.setBook(Book.fromDto(book));
    bookService.getSupportedLanguages(book.id())
        .subscribe(dto -> getUI().ifPresent(ui -> ui.access(() -> supportedLanguages.setLanguages(dto.supportedLanguages()))));
  }

  static class SupportedLanguages extends VerticalLayout {

    private final Div noLanguagesDiv;
    private final UnorderedList languageList;
    public SupportedLanguages() {
      setWidthFull();
      add(new H4("Supported languages"));
      noLanguagesDiv = new Div("This book is not translated to any language!");
      add(noLanguagesDiv);

      languageList = new UnorderedList();
      add(languageList);

      setSpacing(true);
    }

    public void setLanguages(List<LanguageDto> languages) {
      if (languages.isEmpty()) {
        noLanguagesDiv.setVisible(true);
      }
      else {
        noLanguagesDiv.setVisible(false);
        languageList.add(languages.stream()
            .map(language -> language.name() + " (" + language.code() + ")")
            .map(ListItem::new)
            .toArray(ListItem[]::new));
      }
    }
  }
}
