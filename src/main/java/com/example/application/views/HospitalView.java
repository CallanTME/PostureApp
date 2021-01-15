package com.example.application.views;


import com.example.application.backend.entity.Hospital;

import com.example.application.backend.entity.Ward;
import com.example.application.backend.service.HospitalService;
import com.example.application.backend.service.WardService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

//This view enables to save a hospital or a new ward within the hospital
@Route("hospital")
@PageTitle("Hospital and Ward View | Posture App")
//@Secured("ROLE_Admin")
public class HospitalView extends VerticalLayout {
    //Services are declared as they communicate with the database
    private HospitalService hospitalService;
    private WardService wardService;

    private HorizontalLayout addHospital = new HorizontalLayout();
    private HorizontalLayout addWard = new HorizontalLayout();

    //Textfields where the user can write the entries
    TextField nameIn = new TextField("Hospital Name");
    TextField zipcodeIn = new TextField("Hospital Zipcode");
    Button addHospitalButton = new Button("Add New Hospital");

    TextField hospZipIn = new TextField("Hospital Zipcode");
    TextField nameWardIn = new TextField("Ward Name");
    Button addWardButton = new Button("Add New Ward");

    public HospitalView( HospitalService hospitalService, WardService wardService) {
        //The services communicate with the database, we don't write directly in the database
        this.hospitalService = hospitalService;
        this.wardService = wardService;


        setAlignItems(Alignment.CENTER);
        add(new H1("Hospital and Wards Login"));

        //Sets the bounds to the textfields to ab=void wrong entries
        zipcodeIn.setMaxLength(9);
        hospZipIn.setMaxLength(9);
        addHospitalButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        addWardButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);

        addHospital.setAlignItems(FlexComponent.Alignment.BASELINE);
        addWard.setAlignItems(FlexComponent.Alignment.BASELINE);
        //Adds the textfields and buttons in the horizontal layout
        addHospital.add(nameIn,zipcodeIn,addHospitalButton);
        addWard.add(hospZipIn,nameWardIn,addWardButton);

        //adds the horizontal layouts to the view
        add(addHospital,addWard);



        //Defines the actions when a addHospitalButton is clicked
        addHospitalButton.addClickListener(e ->{
            //If all fields are not field, sends a notification
            if (nameIn.getValue().equals("") || zipcodeIn.getValue().equals("")) {
                Notification emptyFieldError = new Notification("Please fill in all fields", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            //enters the hospital in the database with all the fields entered
            else {
                Hospital hospital = new Hospital(nameIn.getValue(), zipcodeIn.getValue());

                hospitalService.save(hospital);

                //Empties the textfields for new entries
                nameIn.clear();
                zipcodeIn.clear();
                nameIn.focus();
                zipcodeIn.focus();

                //Notifies the user the hospital is added to the database
                Notification n = Notification.show("Hospital added to the Database", 3000, Notification.Position.TOP_END);
                add(n);
            }
        });

        //Defines the actions when a addWardButton is clicked
        addWardButton.addClickListener(e ->{

            String zip = hospZipIn.getValue();
            zip = formatZip(zip);

            //If all fields are not field, sends a notification
            if (hospZipIn.getValue().equals("") || nameWardIn.getValue().equals("")) {
                Notification emptyFieldError = new Notification("Please fill in all fields", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            //If the zipcode of the hospital doesn't exist in the database, sends a notification
            else if (hospitalService.zipsearch(zip)==null){
                Notification emptyFieldError = new Notification("This postcode is not correct, enter a hospital first", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            //enters the ward in the database with all the fields entered. The ward is linked to a hospital
            else {
                Ward ward = new Ward(nameWardIn.getValue(), hospitalService.zipsearch(zip));
                wardService.save(ward);

                nameWardIn.clear();
                hospZipIn.clear();
                nameWardIn.focus();
                hospZipIn.focus();

                //Notifies the user the ward is added to the database
                Notification notif_added = Notification.show("Ward added to the Database",3000, Notification.Position.TOP_END);
                notif_added.open();
            }

        });
    }

    //Formats the zip with the same format as in the entiies and easers the search
    public String formatZip(String postcode) {
        StringBuilder sb = new StringBuilder();
        char[] char_postcode = postcode.toCharArray();
        for (int i = char_postcode.length - 1; i >= 0; i--) {
            if (Character.isLetter(char_postcode[i])) {
                char_postcode[i] = Character.toTitleCase(char_postcode[i]);
                sb.append(char_postcode[i]);
            } else if (Character.isDigit(char_postcode[i])) {
                sb.append(char_postcode[i]);
            }
        }

        //insert a space after 3 chars from the back
        sb.insert(3, ' ');
        int lpostcode = sb.length();

        //Reverse the string
        char[] char_postcode2 = new char[lpostcode];
        for (int i = 0; i < lpostcode; i++) {
            char_postcode2[lpostcode - 1-i] = sb.charAt(i);
        }
        return String.valueOf(char_postcode2);
    }
}


