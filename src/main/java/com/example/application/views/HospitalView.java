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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

//This view enables to save a hospital or a new ward within the hospital
@Route("hospital")
@PageTitle("Hospital and Ward View | Posture App")
//@Secured("ROLE_Admin")
public class HospitalView extends VerticalLayout {
    private HospitalService hospitalService;
    private WardService wardService;

    private HorizontalLayout otherPages = new HorizontalLayout();
    private HorizontalLayout addHospital = new HorizontalLayout();
    private HorizontalLayout addWard = new HorizontalLayout();

    Button mainPage = new Button ("Main Page");

    TextField nameIn = new TextField("Hospital Name");
    TextField zipcodeIn = new TextField("Hospital Zipcode");
    Button addHospitalButton = new Button("Add New Hospital");

    TextField hospIdIn = new TextField("Hospital Id");
    TextField nameWardIn = new TextField("Ward Name");
    Button addWardButton = new Button("Add New Ward");

    public HospitalView( HospitalService hospitalService, WardService wardService) {
        this.hospitalService = hospitalService;
        this.wardService = wardService;

        otherPages.add(mainPage);
        otherPages.setVerticalComponentAlignment(Alignment.START,mainPage);
        add(otherPages);

        setAlignItems(Alignment.CENTER);
        add(new H1("Hospital and Wards Login"));

        zipcodeIn.setMaxLength(9);
        hospIdIn.setMaxLength(9);
        addHospitalButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        addWardButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);

        addHospital.setAlignItems(FlexComponent.Alignment.BASELINE);
        addWard.setAlignItems(FlexComponent.Alignment.BASELINE);
        addHospital.add(nameIn,zipcodeIn,addHospitalButton);
        addWard.add(hospIdIn,nameWardIn,addWardButton);

        add(addHospital,addWard);



        addHospitalButton.addClickListener(e ->{
            if (nameIn.getValue().equals("") || zipcodeIn.getValue().equals("")) {
                Notification emptyFieldError = new Notification("Please fill in all fields", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }
            else {
                Hospital hospital = new Hospital(nameIn.getValue(), zipcodeIn.getValue());

                hospitalService.save(hospital);

                nameIn.clear();
                zipcodeIn.clear();
                nameIn.focus();
                zipcodeIn.focus();

                Notification n = Notification.show("Hospital added to the Database", 3000, Notification.Position.TOP_END);
                add(n);
            }
        });

        addWardButton.addClickListener(e ->{

            if (hospIdIn.getValue().equals("") || nameWardIn.getValue().equals("")) {
                Notification emptyFieldError = new Notification("Please fill in all fields", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }


            /*if(!hospitalService.getById(Math.round(hospIdIn.getValue()))){
                Notification n= Notification.show("This Hospital Id doesn't exist");
                n.open();
            }*/
            else {
                Ward ward = new Ward(nameWardIn.getValue(), hospitalService.getById(Math.round(Float.parseFloat(hospIdIn.getValue()))));

                wardService.save(ward);

                nameWardIn.clear();
                hospIdIn.clear();
                nameWardIn.focus();
                hospIdIn.focus();

                Notification notif_added = Notification.show("Ward added to the Database");
                notif_added.open();
            }
        });
    }

}
