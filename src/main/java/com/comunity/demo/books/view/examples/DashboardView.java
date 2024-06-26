package com.comunity.demo.books.view.examples;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;

//@Route(value = "dashboard", layout = DemoMainLayout.class)
//@RouteAlias(value = "", layout = DemoMainLayout.class)
@CssImport("./styles/highlight.css")
@PageTitle("Demo Vaadin UI")
public class DashboardView extends VerticalLayout {

  public DashboardView() {
    add(new H2("Dashboard"));

    TextField name = new TextField("Your name");
    Button sayHello = new Button("Say hello");
    add(name, sayHello);

    // Handle clicks
    sayHello.addClickListener(e -> Notification.show("Hello " + name.getValue()));
  }
}
