package com.example.application.backend.entity;

import java.util.ArrayList;
import java.lang.Math;

public class Pressure {


    public int status;

    ArrayList<Double> pressure_right = new ArrayList<>();
    ArrayList<Double> pressure_left = new ArrayList<>();
    ArrayList<Double> pressure_right_average = new ArrayList<>();
    ArrayList<Double> pressure_left_average = new ArrayList<>();

    //Methods
    public Pressure() {

        pressure_right.add(10.0);
        pressure_right.add(9.0);
        pressure_right.add(8.0);
        pressure_right.add(7.0);
        pressure_right.add(6.0);
        pressure_right.add(5.0);
        pressure_right.add(4.0);
        pressure_right.add(3.0);
        pressure_right.add(2.0);
        pressure_right.add(1.0);

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
            for (int i = j; i < 5; i++) {

                sum_r = sum_r + pressure_right.get(i);
            }

            double average_r = sum_r / 5;
            System.out.println(average_r);
            pressure_right_average.add(average_r);

            //Averages 5 pressure values from the left and stores the value in another arraylist

            Double sum_l = Double.valueOf(0);

            for (int i = j; i < 5; i++) {

                sum_l = sum_l + pressure_left.get(i);
            }

            double average_l = sum_l / 5;
            System.out.println(average_l);
            pressure_left_average.add(average_l);
        }

        System.out.println(pressure_right_average);
        System.out.println(pressure_left_average);

        //Calculate difference between l and r, to check for extreme positions

        for (int i = 0; i < pressure_right_average.size(); i++) {

            double threshold = 2.0;
            double diff = pressure_right_average.get(i) - pressure_left_average.get(i);
            System.out.println(diff);

            if (Math.abs(diff) >= threshold) {

                this.status = 100;
            }

        }
    }

    public int get_status(){
        return status;
    }



    /*public boolean get_position_time() {
        return position_time;
    }*/

}
