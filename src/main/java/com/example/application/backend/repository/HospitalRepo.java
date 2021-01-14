package com.example.application.backend.repository;

import com.example.application.backend.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HospitalRepo extends JpaRepository<Hospital,Long> {
    @Query("select h from Hospital h " +
            "where zipcode like concat('%', :zip, '%') ")
    Hospital search(@Param("zip") String zipToFind);
}
