package com.example.application.backend.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
Class that stores the ward for different hospitals
Not used at its full potential as of 13/01/2020
Future development : There will be different wards in the Ward view.
Nurses assigned to a ward will only be able to access their ward
 */


@Entity
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long ward_id;
    private String ward_name;
    private int ward_size;

    @ManyToOne
    private Hospital hospital;

    //These fields are no used yet. For future development, a ward will have specific beds and visible
    // in view specific to the ward and accessible only by the nurses assigned to the ward
    @OneToMany
    private List<Nurse> nurse_list;

    @OneToMany
    private List<Bed> bed_list;



    public Ward(String ward_name, Hospital hospital){
        //formats the ward name as lower cases
        ward_name=ward_name.toLowerCase();
        this.ward_name = ward_name;
        //a ward is defined for a hospital (the hospital ID is a foreign key)
        this.hospital = hospital;
    }

    public Ward(){}


    public long getWard_id() {
        return ward_id;
    }
    public String getWard_name(){return ward_name;}

    //Assigns nurse to the ward
    public void addNurse(Nurse n){
        this.nurse_list.add(n);
    }

    public void removeNurse(Nurse n){
        //removes the nurse in the list who has the same first, last name and email.
        this.nurse_list.remove(n);
    }

    //Adds and removes bed when the ward expands or reduces in size
    public void addBed(Bed b){
        this.bed_list.add(b);
    }
    public void removeBed(Bed b){
        this.bed_list.remove(b);
    }
    public int getWard_size() {
        return bed_list.size();
    }
    public List<Nurse> getNurse_list() {
        return nurse_list;
    }
    public List<Bed> getBed_list() {
        return bed_list;
    }
    public Hospital getHospital(){return hospital;}

}
