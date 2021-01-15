package com.example.application.backend.service;

import com.example.application.backend.entity.Hospital;
import com.example.application.backend.entity.Nurse;
import com.example.application.backend.repository.NurseRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class NurseService {
    private static final Logger LOGGER = Logger.getLogger(NurseService.class.getName());
    private NurseRepo nurseRepo;


    public List<Nurse> findAll(){return nurseRepo.findAll();}
    public NurseService(NurseRepo nurseRepo){
        this.nurseRepo = nurseRepo;
    }
    public void deleteById(long id){
        nurseRepo.deleteById(id);
    }
    public Nurse getById(long id){
        return nurseRepo.getOne(id);
    }


    public void save(Nurse nurse){
        if (nurse == null){
            LOGGER.log(Level.SEVERE, "Nurse is null");
            return;
        }
        nurseRepo.save(nurse);
    }

    //Gets from the database the nurse object whose email is provided  (used instead of getById)
    //Calls a function in the Nurse repository
    public Nurse emailSearch(String email){
        return nurseRepo.search(email);
    }



}
