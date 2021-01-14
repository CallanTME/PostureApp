package com.example.application.backend.service;

import com.example.application.backend.entity.Hospital;
import com.example.application.backend.repository.HospitalRepo;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.logging.Level;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class HospitalService {
    private static final Logger LOGGER = Logger.getLogger(HospitalService.class.getName());
    private HospitalRepo hospitalRepo;


    public List<Hospital> findAll(){return hospitalRepo.findAll();}
    public HospitalService(HospitalRepo hospitalRepo){
        this.hospitalRepo = hospitalRepo;
    }
    public Hospital getById(long id){
        return hospitalRepo.getOne(id);
    }


    public void save(Hospital hospital){
        if (hospital == null){
            LOGGER.log(Level.SEVERE, "Patient is null");
            return;
        }
        hospitalRepo.save(hospital);
    }

     public Hospital zipsearch(String zipcode){
        return hospitalRepo.search(zipcode);
     }


}
