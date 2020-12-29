package com.example.application.backend.entity;

import java.util.ArrayList;
import java.lang.Math;
import java.lang.System;


public class Pressure {


    private long status;
    //private int bradenscoretier;

    /* 15+ low risk, tier = 4
            13-14 moderate risk, tier = 3
            9-12 high risk, tier = 2
            below 9 severe risk, tier = 1
         */
    long startTime = System.currentTimeMillis();
    long elapsedTime;

    ArrayList<Double> pressure_right = new ArrayList<>();
    ArrayList<Double> pressure_left = new ArrayList<>();
    ArrayList<Double> pressure_right_average = new ArrayList<>();
    ArrayList<Double> pressure_left_average = new ArrayList<>();

    //Methods
    public Pressure() {

        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);
        pressure_right.add(10.0);

        pressure_left.add(2.0);
        pressure_left.add(2.0);
        pressure_left.add(2.0);
        pressure_left.add(2.0);
        pressure_left.add(2.0);
        pressure_left.add(2.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);
        pressure_left.add(10.0);


        System.out.println(pressure_right);
        System.out.println(pressure_left);
    }

    public void set_status(){

        for (int j = 0; j < (pressure_right.size() - 5); j++) {
            //Averages 5 pressure values from the right and stores the value in another arraylist

            Double sum_r = Double.valueOf(0);
            for (int i = j; i < j+5; i++) {

                sum_r = sum_r + pressure_right.get(i);
            }

            double average_r = sum_r / 5;
            //System.out.println(average_r);
            pressure_right_average.add(average_r);

            //Averages 5 pressure values from the left and stores the value in another arraylist

            Double sum_l = Double.valueOf(0);

            for (int n = j; n < j+5; n++) {

                sum_l = sum_l + pressure_left.get(n);
            }

            double average_l = sum_l / 5;
            //System.out.println(average_l);
            pressure_left_average.add(average_l);
        }

        System.out.println(pressure_right_average);
        System.out.println(pressure_left_average);

        for (int i = 1; i < pressure_right_average.size(); i++) {

            //for extreme position checking
            double threshold = 2.0;
            //Calculate difference between l and r, to check for extreme positions
            double diff = pressure_right_average.get(i) - pressure_left_average.get(i);
            System.out.println(diff);

            //Sets status to 100 if the patient is in an extreme position
            if (Math.abs(diff) >= threshold) {

                this.status = 100;
                System.out.println("Status is" + status);
            }

            else {

                //for duration checking
                double min_diff = 1.0;
                //the difference required between consecutive l or r readings for a meaningful position change
                int maxtime = 20000;

                //Checks whether position has changed by checking if the R or L readings have changed
                double change_r = pressure_right_average.get(i) - pressure_right_average.get(i - 1);
                double change_l = pressure_left_average.get(i) - pressure_left_average.get(i - 1);
                System.out.println(change_r);
                System.out.println(change_l);

                if ((Math.abs(change_r) >= min_diff) || (Math.abs(change_l) >= min_diff)) {

                    System.out.println("Patient has repositioned");
                    startTime = System.currentTimeMillis();
                    //Resets time if patient has repositioned
                    this.status = 0;
                    System.out.println("Status is" + status);
                }
                else {
                    elapsedTime = System.currentTimeMillis() - startTime;
                    long elapsedSeconds = elapsedTime / 1000;
                    long secondsDisplay = elapsedSeconds % 60;
                    long elapsedMinutes = elapsedSeconds / 60;
                    System.out.println("Patient has not moved in " + elapsedTime + " milliseconds");

                    /*switch (bradenscoretier){
                        case 1:

                        default:
                            status = ((elapsedTime/maxtime)*100);
                            this.status = status;

                    }*/

                }
            }

        }


    }

    public long get_status(){

        return status;
    }

    /*public void setBradenscoretier(int bradenscoretier) {

        this.bradenscoretier = bradenscoretier;
    }

    public int getBradenscoretier() {

        System.out.println(bradenscoretier);
        return bradenscoretier;
    }*/


    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setStartTime() {
        long startTime = System.currentTimeMillis();
        this.startTime = startTime;
        //This resets the start time e.g. when the nurse signs off
    }

    public long getStartTime() {
        return startTime;
    }


}
