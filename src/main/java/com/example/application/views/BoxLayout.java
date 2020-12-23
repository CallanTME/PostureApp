package com.example.application.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class BoxLayout extends VerticalLayout {

    public BoxLayout(){

        setAlignItems(Alignment.AUTO);
        getStyle().set("border", "1px solid #9E9E9E");
        setWidth("460px");
        setHeight("250px");
        setPadding(true);
    }
}
