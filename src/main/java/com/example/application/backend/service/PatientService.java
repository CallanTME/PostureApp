package com.example.application.backend.service;

import com.example.application.backend.entity.Patient;
import com.example.application.backend.repository.PatientRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

// This class acts as a middle layer between the repository and the UI
@Service
public class PatientService {
    private static final Logger LOGGER = Logger.getLogger(PatientService.class.getName());
    private PatientRepo patientRepo;

    public PatientService(PatientRepo patientRepo){
        this.patientRepo = patientRepo;
    }
    public List<Patient> findAll(){
        return patientRepo.findAll();
    }

    public long count(){
        return patientRepo.count();
    }

    public void delete(Patient patient){
        patientRepo.delete(patient);
    }

    public Patient getById(long id){
        return patientRepo.getOne(id);
    }

    public Optional<Patient> findById(long id){
        return patientRepo.findById(id);
    }

    public void save(Patient patient){
        if (patient == null){
            LOGGER.log(Level.SEVERE, "Patient is null");
            return;
        }
        patientRepo.save(patient);
    }
}
