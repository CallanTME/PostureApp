package com.example.application.backend.entity;
//import com.example.application.backend.entity.Pressure;


import com.example.application.backend.Pressure;

import javax.persistence.*;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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

    public void getPressureData() {
        //establish the db connection:
        Statement stmt = null;
        Connection c = null;

        double tempData[] = new double[3];


        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "06AL12du");
        } catch (Exception p) {
            p.printStackTrace();
            System.err.println(p.getClass().getName() + ": " + p.getMessage());
            System.exit(0);
        }


        try {
            stmt = c.createStatement();
            String sql = "select avg(left),avg(right),avg(under)\n" +
                    "from(select left,right,under\n" +
                    "     from Info\n" +
                    "     Order By ProductID desc\n" +
                    "     limit 10\n" +
                    "     where bed_num=" + bedNum + "\t);";
            ResultSet rs = stmt.executeQuery(sql);

            tempData[0] = rs.getDouble("avg(left)");
            tempData[1] = rs.getDouble("avg(right)");
            tempData[2] = rs.getDouble("avg(under)");


        }catch (Exception f) {
            f.printStackTrace();
            System.err.println(f.getClass().getName() + ": " + f.getMessage());
            System.exit(0);
        }


        //we now want to save this to the pressure object.
        currentPressure.getVals(tempData);

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

    public Pressure getPreviousPressure() {
        return previousPressure;
    }

    public Pressure getCurrentPressure() {
        return currentPressure;
    }

}
