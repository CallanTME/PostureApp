package com.example.application.backend.entity;

import com.example.application.backend.FormatString;

import javax.persistence.*;

@Entity
public class Nurse implements FormatString {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int nurse_id;

    private String email;

    private String name;

    private String password;

    @ManyToOne
    private Ward ward;


    public Nurse(String name, String email) {
        this.name= FormatName(name);
        this.email=email;
    }

    public Nurse(){}

    //to make it easier, the email serves as Login (it is unique)
    public String getEmail() {
        return email;
    }
    //this is used to reset the email
    public void setEmail(String email) {
        this.email = email;
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

    public void setWardId(Ward ward) {
        this.ward = ward;
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
}
