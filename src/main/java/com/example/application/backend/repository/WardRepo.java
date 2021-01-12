package com.example.application.backend.repository;

import com.example.application.backend.entity.Nurse;
import com.example.application.backend.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WardRepo extends JpaRepository<Ward,Long> {
    @Query("select w from Ward w " +
            "where ward_name like concat('%', :name, '%') ")
    //For future iteration, have the hospitalId in too; this page should be accessible only for the staff of a specific hospital
    Ward search(@Param("name") String nameToFind);
}
