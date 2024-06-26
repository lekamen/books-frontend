package com.comunity.demo.books.view.examples;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoMainLayout extends AppLayout implements RouterLayout {

  public DemoMainLayout() {
    createHeader();
    createDrawer();
  }

  private void createHeader() {
    H1 logo = new H1("Vaadin Demo");
    var header = new HorizontalLayout(new DrawerToggle(), logo);
    addToNavbar(header);
  }

  private void createDrawer() {
    RouterLink dashboardView = new RouterLink("Dashboard", DashboardView.class);
    dashboardView.setHighlightCondition(HighlightConditions.sameLocation());
    addToDrawer(new VerticalLayout(dashboardView));

    RouterLink dashboardHorizontalView = new RouterLink("Dashboard horizontal", DashboardHorizontalView.class);
    dashboardHorizontalView.setHighlightCondition(HighlightConditions.sameLocation());
    addToDrawer(new VerticalLayout(dashboardHorizontalView));

    RouterLink dashboardRequiredView = new RouterLink("Dashboard required", DashboardRequiredView.class);
    dashboardRequiredView.setHighlightCondition(HighlightConditions.sameLocation());
    addToDrawer(new VerticalLayout(dashboardRequiredView));

  }



}