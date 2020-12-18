package com.example.application.backend.repository;

import com.example.application.backend.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedRepo extends JpaRepository<Bed,Long> {
}
