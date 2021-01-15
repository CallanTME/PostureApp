package com.example.application.backend.service;

import com.example.application.backend.entity.Hospital;
import com.example.application.backend.repository.HospitalRepo;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.logging.Level;
import java.util.Optional;
import java.util.logging.Logger;

/*
The service class is used to communicate with the repository (database) instead of the entity
 */
@Service
public class HospitalService {
    private static final Logger LOGGER = Logger.getLogger(HospitalService.class.getName());
    private HospitalRepo hospitalRepo;


    //Returns the list of all the hospital in the database
    public List<Hospital> findAll(){return hospitalRepo.findAll();}
    public HospitalService(HospitalRepo hospitalRepo){
        this.hospitalRepo = hospitalRepo;
    }

    //Gets the hospital object from the database with its id
    public Hospital getById(long id){
        return hospitalRepo.getOne(id);
    }


    //Saves hospital in the database if the object is not null;
    public void save(Hospital hospital){
        if (hospital == null){
            LOGGER.log(Level.SEVERE, "Patient is null");
            return;
        }
        hospitalRepo.save(hospital);
    }

    //Get  from the database the hospital object whose zipcode is provided (used instead of getById)
    //Calls a function in the repository
     public Hospital zipsearch(String zipcode){
        return hospitalRepo.search(zipcode);
     }


}
