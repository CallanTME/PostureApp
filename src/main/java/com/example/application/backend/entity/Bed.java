package com.example.application.backend.entity;
//import com.example.application.backend.entity.Pressure;


import com.example.application.backend.Pressure;

import javax.persistence.*;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;



@Entity
public class Bed{


    @Id
    private long id;

    private double bedNum;
    private boolean isEmpty;
    private double timeInPos;
    private int count;
    private double status;


    @OneToOne
    private Patient patient;

    @Transient
    private Pressure previousPressure = new Pressure(0,0,0);

    @Transient
    private Pressure currentPressure = new Pressure(0,0,0);


    public Bed(double bedNum){
        this.bedNum = bedNum;
        isEmpty = true;
        id = Math.round(bedNum);
        count = 0;
    }

    public Bed(double bedNum, boolean isEmpty){
        this.bedNum = bedNum;
        this.isEmpty = isEmpty;
        id = Math.round(bedNum);
        count = 0;
    }

    public Bed(){

    }

    /*
    public void getPressureData() {
        //establish the db connection:
        Statement stmt = null;
        Connection c = null;

        double tempData[] = new double[3];


        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "callan");
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

     */

    public double getStatus() {
        return status;
    }

    public void setStatus() {
        if(previousPressure == null){
            previousPressure = currentPressure;
        }
        //Threshold for extreme position
        double threshold = 2.0;

        //Difference between current R and L pressures
        double diff = currentPressure.getpLeft() - currentPressure.getpRight();

        //Minimum difference for a patient to have moved
        double mindiff = 1.0;

        //Differences between the current and previous R and L pressures
        double change_r = currentPressure.getpRight() - previousPressure.getpRight();
        double change_l = currentPressure.getpLeft() - previousPressure.getpLeft();

        //Checks for extreme pressure
        if (Math.abs(diff) >= threshold ) {

            status = 100;

        }

        //Checks if the patient has not moved
        else if ((Math.abs(change_r) <= mindiff) || (Math.abs(change_l) <= mindiff)){

            count = count + 1;
            // *60 is to speed up by 60 times
            setTimeInPos((double)count*(1/6)*60);
            // change position of low risk in 4h, *60 is to speed up by 60 times
            double tempStatus = ((double)count/1440)*60*100;

            if(patient.getbScore() >= 15)
            {
                status = tempStatus;
            }
            else if(patient.getbScore() == 13||patient.getbScore() == 14)
            {
                //change position in 3h 12m
                status = tempStatus*1.25;
            }
            else if(patient.getbScore() < 9)
            {
                //change position in 2h
                status = tempStatus*2;
            }
            else {
                //change position in 2h 40m
                status = tempStatus*1.5;
            }

            if(status > 100){
                status = 100;
            }
        }
        //If patient has moved
        else {

            count = 0;
            status = 0;
        }

    }

    public void update(){
        //getPressureData();
        setStatus();
        previousPressure = currentPressure;
    }

    public int getCount() {
        return count;
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
