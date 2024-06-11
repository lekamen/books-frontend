package com.comunity.demo.books.view.book;

import com.comunity.demo.books.service.BookService;
import com.comunity.demo.books.view.MainLayout;
import com.comunity.demo.books.view.model.Book;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

@Route(value = "books", layout = MainLayout.class)
@Slf4j
public class BookView extends VerticalLayout implements HasUrlParameter<Long>, HasDynamicTitle {

  private String title;
  private Long bookId;
  private Book book;
  private final BookService bookService;

  public BookView(BookService bookService) {
    this.bookService = bookService;
  }

  @Override
  public String getPageTitle() {
    return title;
  }

  @Override
  public void setParameter(BeforeEvent event, Long bookId) {
    if (bookId != null) {
      log.info("Opening BookView with id {}", bookId);

      this.bookId = bookId;

      bookService.getById(bookId)
          .subscribe(dto -> getUI().ifPresent(ui -> ui.access(() -> {

            book = Book.fromDto(dto);
            title = "Book " + book.getName();

          })));
    }
    else {
      title = "Book unknown";
    }
  }
}
