
package com.example.application.backend.service;

import com.example.application.backend.entity.Nurse;
import com.example.application.backend.entity.Ward;
import com.example.application.backend.repository.WardRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class WardService {
    private static final Logger LOGGER = Logger.getLogger(WardService.class.getName());
    //declares a ward repo as the service communicates with the repositpory (database)
    private WardRepo wardRepo;

    public List<Ward> findAll(){return wardRepo.findAll();}
    public WardService(WardRepo wardRepo){
        this.wardRepo = wardRepo;
    }
    public Ward getById(long id){
        return wardRepo.getOne(id);
    }


    public void save(Ward ward){
        if (ward == null){
            LOGGER.log(Level.SEVERE, "Ward is null");
            return;
        }
        wardRepo.save(ward);
    }

    //Gets  from the database the ward object whose name is provided (used instead of getById)
    //Calls a function in the repository
    public Ward nameSearch(String name){
        return wardRepo.search(name);
    }

}
