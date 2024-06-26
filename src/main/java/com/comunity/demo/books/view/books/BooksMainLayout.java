package com.comunity.demo.books.view.books;

import static com.comunity.demo.books.util.Icons.icon;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Push
public class BooksMainLayout extends AppLayout implements RouterLayout, AppShellConfigurator {

  public BooksMainLayout() {
    createHeader();
    createDrawer();
  }

  private void createHeader() {
    H1 logo = new H1("Books");
    logo.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);

    var header = new HorizontalLayout(new DrawerToggle(), logo);

    header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    header.setWidthFull();
    header.addClassNames(
        LumoUtility.Padding.Vertical.NONE,
        LumoUtility.Padding.Horizontal.MEDIUM);

    addToNavbar(header);
  }

  private void createDrawer() {
    RouterLink dashboardView = new RouterLink(DashboardView.class);
    dashboardView.setHighlightCondition(HighlightConditions.sameLocation());
    dashboardView.add(icon(VaadinIcon.ACCORDION_MENU, "Dashboard"));
    addToDrawer(new VerticalLayout(dashboardView));

    RouterLink languageView = new RouterLink(LanguagesView.class);
    languageView.setHighlightCondition(HighlightConditions.sameLocation());
    languageView.add(icon(VaadinIcon.GLOBE, "Supported languages"));
    addToDrawer(new VerticalLayout(languageView));

    RouterLink booksView = new RouterLink(BooksView.class);
    booksView.setHighlightCondition(HighlightConditions.sameLocation());
    booksView.add(icon(VaadinIcon.BOOK, "Books"));
    addToDrawer(new VerticalLayout(booksView));

  }



}