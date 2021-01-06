package com.example.application.views;


import com.example.application.backend.entity.Hospital;

import com.example.application.backend.entity.Ward;
import com.example.application.backend.service.HospitalService;
import com.example.application.backend.service.WardService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import static com.sun.el.lang.ELArithmetic.add;

//This view enables to save a hospital or a new ward within the hospital
@Route("hospital")
@PageTitle("Hospital and Ward View | Posture App")
public class HospitalView extends VerticalLayout {
    private HospitalService hospitalService;
    private WardService wardService;

    private HorizontalLayout addHospital = new HorizontalLayout();
    private HorizontalLayout addWard = new HorizontalLayout();

    TextField nameIn = new TextField("Hospital Name");
    TextField zipcodeIn = new TextField("Hospital Zipcode");
    Button addHospitalButton = new Button("Add New Hospital");

    NumberField HospIdIn = new NumberField("Hospital Id");
    TextField nameWardIn = new TextField("Ward Name");
    Button addWardButton = new Button("Add New Ward");

    public HospitalView( HospitalService hospitalService, WardService wardService) {
        this.hospitalService = hospitalService;
        this.wardService = wardService;

        zipcodeIn.setMaxLength(9);
        HospIdIn.setMaxLength(9);
        addHospitalButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        addWardButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);

        addHospital.setAlignItems(FlexComponent.Alignment.BASELINE);
        addWard.setAlignItems(FlexComponent.Alignment.BASELINE);
        addHospital.add(nameIn,zipcodeIn,addHospitalButton);
        addWard.add(HospIdIn,nameWardIn,addWardButton);

        add(addHospital,addWard);

        /*addHospitalButton.addClickListener(e ->{

            Hospital hospital = new Hospital(nameIn.getValue(),zipcodeIn.getValue());

            hospitalService.save(hospital);

            nameIn.clear();
            zipcodeIn.clear();
            nameIn.focus();
            zipcodeIn.focus();
        });*/

        /*addWardButton.addClickListener(e ->{

            Ward ward = new Ward(nameWardIn.getValue(),hospitalService.getById(Math.round(HospIdIn.getValue())));

            wardService.save(ward);

            nameWardIn.clear();
            HospIdIn.clear();
            nameWardIn.focus();
            HospIdIn.focus();
        });*/
    }

}
