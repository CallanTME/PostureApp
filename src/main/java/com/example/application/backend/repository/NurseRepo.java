package com.example.application.backend.repository;

import com.example.application.backend.entity.Hospital;
import com.example.application.backend.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NurseRepo extends JpaRepository<Nurse,Long> {
    /*
        Selects the nurse with the specific email
        A email is unique to the nurse so there is only one result
        Enables to get an nurse by email in the view rather than id (which can change and is not known by the user)
     */
    @Query("select n from Nurse n " +
            "where email like concat('%', :mail, '%') ")
    Nurse search(@Param("mail") String emailToFind);
}
