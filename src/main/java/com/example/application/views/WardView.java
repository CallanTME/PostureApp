package com.example.application.views;

import com.example.application.backend.entity.Bed;
import com.example.application.backend.entity.Patient;
import com.example.application.backend.service.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

import java.util.*;

// This is the main UI of the app
// It displays 9 beds with patient information and allow the input/discharge of patients

@Route("")
@PageTitle("Ward View | Posture App")
public class WardView extends VerticalLayout {

    // Service classes to access the bed and patient databases
    private BedService bedService;
    private PatientService patientService;

    // layouts within this main UI

    private HorizontalLayout otherPages = new HorizontalLayout();
    // layout for the input fields and buttons
    private HorizontalLayout addLine = new HorizontalLayout();
    // each of these layouts holds the information from 3 beds
    private HorizontalLayout h1 = new HorizontalLayout();
    private HorizontalLayout h2 = new HorizontalLayout();
    private HorizontalLayout h3 = new HorizontalLayout();

    // The input field and button components to be added to addLine
    NumberField idIn = new NumberField("ID");
    TextField nameIn = new TextField("Name");
    NumberField bedNumIn = new NumberField("Bed #");
    NumberField bScoreIn = new NumberField("Braden Score");
    Button addButton = new Button("Add New");
    Button addIdButton = new Button("Add by ID");
    NumberField dischargeBedNumIn = new NumberField("Bed #");
    Button dischargeButton = new Button("Discharge");

    // buttons to access the other page
    Button accessAdmin = new Button ("Admin Page");
    Button accessNurseAssignment = new Button ("Nurse Assignment");

    public WardView(BedService bedService, PatientService patientService){
        this.bedService = bedService;
        this.patientService = patientService;

        // sets up the page title and logout link
        createHeader();

        // adds buttons to other page
        otherPages.add(accessAdmin,accessNurseAssignment);
        otherPages.setVerticalComponentAlignment(Alignment.START,accessAdmin);
        add(otherPages);

        setAlignItems(Alignment.CENTER);

        // adding controls to the numbe fields and setting their bounds
        bedNumIn.setHasControls(true);
        bedNumIn.setMin(1);
        bedNumIn.setMax(9);

        dischargeBedNumIn.setHasControls(true);
        dischargeBedNumIn.setMin(1);
        dischargeBedNumIn.setMax(9);

        bScoreIn.setHasControls(true);
        bScoreIn.setMin(1);
        bScoreIn.setMax(24);

        // changing the button colours and themes
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        dischargeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
        addIdButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_CONTRAST);

        addLine.setAlignItems(Alignment.BASELINE);
        addLine.add(idIn,nameIn,bedNumIn,bScoreIn,addButton,addIdButton,dischargeBedNumIn,dischargeButton);

