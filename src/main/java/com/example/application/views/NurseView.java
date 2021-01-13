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


    TextField nurseEmailIn = new TextField("Enter Nurse Email");
    TextField nameIn = new TextField("Nurse Name");
    TextField nurseEmail = new TextField("Nurse Email");
    TextField wardName = new TextField("Enter the ward Name");
    TextField wardName1 = new TextField("Enter the ward Name");
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
        addNurse.add(nameIn,nurseEmail, wardName,addNurseButton);
        assignN.add(nurseEmailIn, wardName1, assignNurseButton);

        add(new H3("Add nurse to Database and assign nurse to ward"));
        add(addNurse);
        add(new H3("Reassign Nurse to ward"));
        add(assignN);


        addNurseButton.addClickListener(e ->{

            String email= nurseEmail.getValue();
            email = email.toLowerCase();
            boolean c_email = false;
            for (int i=0;i<email.length();i++){
                if (email.charAt(i)=='@'){
                    c_email=true;
                }
            }



            if (nameIn.getValue().equals("") || nurseEmail.getValue().equals("") || wardName.getValue().equals("")) {
                Notification emptyFieldError = new Notification("Please fill in all fields", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else if (!c_email){
                Notification emptyFieldError = new Notification("Please enter a correct email", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else if(wardService.nameSearch(wardName.getValue().toLowerCase())==null){
                Notification emptyFieldError = new Notification("Ward name not in database", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else {
                Nurse nurse = new Nurse(nameIn.getValue(), nurseEmail.getValue());
                nurse.setWard(wardService.nameSearch(wardName.getValue().toLowerCase()));

                nurseService.save(nurse);

                nameIn.clear();
                nurseEmail.clear();
                wardName.clear();
                nameIn.focus();
                nurseEmail.focus();
                wardName.focus();

                Notification n = Notification.show("Nurse added to the Database", 3000, Notification.Position.TOP_END);
                add(n);
            }


        });

        assignNurseButton.addClickListener(e ->{

            String email= nurseEmailIn.getValue();
            email = email.toLowerCase();

            if (wardName1.getValue().equals("")|| nurseEmailIn.getValue().equals("")) {
                Notification emptyFieldError = new Notification("Please fill in all fields", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else if(nurseService.emailSearch(email)==null){
                Notification emptyFieldError = new Notification("This email is not in the database", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else if(wardService.nameSearch(wardName1.getValue().toLowerCase())==null){
                Notification emptyFieldError = new Notification("Ward name not in database", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else {
                Ward ward = wardService.nameSearch(wardName1.getValue().toLowerCase());
                Nurse nurse = nurseService.emailSearch(email);

                nurseService.deleteById(Math.round(nurse.getNurse_id()));
                nurse.setWard(ward);
                nurseService.save(nurse);

                wardName1.clear();
                nurseEmailIn.clear();
                wardName1.focus();
                nurseEmailIn.focus();

                Notification n = Notification.show("Nurse reassigned", 3000, Notification.Position.TOP_END);
                add(n);

            }
        });


    }

}
