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
import org.hibernate.Session;

import java.awt.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;

@Route("")
public class WardView extends VerticalLayout {

    private BedService bedService;
    private PatientService patientService;

    Statement stat;
    Connection c;

    private HorizontalLayout addLine = new HorizontalLayout();
    private HorizontalLayout h1 = new HorizontalLayout();
    private HorizontalLayout h2 = new HorizontalLayout();
    private HorizontalLayout h3 = new HorizontalLayout();

    TextField nameIn = new TextField("Name");
    NumberField bedNumIn = new NumberField("Bed #");
    NumberField bScoreIn = new NumberField("Braden Score");
    Button addButton = new Button("ADD");
    NumberField dischargeBedNumIn = new NumberField("Bed #");
    Button dischargeButton = new Button("Discharge");



    public WardView(BedService bedService, PatientService patientService){
        this.bedService = bedService;
        this.patientService = patientService;

        setAlignItems(Alignment.CENTER);
        add(new H1("Ward 1"));

        bedNumIn.setHasControls(true);
        bedNumIn.setMin(1);
        bedNumIn.setMax(9);

        dischargeBedNumIn.setHasControls(true);
        dischargeBedNumIn.setMin(1);
        dischargeBedNumIn.setMax(9);

        bScoreIn.setHasControls(true);
        bScoreIn.setMin(1);
        bScoreIn.setMax(24);

        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        dischargeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);

        addLine.setAlignItems(Alignment.BASELINE);
        addLine.add(nameIn,bedNumIn,bScoreIn,addButton,dischargeBedNumIn,dischargeButton);

        for(int i = 1;i < 4;i++) {
            BoxLayout box = new BoxLayout();
            bedService.findById(i)
                    .map(BedLayout::new)
                    .ifPresent(box::add);
            h1.add(box);
        }

        for(int i = 4;i < 7;i++) {
            BoxLayout box = new BoxLayout();
            bedService.findById(i)
                    .map(BedLayout::new)
                    .ifPresent(box::add);
            h2.add(box);
        }

        for(int i = 7;i < 10;i++) {
            BoxLayout box = new BoxLayout();
            bedService.findById(i)
                    .map(BedLayout::new)
                    .ifPresent(box::add);
            h3.add(box);
        }

        add(addLine,h1,h2,h3);

        addButton.addClickListener(e ->{

            Patient patient = new Patient(nameIn.getValue(),bScoreIn.getValue(),bedNumIn.getValue());
            Bed bed = new Bed(bedNumIn.getValue(),false);

            Random r = new Random();
            r.nextInt(100);

            patient.setTimeInPos(r.nextInt(100));

            patientService.save(patient);
            bed.setPatient(patient);

            bedService.deleteByBedNum(bedNumIn.getValue());
            bedService.save(bed);

            refreshList();

            nameIn.clear();
            bScoreIn.clear();
            bedNumIn.clear();
            nameIn.focus();
            bScoreIn.focus();
            bedNumIn.focus();

        });

        dischargeButton.addClickListener(e ->{
            Bed bed = new Bed(dischargeBedNumIn.getValue());

            bedService.deleteByBedNum(dischargeBedNumIn.getValue());
            bedService.save(bed);

            refreshList();

            dischargeBedNumIn.clear();
            dischargeBedNumIn.focus();
        });
    }

    private void refreshList(){

        remove(h1,h2,h3);
        h1.removeAll();
        h2.removeAll();
        h3.removeAll();

        ArrayList<Bed> bedList = new ArrayList<Bed>();
        bedList.addAll(bedService.findAll());

        Collections.sort(bedList, new Comparator<Bed>() {
            @Override
            public int compare(Bed bed, Bed t1) {
                return (int) (bed.getBedNum() - t1.getBedNum());
            }
        });

        for(Bed tempBed : bedList){
            BedLayout bedLayout = new BedLayout(tempBed);
            BoxLayout boxLayout = new BoxLayout();

            if (tempBed.getBedNum() < 4) {
                boxLayout.add(bedLayout);
                h1.add(boxLayout);
            } else if (tempBed.getBedNum() < 7){
                boxLayout.add(bedLayout);
                h2.add(boxLayout);
            } else if (tempBed.getBedNum() < 10){
                boxLayout.add(bedLayout);
                h3.add(boxLayout);;
            }
        }

        add(h1,h2,h3);
    }


}

