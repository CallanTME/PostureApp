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
    private double count;
    private double status;


    @OneToOne
    private Patient patient;

    @Transient
    private Pressure previousPressure = new Pressure();

    @Transient
    private Pressure currentPressure = new Pressure();

    public Bed(double bedNum){
        this.bedNum = bedNum;
        isEmpty = true;
        // sets the id so it is the same as the bed number
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

    public void getPressureData() {
        //To establish a hard coded postgres DB connection we need the following variable types:
        Statement stmt = null;
        Connection c = null;

        //array to hold the data we extract from the DB
        double tempData[] = new double[3];


        //The heroku Postgres database was set up within Heroku
        try {
            Class.forName("org.postgresql.Driver");
            //log in details to gain access to the database:
            c = DriverManager.getConnection("jdbc:postgresql://ec2-52-208-138-246.eu-west-1.compute.amazonaws.com:5432/d74qrk7q3mi6tl", "rtphbsmoqjjuas", "4e02e853823c22eba9f167d1ebb7759e7dd2c21d50743c5995e95cc08f57307b");
            //c = DriverManager.getConnection("jdbc:postgresql://braden.ddns.net:4444/webApp ","braden", "ImperialBradenProject");

        } catch (Exception p) {
            p.printStackTrace();
            System.err.println(p.getClass().getName() + ": " + p.getMessage());
            System.exit(0);
        }
        //now connected to the heroku postgres database




        /*
        The real life device has 3 sensors (to the left,right and underneath the patient)
        so we will be taking the last 5 readings from each sensor and finding the average of them

        To replicate the pressure sensors giving us live readings we will contiually adjust
        the pressure readings table (called pressuretable) we will delete the oldest records of
        the bed data we are accessing and then insert random new ones every time the program
        goes through this part of code

         */


        try {
            //new sql statement:
            stmt = c.createStatement();
            //to find the average of the last 5 readings of each pressure sensor
            String sql = "select avg(\"left\") as avg1,avg(\"right\") as avg2,avg(under) as avg3 from(select \"left\",\"right\",under from pressuretable where bed_num = "+bedNum+" Order By id desc\n" +
                    "    limit 5)as notneeded";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                //assigning the pressure average values into the variables we created
                tempData[0] = rs.getDouble("avg1");
                tempData[1] = rs.getDouble("avg2");
                tempData[2] = rs.getDouble("avg3");
            }

            //the part that will replicate the live sensors:
            //1st to delete old rows to get rid of the oldest values since we are using the free
            //version of heroku there is a limit on rows we can have so will need to limit
            //the total number of rows we have at one time
            String deleteData = "delete from pressuretable where\n" +
                    "id=any(select id from pressuretable where bed_num = "+bedNum+"\n" +
                    "order by id asc limit 5)";
            stmt.executeUpdate(deleteData);

            //inserting 5 random values into each pressureTable sensor to replicate the real life sensors

            String addData = "insert into pressuretable (bed_num,\"left\",\"right\",under)\n" +
                    "select  "+ bedNum +", (random()*100)::int,  (random()*100)::int,(random()*100)::int from generate_series(1,5);";
            stmt.executeUpdate(addData);


            /*
            we also want to store pressure data for researchers and for the legal side.
            Due to limitations of free Heroku we can't save all the data as we would simply
            quickly run out of storage space. Below is the code used to create a view (virtual table)
            which will save all the pressure data.



            the below statement will save all of the incoming pressure data with its allocated
            patient info, without their name for confidentiality reasons.

            String researchStr = "create view info as"+
	        "select patient.bed_num,b_score,patient.id,is_empty,"left","right",under"+
	        "from bed,patient,pressuretable"+
            "where is_empty = false";
            stmt.executeUpdate(researchStr);

             */


            //close the database connection
            c.close();

        }catch (Exception f) {
            f.printStackTrace();
            System.err.println(f.getClass().getName() + ": " + f.getMessage());
            System.exit(0);
        }


        //we now want to save this to the pressure object.
        currentPressure.getVals(tempData);

    }

    public double getStatus() {
        return status;
    }

    public void setStatus() {
        if(previousPressure == null){
            previousPressure = currentPressure;
        }
        //Threshold for extreme position
        double threshold = 70;

        //Difference between current R and L pressures
        double diff = currentPressure.getpLeft() - currentPressure.getpRight();

        //Minimum difference for a patient to have moved
        double mindiff = 60;

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
            timeInPos = count*0.166667*60;
            // change position of low risk in 4h, *60 is to speed up by 60 times
            double tempStatus = (count/1440)*60*100;

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
            reset();
        }

    }

    // for when a patient changes position or is repositioned by the nurse
    public void reset(){

        count = 0;
        status = 0;
        timeInPos = 0;
    }

    // this runs periodically to get new data and update the status of each occupied bed
    public void update(){
        getPressureData();
        setStatus();
        previousPressure = currentPressure;
    }

    public double getCount() {
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
