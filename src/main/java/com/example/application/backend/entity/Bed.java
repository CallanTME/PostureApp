package com.example.application.backend.entity;


import javax.persistence.*;

@Entity
public class Bed{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double bedNum;
    private boolean isEmpty;

    @OneToOne
    private Patient patient;

    public Bed(double bedNum){
        this.bedNum = bedNum;
        isEmpty = true;
    }

    public Bed(double bedNum, boolean isEmpty){
        this.bedNum = bedNum;
        this.isEmpty = isEmpty;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
