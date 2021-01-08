package com.example.application.views;

import com.example.application.backend.entity.Nurse;
import com.example.application.backend.entity.Ward;
import com.example.application.backend.service.NurseService;
import com.example.application.backend.service.WardService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("nurse_assignment")
@PageTitle("Nurse Assignment | Posture App")

public class NurseView extends VerticalLayout {
    private NurseService nurseService;
    private WardService wardService;

    private HorizontalLayout addNurse = new HorizontalLayout();
    private HorizontalLayout assignN = new HorizontalLayout();

    NumberField nurseId = new NumberField("Nurse Id");
    TextField nameIn = new TextField("Nurse Name");
    TextField nurseEmail = new TextField("Nurse Email");
    NumberField wardId = new NumberField("Enter the ward Id");
    NumberField wardId1 = new NumberField("Enter the ward Id");
    //TextField createPassword = new TextField("Create a password");
    Button addNurseButton = new Button("Add New Nurse and Assign");
    Button assignNurseButton = new Button("Assign Nurse to Ward");


    public NurseView (NurseService nurseService,WardService wardService){
        this.nurseService=nurseService;
        this.wardService = wardService;

        setAlignItems(Alignment.CENTER);
        add(new H1("Nurse Login and assigmnent"));

        addNurseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        assignNurseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);

        addNurse.setAlignItems(FlexComponent.Alignment.BASELINE);
        assignN.setAlignItems(FlexComponent.Alignment.BASELINE);
        addNurse.add(nameIn,nurseEmail,wardId,addNurseButton);
        assignN.add(nurseId,wardId1, assignNurseButton);

        add(new H3("Add nurse to Database and assign nurse to ward"));
        add(addNurse);
        add(new H3("Reassign Nurse to ward"));
        add(assignN);

        addNurseButton.addClickListener(e ->{

            Nurse nurse = new Nurse(nameIn.getValue(),nurseEmail.getValue());
            nurse.setWard(wardService.getById(Math.round(wardId.getValue())));

            nurseService.save(nurse);

            nameIn.clear();
            nurseEmail.clear();
            wardId.clear();
            nameIn.focus();
            nurseEmail.focus();
            wardId.focus();

            Notification n= Notification.show("Nurse added to the Database");
            add(n);
        });

        assignNurseButton.addClickListener(e ->{
            Ward ward = wardService.getById(Math.round(wardId1.getValue()));
            Nurse nurse = nurseService.getById(Math.round(nurseId.getValue()));
            nurseService.deleteById(Math.round(nurseId.getValue()));
            nurse.setWard(ward);
            nurseService.save(nurse);

            wardId1.clear();
            nurseId.clear();
            wardId1.focus();
            nurseId.focus();

            Notification n= Notification.show("Nurse reassigned");
            add(n);
        });


    }

}
