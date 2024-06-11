package com.comunity.demo.books.view;

import static com.comunity.demo.books.util.Icons.icon;

import com.comunity.demo.books.service.BookService;
import com.comunity.demo.books.view.book.BookView;
import com.comunity.demo.books.view.model.BookDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Route(value = "books", layout = MainLayout.class)
@PageTitle("Books")
@Slf4j
public class BooksView extends VerticalLayout {
  private final HorizontalLayout bookPlaceholder;

  public BooksView(BookService bookService) {
    Grid<BookDto> grid = new Grid<>();
    grid.setSelectionMode(SelectionMode.SINGLE);
    grid.setAllRowsVisible(true);
    grid.addColumn(BookDto::author).setHeader("Author");
    grid.addColumn(BookDto::name).setHeader("Name");
    grid.addColumn(BookDto::publisher).setHeader("Publisher");
    grid.addComponentColumn(this::isPublishedRenderer).setHeader("Is published");
    grid.addComponentColumn(this::linkRenderer).setHeader("Edit");
    add(grid);

    bookPlaceholder = new HorizontalLayout();
    bookPlaceholder.setWidthFull();
    add(bookPlaceholder);

    grid.addSelectionListener(event -> {
      Optional<BookDto> selected = event.getFirstSelectedItem();
      bookPlaceholder.removeAll();
      selected.ifPresent(bookDto -> bookPlaceholder.add(new BookForm(bookDto, bookService)));
    });

    bookService.getBooks()
        .subscribe(books -> getUI().ifPresent(ui -> ui.access(() -> grid.setItems(books))));

  }

  private Component isPublishedRenderer(BookDto bookDto) {
    Checkbox checkbox = new Checkbox();
    checkbox.setValue(bookDto.isPublished());
    checkbox.setReadOnly(true);

    return checkbox;
  }

  private Component linkRenderer(BookDto book) {
    RouterLink bookView = new RouterLink(BookView.class, book.id());
    bookView.add(icon(VaadinIcon.EXTERNAL_LINK, ""));

    return bookView;
  }
}
