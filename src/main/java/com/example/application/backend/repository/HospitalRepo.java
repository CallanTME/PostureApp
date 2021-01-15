package com.example.application.backend.repository;

import com.example.application.backend.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HospitalRepo extends JpaRepository<Hospital,Long> {
    /*
        Selects the hospital with the specific zipcode
        A zipcode is unique so there is only one result
        Enables to get an hospital by its zipcode in the view rather than its id (which can change and is not known by the user)
     */

    @Query("select h from Hospital h " +
            "where zipcode like concat('%', :zip, '%') ")
    Hospital search(@Param("zip") String zipToFind);
}
