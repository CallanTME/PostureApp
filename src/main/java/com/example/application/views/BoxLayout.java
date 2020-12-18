package com.example.application.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class BoxLayout extends VerticalLayout {

    double boxId;

    public BoxLayout(double boxId){

        this.boxId = boxId;

        getStyle().set("border", "1px solid #9E9E9E");
        setWidth("460px");
        setHeight("250px");
        setPadding(true);
    }

    public double getBoxId() {
        return boxId;
    }

    public void setBoxId(double boxId) {
        this.boxId = boxId;
    }
}
