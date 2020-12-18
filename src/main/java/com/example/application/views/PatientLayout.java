package com.example.application.views;

import com.example.application.backend.entity.Patient;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration;

public class PatientLayout extends VerticalLayout {

    TextField name = new TextField("Name");
    NumberField bScore = new NumberField("Braden Score");

    public PatientLayout(Patient patient){

        HorizontalLayout infoLine = new HorizontalLayout();
        ProgressBar progressBar = new ProgressBar(0,100,0);
        progressBar.setHeight("20px");



        name.setReadOnly(true);
        name.setWidth("175px");
        H2 bedNumHeader = new H2("Bed " + (int)patient.getBedNum());
        bedNumHeader.setWidth("80px");
        bScore.setWidth("100px");
        bScore.setReadOnly(true);

        infoLine.add(bedNumHeader,name,bScore);
        add(
                infoLine,
                new Label("Time in Position"),
                progressBar
        );


        Binder <Patient> patientBinder = new Binder<>(Patient.class);
        patientBinder.bindInstanceFields(this);
        patientBinder.setBean(patient);
    }
}
