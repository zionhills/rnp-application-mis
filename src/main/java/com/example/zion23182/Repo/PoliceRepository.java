package com.example.zion23182.Repo;

import com.example.zion23182.Model.Police;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PoliceRepository extends JpaRepository<Police,Long> {
    Police findByEmail(String email);
}
