package com.example.application.backend.repository;

import com.example.application.backend.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WardRepo extends JpaRepository<Ward,Long> {
}
