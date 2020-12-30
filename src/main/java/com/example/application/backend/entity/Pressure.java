package com.example.application.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pressure {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long count;

    private double pLeft;
    private double pUnder;
    private double pRight;

    public Pressure(){};

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

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
