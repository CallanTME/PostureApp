package com.example.application.views;

import com.example.application.backend.entity.Bed;
import com.example.application.backend.entity.Patient;
import com.example.application.backend.repository.BedRepo;
import com.example.application.backend.service.*;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.awt.*;
import java.util.ArrayList;

@Route("")
public class WardView extends VerticalLayout {

    private BedService bedService;
    private PatientService patientService;

    private HorizontalLayout addLine = new HorizontalLayout();
    private HorizontalLayout h1 = new HorizontalLayout();
    private HorizontalLayout h2 = new HorizontalLayout();
    private HorizontalLayout h3 = new HorizontalLayout();

    TextField nameIn = new TextField("Name");
    NumberField bedNumIn = new NumberField("Bed #");
    NumberField bScoreIn = new NumberField("Braden Score");
    Button addButton = new Button("ADD");


    public WardView(BedService bedService, PatientService patientService){
        this.bedService = bedService;
        this.patientService = patientService;

        setAlignItems(Alignment.CENTER);
        add(new H1("Ward 1"));

        bedNumIn.setHasControls(true);
        bedNumIn.setMin(1);
        bedNumIn.setMax(9);

        bScoreIn.setHasControls(true);
        bScoreIn.setMin(1);
        bScoreIn.setMax(24);

        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);

        addLine.setAlignItems(Alignment.BASELINE);
        addLine.add(nameIn,bedNumIn,bScoreIn,addButton);

        for(int i = 1;i < 4;i++) {
            BoxLayout box = new BoxLayout(i);
            bedService.findById(i)
                    .map(BedLayout::new)
                    .ifPresent(box::add);
            h1.add(box);
        }

        for(int i = 4;i < 7;i++) {
            BoxLayout box = new BoxLayout(i);
            bedService.findById(i)
                    .map(BedLayout::new)
                    .ifPresent(box::add);
            h2.add(box);
        }

        for(int i = 7;i < 10;i++) {
            BoxLayout box = new BoxLayout(i);
            bedService.findById(i)
                    .map(BedLayout::new)
                    .ifPresent(box::add);
            h3.add(box);
        }

        add(addLine,h1,h2,h3);

        addButton.addClickListener(e ->{
            patientService.save(new Patient(nameIn.getValue(),bScoreIn.getValue(),bedNumIn.getValue()));
            Patient patient = new Patient(nameIn.getValue(),bScoreIn.getValue(),bedNumIn.getValue());

            PatientLayout patientLayout = new PatientLayout(patient);

            remove(h1,h2,h3);
            h1.removeAll();
            h2.removeAll();
            h3.removeAll();

            for(int i = 1;i < 4;i++) {
                BoxLayout box = new BoxLayout(i);
                if(patient.getBedNum() == i){
                    box.add(patientLayout);
                } else {
                    bedService.findById(i)
                            .map(BedLayout::new)
                            .ifPresent(box::add);
                }
                h1.add(box);
            }

            for(int i = 4;i < 7;i++) {
                BoxLayout box = new BoxLayout(i);
                if(patient.getBedNum() == i){
                    box.add(patientLayout);
                } else {
                    bedService.findById(i)
                            .map(BedLayout::new)
                            .ifPresent(box::add);
                }
                h2.add(box);
            }

            for(int i = 7;i < 10;i++) {
                BoxLayout box = new BoxLayout(i);
                if(patient.getBedNum() == i){
                    box.add(patientLayout);
                } else {
                    bedService.findById(i)
                            .map(BedLayout::new)
                            .ifPresent(box::add);
                }
                h3.add(box);
            }

            add(h1,h2,h3);

            nameIn.clear();
            bScoreIn.clear();
            bedNumIn.clear();
            nameIn.focus();
            bScoreIn.focus();
            bedNumIn.focus();

        });
    }
}

