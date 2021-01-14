package com.example.application.backend.entity;

import com.example.application.backend.FormatString;

import javax.persistence.*;
import javax.validation.constraints.Email;

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


    //Maybe not a good idea to have it here (nobody should be able to read anyone's password)
    //Maybe and interface?
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


    @Override
    public String FormatName(String name) {
        StringBuilder sb = new StringBuilder();
        boolean space = true;

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
