package com.example.application.views;

import com.example.application.backend.entity.Nurse;
import com.example.application.backend.entity.Ward;
import com.example.application.backend.service.NurseService;
import com.example.application.backend.service.WardService;
import com.vaadin.flow.component.UI;
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

    private HorizontalLayout otherPages = new HorizontalLayout();
    private HorizontalLayout addNurse = new HorizontalLayout();
    private HorizontalLayout assignN = new HorizontalLayout();

    Button mainPage = new Button ("Main Page");

    TextField nurseId = new TextField("Nurse Id");
    TextField nameIn = new TextField("Nurse Name");
    TextField nurseEmail = new TextField("Nurse Email");
    TextField wardId = new TextField("Enter the ward Id");
    TextField wardId1 = new TextField("Enter the ward Id");
    //TextField createPassword = new TextField("Create a password");
    Button addNurseButton = new Button("Add New Nurse and Assign");
    Button assignNurseButton = new Button("Assign Nurse to Ward");


    public NurseView (NurseService nurseService,WardService wardService){
        this.nurseService=nurseService;
        this.wardService = wardService;

        otherPages.add(mainPage);
        otherPages.setVerticalComponentAlignment(Alignment.START,mainPage);
        add(otherPages);

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
            String email= nurseEmail.getValue();
            boolean c_email = false;
            for (int i=0;i<email.length();i++){
                if (email.charAt(i)=='@'){
                    c_email=true;
                }
            }

            if (nameIn.getValue().equals("") || nurseEmail.getValue().equals("") || wardId.getValue().equals("")) {
                Notification emptyFieldError = new Notification("Please fill in all fields", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else if(wardService.getById(Math.round(Float.parseFloat(wardId.getValue())))==null){
                Notification emptyFieldError = new Notification("This ward does not exist", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else if (!c_email){
                Notification emptyFieldError = new Notification("Please enter a correct email", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else {
                Nurse nurse = new Nurse(nameIn.getValue(), nurseEmail.getValue());
                nurse.setWard(wardService.getById(Math.round(Float.parseFloat(wardId.getValue()))));

                nurseService.save(nurse);

                nameIn.clear();
                nurseEmail.clear();
                wardId.clear();
                nameIn.focus();
                nurseEmail.focus();
                wardId.focus();

                Notification n = Notification.show("Nurse added to the Database");
                add(n);
            }
        });

        assignNurseButton.addClickListener(e ->{

            if (wardId1.getValue().equals("")|| nurseId.getValue().equals("")) {
                Notification emptyFieldError = new Notification("Please fill in all fields", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else if(wardService.getById(Math.round(Float.parseFloat(wardId1.getValue())))==null){
                Notification emptyFieldError = new Notification("This ward Id does not exist", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else if(nurseService.getById(Math.round(Float.parseFloat(nurseId.getValue())))==null){
                Notification emptyFieldError = new Notification("This nurse Id does not exist", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else {
                Ward ward = wardService.getById(Math.round(Float.parseFloat(wardId1.getValue())));
                Nurse nurse = nurseService.getById(Math.round(Float.parseFloat(nurseId.getValue())));
                nurseService.deleteById(Math.round(Float.parseFloat(nurseId.getValue())));
                nurse.setWard(ward);
                nurseService.save(nurse);

                wardId1.clear();
                nurseId.clear();
                wardId1.focus();
                nurseId.focus();

                Notification n = Notification.show("Nurse reassigned");
                add(n);
            }
        });


    }

}
