package com.example.application.views;

import com.example.application.backend.entity.Bed;
import com.example.application.backend.entity.Patient;
import com.example.application.backend.service.PatientService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;

public class BedLayout extends VerticalLayout {

    NumberField bedNum = new NumberField();

    public BedLayout(Bed bed){

        add(new H2("Bed " + (int)bed.getBedNum()));

        if(bed.isEmpty()){

            ProgressBar positionProgress = new ProgressBar(0,100);
            positionProgress.setHeight("20px");
            positionProgress.setValue(0);

            add(
                    new Label("This Bed is Empty"),
                    positionProgress
            );
        }

        Binder <Bed> bedBinder = new Binder<>(Bed.class);
        bedBinder.bindInstanceFields(this);
        bedBinder.setBean(bed);
    }
}
