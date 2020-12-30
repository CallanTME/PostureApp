package com.example.application.backend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Pressure {

    private double pLeft;
    private double pUnder;
    private double pRight;

    public Pressure(){};

    public double getpLeft() {
        return pLeft;
    }

    public void setpLeft(double pLeft) {
        this.pLeft = pLeft;
    }

    public double getpUnder() {
        return pUnder;
    }

    public void setpUnder(double pUnder) {
        this.pUnder = pUnder;
    }

    public double getpRight() {
        return pRight;
    }

    public void setpRight(double pRight) {
        this.pRight = pRight;
    }
}
