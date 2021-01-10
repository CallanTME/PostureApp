package com.example.application.views;

import com.example.application.backend.entity.Bed;
import com.example.application.backend.entity.Patient;
import com.example.application.backend.service.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

import java.util.*;

@Route("")
@PageTitle("Ward View | Posture App")
//@Secured("ROLE_USER")
public class WardView extends VerticalLayout {

    private BedService bedService;
    private PatientService patientService;

    private HorizontalLayout otherPages = new HorizontalLayout();
    private HorizontalLayout addLine = new HorizontalLayout();
    private HorizontalLayout h1 = new HorizontalLayout();
    private HorizontalLayout h2 = new HorizontalLayout();
    private HorizontalLayout h3 = new HorizontalLayout();

    NumberField idIn = new NumberField("ID");
    TextField nameIn = new TextField("Name");
    NumberField bedNumIn = new NumberField("Bed #");
    NumberField bScoreIn = new NumberField("Braden Score");
    Button addButton = new Button("Add New");
    Button addIdButton = new Button("Add by ID");
    NumberField dischargeBedNumIn = new NumberField("Bed #");
    Button dischargeButton = new Button("Discharge");

    Button accessAdmin = new Button ("Admin Page");
    Button accessNurseAssignment = new Button ("Nurse Assignment");

    public WardView(BedService bedService, PatientService patientService){
        this.bedService = bedService;
        this.patientService = patientService;

        otherPages.add(accessAdmin,accessNurseAssignment);
        otherPages.setVerticalComponentAlignment(Alignment.START,accessAdmin);
        add(otherPages);

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
        addIdButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_CONTRAST);

        addLine.setAlignItems(Alignment.BASELINE);
        addLine.add(idIn,nameIn,bedNumIn,bScoreIn,addButton,addIdButton,dischargeBedNumIn,dischargeButton);

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

        accessAdmin.addClickListener(e->{
            UI.getCurrent().navigate("hospital");
        });

        accessNurseAssignment.addClickListener(e->{
            UI.getCurrent().navigate("nurse_assignment");
        });

        addButton.addClickListener(e ->{

            if (nameIn.getValue().equals("") || bScoreIn.isEmpty() || bedNumIn.isEmpty()){
                Notification emptyFieldError = new Notification("Please fill in all fields", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            } else if(bedNumIn.getValue() > 9 || bedNumIn.getValue() < 1) {
                Notification bedBoundsError = new Notification("Bed number is out of bounds", 3000, Notification.Position.TOP_END);
                bedBoundsError.open();
                bedNumIn.clear();
                bedNumIn.focus();
            } else if (bScoreIn.getValue() < 1 || bScoreIn.getValue() > 24) {
                Notification bScoreBoundsError = new Notification("Braden Score is out of bounds", 3000, Notification.Position.TOP_END);
                bScoreBoundsError.open();
                bScoreIn.clear();
                bScoreIn.focus();
            } else {
                Bed checkBed = bedService.getByBedNum(bedNumIn.getValue());
                if (checkBed.isEmpty()) {
                    Patient patient = new Patient(nameIn.getValue(), bScoreIn.getValue());
                    Bed bed = new Bed(bedNumIn.getValue(), false);

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
                } else {
                    Notification bedError = new Notification("This bed is already occupied", 3000, Notification.Position.TOP_END);
                    bedError.open();
                    bedNumIn.clear();
                    bedNumIn.focus();
                }
            }
        });

        dischargeButton.addClickListener(e ->{

            if(dischargeBedNumIn.isEmpty()){
                Notification emptyFieldError = new Notification("Please fill a bed number to discharge", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();

            } else if (dischargeBedNumIn.getValue() < 1 || dischargeBedNumIn.getValue() > 9){
                Notification bedBoundsError = new Notification("Discharge bed number is out of bounds", 3000, Notification.Position.TOP_END);
                bedBoundsError.open();
                dischargeBedNumIn.clear();
                dischargeBedNumIn.focus();

            } else {
                Bed checkBed = bedService.getByBedNum(dischargeBedNumIn.getValue());

                if(checkBed.isEmpty()){
                    Notification bedEmptyError = new Notification("This bed is already empty", 3000, Notification.Position.TOP_END);
                    bedEmptyError.open();
                    dischargeBedNumIn.clear();
                    dischargeBedNumIn.focus();
                } else {
                    Bed bed = new Bed(dischargeBedNumIn.getValue());

                    bedService.deleteByBedNum(dischargeBedNumIn.getValue());
                    bedService.save(bed);

                    refreshList();

                    dischargeBedNumIn.clear();
                    dischargeBedNumIn.focus();
                }
            }
        });

        addIdButton.addClickListener(e ->{

            if(idIn.isEmpty() || bedNumIn.isEmpty()){
                Notification emptyFieldError = new Notification("Please fill in and ID and bed number", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            } else if(bedNumIn.getValue() > 9 || bedNumIn.getValue() < 1){
                Notification bedBoundsError = new Notification("Bed number is out of bounds", 3000, Notification.Position.TOP_END);
                bedBoundsError.open();
                bedNumIn.clear();
                bedNumIn.focus();
            } else {
                long id = Math.round(idIn.getValue());
                if (patientService.findById(id).isPresent()) {

                    Bed bed = new Bed(bedNumIn.getValue(), false);
                    bed.setPatient(patientService.getById(id));
                    bedService.deleteByBedNum(bedNumIn.getValue());
                    bedService.save(bed);

                    refreshList();

                    bedNumIn.clear();
                    bedNumIn.focus();
                    idIn.clear();
                    idIn.focus();
                } else {
                    Notification noIdError = new Notification("This ID does not exist", 3000, Notification.Position.TOP_END);
                    noIdError.open();
                }
            }
        });


        UI.getCurrent().setPollInterval(10000);

        UI.getCurrent().addPollListener(e ->{
            ArrayList<Bed> bedList = new ArrayList<Bed>();
            bedList.addAll(bedService.findAll());

            for(Bed bed : bedList){
                if(!bed.isEmpty()){
                    bed.update();
                }
            }

            bedService.deleteAll();
            for(Bed bed : bedList){
                bedService.save(bed);
            }

            refreshList();
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
                h3.add(boxLayout);
            }
        }

        add(h1,h2,h3);
    }
}

