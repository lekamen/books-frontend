package com.comunity.demo.books.view.examples;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "dashboard-horizontal", layout = DemoMainLayout.class)
@PageTitle("Horizontal dashboard")
public class DashboardHorizontalView extends VerticalLayout {

  public DashboardHorizontalView() {
    add(new H2("Dashboard"));

    TextField name = new TextField("Your name");
    Button sayHello = new Button("Say hello");

    HorizontalLayout row = new HorizontalLayout(name, sayHello);
    add(row);

    //row.setDefaultVerticalComponentAlignment(Alignment.END);
    //name.setRequired(true);

    // Handle clicks
    sayHello.addClickListener(e -> Notification.show("Hello " + name.getValue()));
  }
}
