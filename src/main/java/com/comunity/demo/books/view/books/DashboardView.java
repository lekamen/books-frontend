package com.comunity.demo.books.view.books;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = BooksMainLayout.class)
@PageTitle("Books UI")
public class DashboardView extends VerticalLayout {

  public DashboardView() {
    add("Dashboard");

  }
}
