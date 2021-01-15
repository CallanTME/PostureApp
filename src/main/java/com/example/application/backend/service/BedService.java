package com.example.application.backend.service;

import com.example.application.backend.entity.Bed;
import com.example.application.backend.repository.BedRepo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// This class acts as a middle layer between the repository and the UI
@Service
public class BedService {

    // These methods call the repos methods
    private BedRepo bedRepo;

    public BedService(BedRepo bedRepo){
        this.bedRepo = bedRepo;
    }

    public List<Bed> findAll(){
        return bedRepo.findAll();
    }

    public Optional<Bed> findById(long id){
        return bedRepo.findById(id);
    }

    public void deleteAll(){
        bedRepo.deleteAll();
    }

    public void deleteByBedNum(double bedNum){
        ArrayList<Bed> bedList = new ArrayList<Bed>();
        bedRepo.findAll().stream().forEach(bedList::add);
        for(Bed bed : bedList){
            if (bed.getBedNum() == bedNum){
                bedRepo.delete(bed);
            }
        }
    }

    public Bed getByBedNum(double bedNum){
        ArrayList<Bed> bedList = new ArrayList<Bed>();
        bedRepo.findAll().stream().forEach(bedList::add);
        for(Bed bed : bedList){
            if (bed.getBedNum() == bedNum){
                return bed;
            }
        }
        return null;
    }

    public void save(Bed bed){
        bedRepo.save(bed);
    }

    // This creates 9 empty beds when the program is first run
    @PostConstruct
    public void setUpBeds(){
        if(bedRepo.count() == 0){
            bedRepo.saveAll(
                    Stream.of(1,2,3,4,5,6,7,8,9)
                    .map(Bed::new)
                    .collect(Collectors.toList()));
        }
    }
}