        // initializes 9 empty beds when the app is run
        for(int i = 1;i < 4;i++) {
            // this is the box which the information in contained within
            BoxLayout box = new BoxLayout();
            // finds the beds in the database and maps them to a bed layout
            bedService.findById(i)
                    .map(BedLayout::new)
                    .ifPresent(box::add);
            // adds the first 3 beds to the first line
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

        // adds all the layouts to the UI
        add(addLine,h1,h2,h3);

        accessAdmin.addClickListener(e->{
            UI.getCurrent().navigate("hospital");
        });

        accessNurseAssignment.addClickListener(e->{
            UI.getCurrent().navigate("nurse_assignment");
        });

        // code executed when the add button is clicked
        addButton.addClickListener(e ->{

            // checks that the necessary fields all have input
            if (nameIn.getValue().equals("") || bScoreIn.isEmpty() || bedNumIn.isEmpty()){
                // provides a notification to tell the user of the error
                Notification emptyFieldError = new Notification("Please fill in all fields", 3000, Notification.Position.TOP_CENTER);
                emptyFieldError.open();
            // checks that the number fields have numbers within bounds
            } else if(bedNumIn.getValue() > 9 || bedNumIn.getValue() < 1) {
                Notification bedBoundsError = new Notification("Bed number is out of bounds", 3000, Notification.Position.TOP_CENTER);
                bedBoundsError.open();
                bedNumIn.clear();
                bedNumIn.focus();
            } else if (bScoreIn.getValue() < 1 || bScoreIn.getValue() > 24) {
                Notification bScoreBoundsError = new Notification("Braden Score is out of bounds", 3000, Notification.Position.TOP_CENTER);
                bScoreBoundsError.open();
                bScoreIn.clear();
                bScoreIn.focus();
            } else {
                // gets the selected bed from the database and checks if it is empty
                Bed checkBed = bedService.getByBedNum(bedNumIn.getValue());
                // if it is empty, then it will add the inputted patient
                if (checkBed.isEmpty()) {
                    // creates a new patient with input information
                    Patient patient = new Patient(nameIn.getValue(), bScoreIn.getValue());
                    // creates a new occupied bed with the input bed number
                    Bed bed = new Bed(bedNumIn.getValue(), false);

                    // saves the patient to the database
                    patientService.save(patient);
                    // sets the bed's patient as the new patient
                    bed.setPatient(patient);

                    // deletes the previous empty bed with the same bed number
                    bedService.deleteByBedNum(bedNumIn.getValue());
                    // saves the new bed with the inputted patient to the database
                    bedService.save(bed);

                    // refreshes the 9 bed layout to show new patient
                    refreshList();

                    //clears all the input fields so they are ready for the next input
                    nameIn.clear();
                    bScoreIn.clear();
                    bedNumIn.clear();
                    nameIn.focus();
                    bScoreIn.focus();
                    bedNumIn.focus();

                } else {
                    // if the bed is already full this notifies the user and does not add the patient
                    Notification bedError = new Notification("This bed is already occupied", 3000, Notification.Position.TOP_CENTER);
                    bedError.open();
                    bedNumIn.clear();
                    bedNumIn.focus();
                }
            }
        });

        // code executed when the discharge button is clicked
        dischargeButton.addClickListener(e ->{

            // checks if the bed number input it empty
            if(dischargeBedNumIn.isEmpty()){
                Notification emptyFieldError = new Notification("Please fill a bed number to discharge", 3000, Notification.Position.TOP_CENTER);
                emptyFieldError.open();
            // checks if the bed number inputted is already in bounds
            } else if (dischargeBedNumIn.getValue() < 1 || dischargeBedNumIn.getValue() > 9){
                Notification bedBoundsError = new Notification("Discharge bed number is out of bounds", 3000, Notification.Position.TOP_CENTER);
                bedBoundsError.open();
                dischargeBedNumIn.clear();
                dischargeBedNumIn.focus();

            } else {
                // gets bed to validate that it is occupied
                Bed checkBed = bedService.getByBedNum(dischargeBedNumIn.getValue());

                // checks if the bed is occupied
                if(checkBed.isEmpty()){
                    Notification bedEmptyError = new Notification("This bed is already empty", 3000, Notification.Position.TOP_CENTER);
                    bedEmptyError.open();
                    dischargeBedNumIn.clear();
                    dischargeBedNumIn.focus();
                } else {
                    // creates a new empty bed with the same bed number
                    Bed bed = new Bed(dischargeBedNumIn.getValue());

                    // deletes the occupied bed
                    bedService.deleteByBedNum(dischargeBedNumIn.getValue());
                    // saves the empty bed into the database
                    bedService.save(bed);

                    refreshList();

                    dischargeBedNumIn.clear();
                    dischargeBedNumIn.focus();
                }
            }
        });

        // code exectued when the add by id button is clicked
        addIdButton.addClickListener(e ->{

            // checks to make sure the input is valid
            if(idIn.isEmpty() || bedNumIn.isEmpty()){
                Notification emptyFieldError = new Notification("Please fill in and ID and bed number", 3000, Notification.Position.TOP_CENTER);
                emptyFieldError.open();
            } else if(bedNumIn.getValue() > 9 || bedNumIn.getValue() < 1){
                Notification bedBoundsError = new Notification("Bed number is out of bounds", 3000, Notification.Position.TOP_CENTER);
                bedBoundsError.open();
                bedNumIn.clear();
                bedNumIn.focus();
            } else {
                // converts the double input to a long for the id
                long id = Math.round(idIn.getValue());
                // checks if the the input id is in the database
                if (patientService.findById(id).isPresent()) {
                    // creates a new occupied bed
                    Bed bed = new Bed(bedNumIn.getValue(), false);
                    // sets the patient with the input id as the patient of the bed
                    bed.setPatient(patientService.getById(id));
                    // deletes the empty bed
                    bedService.deleteByBedNum(bedNumIn.getValue());
                    // saves the new bed to the database
                    bedService.save(bed);

                    refreshList();

                    bedNumIn.clear();
                    bedNumIn.focus();
                    idIn.clear();
                    idIn.focus();
                } else {
                    // if the id is not in the database this notifies the user
                    Notification noIdError = new Notification("This ID does not exist", 3000, Notification.Position.TOP_CENTER);
                    noIdError.open();
                }
            }
        });

        // this sets the refresh interval to 10 seconds
        UI.getCurrent().setPollInterval(10000);

        // sets what should be done every time the app refreshes
        UI.getCurrent().addPollListener(e ->{

            // gets all beds from the database and saves them to a list so they can be altered
            ArrayList<Bed> bedList = new ArrayList<Bed>(bedService.findAll());

            // for each bed in the list it updates the information within it
            for(Bed bed : bedList){
                if(!bed.isEmpty()){
                    bed.update();
                }
            }

            // deletes all the beds in the database and replaces them with the updated beds
            bedService.deleteAll();
            for(Bed bed : bedList){
                bedService.save(bed);
            }

            refreshList();
        });
    }

