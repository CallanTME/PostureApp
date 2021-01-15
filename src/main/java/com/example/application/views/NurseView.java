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

//The page is accessible by PostureApp/nurse_assignment
@Route("nurse_assignment")
@PageTitle("Nurse Assignment | Posture App")

public class NurseView extends VerticalLayout {
    //Services are declared as they communicate with the database
    private NurseService nurseService;
    private WardService wardService;

    //2 layouts, the first horizontal row is to create a nurse and assign her to a ward
    //The second layer enables to reassign a nurse
    private HorizontalLayout addNurse = new HorizontalLayout();
    private HorizontalLayout assignN = new HorizontalLayout();

    //Textfields where the user can write the entries
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
        //Adds the header of the page at the top middle
        add(new H1("Nurse Login and assigmnent"));

        //Assigns themes of the buttons
        addNurseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        assignNurseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);

        addNurse.setAlignItems(FlexComponent.Alignment.BASELINE);
        assignN.setAlignItems(FlexComponent.Alignment.BASELINE);
        //Adds the buttons and textfields to the horizontal layout
        addNurse.add(nameIn,nurseEmail, wardName,addNurseButton);
        assignN.add(nurseEmailIn, wardName1, assignNurseButton);

        //Adds subheaders and horizontal layers to the view in the correct order
        add(new H3("Add nurse to Database and assign nurse to ward"));
        add(addNurse);
        add(new H3("Reassign Nurse to ward"));
        add(assignN);


        //Clicklisterner determines the action effectuated whn a button is clicked
        addNurseButton.addClickListener(e ->{

            //Gets the email, put it as lower case
            String email= nurseEmail.getValue();
            email = email.toLowerCase();

            //c_email returns true if the email contains a '@'
            boolean c_email = false;
            for (int i=0;i<email.length();i++){
                if (email.charAt(i)=='@'){
                    c_email=true;
                }
            }


            //If all fields are not field, sends a notification
            if (nameIn.getValue().equals("") || nurseEmail.getValue().equals("") || wardName.getValue().equals("")) {
                Notification emptyFieldError = new Notification("Please fill in all fields", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            //If c_email is false (no '@' in the name), sends a notification
            else if (!c_email){
                Notification emptyFieldError = new Notification("Please enter a correct email", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            //If the name of the ward doesn't exist in the database, sends a notification
            else if(wardService.nameSearch(wardName.getValue().toLowerCase())==null){
                Notification emptyFieldError = new Notification("Ward name not in database", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            //Else enters the nurse in the database with all the fields entered
            else {
                Nurse nurse = new Nurse(nameIn.getValue(), nurseEmail.getValue());
                nurse.setWard(wardService.nameSearch(wardName.getValue().toLowerCase()));

                nurseService.save(nurse);

                //Empties the textfields for a new entry
                nameIn.clear();
                nurseEmail.clear();
                wardName.clear();
                nameIn.focus();
                nurseEmail.focus();
                wardName.focus();

                //Notifies the user the nurse was added to the database
                Notification n = Notification.show("Nurse added to the Database", 3000, Notification.Position.TOP_END);
                add(n);
            }


        });

        //Reassigns the nurse
        assignNurseButton.addClickListener(e ->{

            String email= nurseEmailIn.getValue();
            email = email.toLowerCase();

            //If all fields are not field, sends a notification
            if (wardName1.getValue().equals("")|| nurseEmailIn.getValue().equals("")) {
                Notification emptyFieldError = new Notification("Please fill in all fields", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            //If the Nurse's email doesn't exist in the database, sends a notification
            else if(nurseService.emailSearch(email)==null){
                Notification emptyFieldError = new Notification("This email is not in the database", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            //If the name of the ward doesn't exist in the database, sends a notification
            else if(wardService.nameSearch(wardName1.getValue().toLowerCase())==null){
                Notification emptyFieldError = new Notification("Ward name not in database", 3000, Notification.Position.TOP_END);
                emptyFieldError.open();
            }

            else {
                //Create new objects with the values of the fields entered
                Ward ward = wardService.nameSearch(wardName1.getValue().toLowerCase());
                Nurse nurse = nurseService.emailSearch(email);

                //delete the nurse in the database with the old ward value
                nurseService.deleteById(Math.round(nurse.getNurse_id()));
                //sets the nurse's ward
                nurse.setWard(ward);
                //create a new nurse in the database (same information in the fields as before but different ward
                nurseService.save(nurse);

                //Empties the textfields for a new entry
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
