package com.example.application.backend.repository;

import com.example.application.backend.entity.Nurse;
import com.example.application.backend.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WardRepo extends JpaRepository<Ward,Long> {
    /*
       Selects the ward with the ward name
       A name is unique to the ward in a specific hospital
       When several ward views will be created, a table join will need to join ward and hospital on the hospital ID
       in order to select the ward (with ward name) in a specific hospital (with ward ID)

       @Query("select w from Ward w " +
            "inner join Hospital h"+
            "on w.hospital_hosp_id = h.hosp_id"+
            "where ward_name like concat('%', :name, '%') +
            "and hosp_id = :hid")
    */
    @Query("select w from Ward w " +
            "where ward_name like concat('%', :name, '%') ")
    //For future iteration, have the hospitalId in too; this page should be accessible only for the staff of a specific hospital
    Ward search(@Param("name") String nameToFind);
}