    // code for refreshing the 9 bed layout to show updated information
    private void refreshList(){

        // removes all the layouts and the components from the layouts
        remove(h1,h2,h3);
        h1.removeAll();
        h2.removeAll();
        h3.removeAll();

        // gets all beds from the database and saves them to a list
        ArrayList<Bed> bedList = new ArrayList<Bed>(bedService.findAll());

        // sorts them by bed number
        Collections.sort(bedList, new Comparator<Bed>() {
            @Override
            public int compare(Bed bed, Bed t1) {
                return (int) (bed.getBedNum() - t1.getBedNum());
            }
        });


        for(Bed tempBed : bedList){
            // maps each bed to a bed layout
            BedLayout bedLayout = new BedLayout(tempBed);
            // adds each bed layout to a box layout
            BoxLayout boxLayout = new BoxLayout();

            // adds the first 3 beds to the first line
            if (tempBed.getBedNum() < 4) {
                boxLayout.add(bedLayout);
                h1.add(boxLayout);
            // adds the second 3 beds to the second line
            } else if (tempBed.getBedNum() < 7){
                boxLayout.add(bedLayout);
                h2.add(boxLayout);
            // adds the last 3 beds to the third line
            } else if (tempBed.getBedNum() < 10){
                boxLayout.add(bedLayout);
                h3.add(boxLayout);
            }
        }

        // adds all the lines to the UI
        add(h1,h2,h3);
    }

    private void createHeader() {
        // adds a title to the header
        H2 wardName = new H2("Ward 1");
        // adds a logout button which links to /logout
        Anchor logout = new Anchor("logout", "Log out");

        // adds these components to a layout
        HorizontalLayout header = new HorizontalLayout(wardName, logout);

        // formats the header
        header.expand(wardName);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("95%");
        header.addClassName("header");

        // adds the header to the UI
        add(header);
    }
}

