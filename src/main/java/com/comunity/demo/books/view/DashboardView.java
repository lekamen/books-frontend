package com.comunity.demo.books.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Admin UI")
public class DashboardView extends VerticalLayout {

  public DashboardView() {
    add("Dashboard");

  }
}
