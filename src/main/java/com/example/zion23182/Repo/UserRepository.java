package com.example.zion23182.Repo;

import com.example.zion23182.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer>{

    List<User> findAllByRole(String role);

   Optional<User> findByEmail(String username);
    
}
