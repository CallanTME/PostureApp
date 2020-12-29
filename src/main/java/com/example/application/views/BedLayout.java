package com.example.application.views;

import com.example.application.backend.entity.Bed;
import com.example.application.backend.entity.Patient;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;

public class BedLayout extends VerticalLayout {

    NumberField bedNum = new NumberField();

    private Patient patient;

    public BedLayout(Bed bed){

        ProgressBar positionProgress = new ProgressBar(0,100);
        positionProgress.setHeight("20px");


        getStyle().set("border", "1px solid #9E9E9E");
        setWidth("460px");
        setHeight("200px");
        setPadding(true);

        add(new H2("Bed " + (int)bed.getBedNum()));

        if(bed.isEmpty()){
            positionProgress.setValue(0);

            add(
                    new Label("This Bed is Empty"),
                    positionProgress
            );

        }

        //else then add call method to get status and set it to the position.progress

        Binder <Bed> bedBinder = new Binder<>(Bed.class);
        bedBinder.bindInstanceFields(this);
        bedBinder.setBean(bed);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
