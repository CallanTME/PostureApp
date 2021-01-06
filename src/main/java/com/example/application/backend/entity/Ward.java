package com.example.application.backend.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int ward_id;
    private String ward_name;
    private int ward_size;

    @ManyToOne
    private Hospital hospital;

    @OneToMany
    private List<Nurse> nurse_list;

    @OneToMany
    private List<Bed> bed_list;



    public Ward(String ward_name, Hospital hospital){
        this.ward_name = ward_name;
        this.hospital = hospital;
    }

    public Ward(){}


    public int getWard_id() {
        return ward_id;
    }
    public String getWard_name(){return ward_name;}

    public void addNurse(Nurse n){
        this.nurse_list.add(n);
    }

    public void removeNurse(Nurse n){
        //removes the nurse in the list who has the same first, last name and email.
        this.nurse_list.remove(n);
    }

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

}
