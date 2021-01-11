package com.example.application.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class BoxLayout extends VerticalLayout {

    // This is a box to put each bed layout in to easily format the main UI
    public BoxLayout(){
        // formatting of the box
        setAlignItems(Alignment.AUTO);
        getStyle().set("border", "1px solid #9E9E9E");
        setWidth("460px");
        setHeight("275px");
        setPadding(true);
    }
}
