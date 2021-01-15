package com.example.application.backend.entity;

import com.example.application.backend.FormatString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Patient implements FormatString {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private double bScore;

    public Patient(){

    }

    public Patient(String name, double bScore){
        //use a name formatting for easier data management
        this.name = FormatName(name);
        this.bScore = bScore;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        //use a name formatting for easier data management
       this.name = FormatName(name);
    }

    //The bradenscore characterises the likelihood a patient develops a pressure ulcere
    public double getbScore() {
        return bScore;
    }
    public void setbScore(double bScore) {
        this.bScore = bScore;
    }

    /*
    Calls the interface FormatString
    Formats the name entry before pushing the patient object in the database to keep clean data
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
}
