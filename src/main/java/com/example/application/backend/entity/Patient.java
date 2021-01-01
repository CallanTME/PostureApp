package com.example.application.backend.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;
    private double bScore;

    //private double bedNum;




    public Patient(){

    }

    public Patient(String name, double bScore/*, double bedNum*/){
        //use a name formatting for easier data management
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

        this.name = sb.toString();
        this.bScore = bScore;
        //this.bedNum = bedNum;

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

        this.name = sb.toString();
    }

    public double getbScore() {
        return bScore;
    }

    public void setbScore(double bScore) {
        this.bScore = bScore;
    }

    /*
    public double getBedNum() {
        return bedNum;
    }

    public void setBedNum(double bedNum) {
        this.bedNum = bedNum;
    }

     */
}
