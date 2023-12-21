package com.example.zion23182.services;

import com.example.zion23182.Model.User;
import com.example.zion23182.Repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository repo;
    public User saveOrUpdateUser(User User){return repo.save(User); }
    public User findOrderById(int id){return repo.findById(id).orElse(null); }
    public void deleteUser(User User){repo.delete(User);}
    public List<User>getAllUsers(){return repo.findAll();}
    @Transactional
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }
    public boolean appUserEmailExists(String email){
        return findByEmail(email).isPresent();
    }

    public User findUserById(int id){
        return repo.findById(id).get();
    }
    public List<User>findAllByRole(String role){return repo.findAllByRole(role);}

}
