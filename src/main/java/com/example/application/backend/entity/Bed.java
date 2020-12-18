package com.example.application.backend.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double bedNum;
    private boolean isEmpty;


    public Bed(int bedNum){
        this.bedNum = bedNum;
        isEmpty = true;
    }

    public Bed(int bedNum, boolean isEmpty){
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
}
