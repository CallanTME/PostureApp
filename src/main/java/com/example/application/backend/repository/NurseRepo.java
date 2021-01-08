package com.example.application.backend.repository;

import com.example.application.backend.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NurseRepo extends JpaRepository<Nurse,Long> {
}
