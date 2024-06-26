package com.comunity.demo.books.view.examples;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.Setter;

@Route(value = "dashboard-required", layout = DemoMainLayout.class)
@PageTitle("Required dashboard")
public class DashboardRequiredView extends VerticalLayout {

  private final Binder<HelloModel> binder;
  private HelloModel model;

  public DashboardRequiredView() {
    add(new H2("Dashboard"));

    TextField nameText = new TextField("Your name");
    Button sayHelloButton = new Button("Say hello");

    HorizontalLayout row = new HorizontalLayout(nameText, sayHelloButton);
    row.setDefaultVerticalComponentAlignment(Alignment.END);

    add(row);

    binder = new Binder<>(); // different options here based on constructor

    binder.forField(nameText).asRequired().bind(HelloModel::getName, HelloModel::setName); // also different options here
//    binder.forField(nameText)
//        .withValidator(value -> value != null && !value.isBlank(), "Value cannot be blank")
//        .bind(HelloModel::getName, HelloModel::setName);

    model = new HelloModel();
    binder.readBean(model); // setBean

    // Handle clicks
    sayHelloButton.addClickListener(this::sayHello);
  }

  private void sayHello(ClickEvent<Button> ignore) {
    boolean success = binder.writeBeanIfValid(model);

    if (!success) {
      Notification.show("Something went wrong").addThemeVariants(NotificationVariant.LUMO_ERROR);
      return;
    }

    Notification.show("Hello " + model.getName());

    model = new HelloModel();
    binder.readBean(model);
  }

  @Getter
  @Setter
  static class HelloModel {
    String name;
  }
}
