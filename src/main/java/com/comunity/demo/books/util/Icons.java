package com.comunity.demo.books.util;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Icons {

  private Icons() {}

  public static HorizontalLayout icon(VaadinIcon icon, String text) {
    return new HorizontalLayout(icon.create(), new Div(text));
  }

}
