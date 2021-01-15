package com.example.application.backend.entity;

import com.example.application.backend.FormatString;

import javax.persistence.*;
import javax.validation.constraints.Email;

/*
Class that stores the Nurses
Not used at its full potential as of 13/01/2020
Future development : Each Nurse will be able to only see with their login and password the ward they are assigned to
 */


@Entity
public class Nurse implements FormatString {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long nurse_id;
    private String email;
    private String name;
    private String password;

    @ManyToOne
    private Ward ward;


    public Nurse(String name, String email) {
        this.name= FormatName(name);

        //checks if the email is in a correct format
        if(correct_email(email)){
            this.email=email.toLowerCase();
        }
        else{
            System.err.println("Incorect Email Format");
        }
    }

    public Nurse(){}

    public String getEmail() {
        return email;
    }

    //assigns an email if the format is correct
    public void setEmail(String email) {
        if(correct_email(email)){
            this.email=email.toLowerCase();
        }
        else{
            System.err.println("Incorect Email Format");
        }

    }

    public String getName() {
        return name;
    }
    public void setName(String email) {
        this.name = FormatName(name);
    }


    /*The password is not used yet in the program as there is only one user type
    Further development: create another secured class with the password and an id (not the login);
    keeps the login detail and password in 2 different class to enhance security
    */

    public String getPassword() {
        return password;
    }
    //useful when resetting password
    public void setPassword(String password) {
        this.password = password;
    }

    public Ward getWard() {
        return ward;
    }
    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public long getNurse_id() {
        return nurse_id;
    }
    public void setNurse_id(long nurse_id) {
        this.nurse_id = nurse_id;
    }

    /*
    Calls the interface FormatString
    Formats the name entry before pushing the nurse object in the database to keep clean data
    Enables easier queries
    All the letters are lower case except the first letter of each word
     */
    @Override
    public String FormatName(String name) {
        StringBuilder sb = new StringBuilder();
        boolean space = true;

        //puts all the letters in a string builder with the required case type
        for (char c : name.toCharArray()){
            if(Character.isLetter(c)){
                if(space){
                    c= Character.toTitleCase(c);
                }
                else{
                    c= Character.toLowerCase(c);
                }
                space=false;
            }
            else{
                space=true;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public String FormatPostcode(String postcode) {
        return null;
    }

    //Checks if there is a '@' in teh email, and returns true if there is (email correct)
    public boolean correct_email(String email){
        boolean c_email = false;

        for (int i=0;i<email.length();i++){
            if (email.charAt(i)=='@'){
                c_email=true;
            }
        }
        return c_email;
    }
}
