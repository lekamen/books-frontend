package com.comunity.demo.books.view.books.book;

import com.comunity.demo.books.service.BookService;
import com.comunity.demo.books.service.LanguageService;
import com.comunity.demo.books.view.books.BooksMainLayout;
import com.comunity.demo.books.view.books.BooksView;
import com.comunity.demo.books.view.books.model.Book;
import com.comunity.demo.books.view.books.model.Language;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import lombok.extern.slf4j.Slf4j;

@Route(value = "books", layout = BooksMainLayout.class)
@Slf4j
public class BookView extends VerticalLayout implements HasUrlParameter<Long>, HasDynamicTitle {

  private String title;
  private Book book;
  private final BookService bookService;

  private final BookInfo bookInfo;
  private final LanguageForm languageForm;

  public BookView(BookService bookService, LanguageService languageService) {
    this.bookService = bookService;

    Button saveButton = new Button("Save book details", VaadinIcon.FILE_O.create(), this::onSave);
    saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    this.bookInfo = new BookInfo(false, saveButton, languageService, bookService);
    this.languageForm = new LanguageForm(bookService, languageService);

    HorizontalLayout infoAndLanguages = new HorizontalLayout();
    infoAndLanguages.add(bookInfo, languageForm);
    infoAndLanguages.setWidthFull();

    setWidthFull();
    Icon icon = VaadinIcon.ARROW_BACKWARD.create();
    RouterLink booksView = new RouterLink(BooksView.class);
    booksView.setHighlightCondition(HighlightConditions.sameLocation());
    booksView.add(icon);

    VerticalLayout backLayout = new VerticalLayout(booksView);
    VerticalLayout title = new VerticalLayout(new H2("Book details"));

    add(backLayout);
    add(title);
    add(infoAndLanguages);
  }

  @Override
  public String getPageTitle() {
    return title;
  }

  @Override
  public void setParameter(BeforeEvent event, Long bookId) {
    if (bookId != null) {
      log.info("Opening BookView with id {}", bookId);

      bookService.getById(bookId) //TODO: explain potential problems with this, regarding to dynamic title
          .subscribe(dto -> getUI().ifPresent(ui -> ui.access(() -> {

            book = Book.fromDto(dto);
            title = "Book " + book.getName();

            bookInfo.setBook(book);
            languageForm.setLanguages(bookId, book.getOriginalLanguage());
          })));

//      BookDto dto = bookService.getById(bookId).block();
//      book = Book.fromDto(dto);
//      title = "Book " + book.getName();
//
//      bookInfo.setBook(book);
//      languageForm.setLanguages(bookId, book.getOriginalLanguage());
    }
    else {
      title = "Book unknown";
    }
  }

  private void onSave(ClickEvent<Button> event) {
    bookInfo.onSave()
        .subscribe(
            updated -> getUI().ifPresent(ui -> ui.access(() -> {
              bookInfo.doOnSuccess(updated);
              languageForm.setLanguages(updated.id(), Language.fromDto(updated.originalLanguage()));
            })),
            bookInfo::doOnError);
  }
}
