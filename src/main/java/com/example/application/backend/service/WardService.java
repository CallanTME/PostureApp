
package com.example.application.backend.service;

import com.example.application.backend.entity.Ward;
import com.example.application.backend.repository.WardRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class WardService {
    private static final Logger LOGGER = Logger.getLogger(WardService.class.getName());
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

}
