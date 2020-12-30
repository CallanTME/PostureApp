package com.example.application.backend.entity;


import com.example.application.backend.Pressure;

import javax.persistence.*;


@Entity
public class Bed{


    @Id
    private long id;

    private double bedNum;
    private boolean isEmpty;
    private double timeInPos;

    @OneToOne
    private Patient patient;

    @Transient
    private Pressure previousPressure;

    @Transient
    private Pressure currentPressure;


    public Bed(double bedNum){
        this.bedNum = bedNum;
        isEmpty = true;
        timeInPos = 0;
        id = Math.round(bedNum);
    }

    public Bed(double bedNum, boolean isEmpty){
        this.bedNum = bedNum;
        this.isEmpty = isEmpty;
        timeInPos = 0;
        id = Math.round(bedNum);
    }

    public Bed(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public double getBedNum() {
        return bedNum;
    }

    public void setBedNum(double bedNum) {
        this.bedNum = bedNum;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty() {
        isEmpty = true;
    }

    public void setFull(){
        isEmpty = false;
    }

    public double getTimeInPos() {
        return timeInPos;
    }

    public void setTimeInPos(double timeInPos) {
        this.timeInPos = timeInPos;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
