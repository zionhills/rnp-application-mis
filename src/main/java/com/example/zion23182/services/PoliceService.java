package com.example.zion23182.services;

import com.example.zion23182.Model.Police;
import com.example.zion23182.Repo.PoliceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliceService {

    @Autowired
    private PoliceRepository repo;

    public List<Police> listAll() {
        return repo.findAll();
    }

    public void save(Police police) {
        repo.save(police);
    }

    public Police get(Long id) {
        return repo.findById(id).get();
    }

    @Transactional
    public Police findMember(String email) {
        return repo.findByEmail(email);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}
