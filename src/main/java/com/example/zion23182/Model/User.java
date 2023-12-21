package com.example.zion23182.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String fName;
    private String lName;
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    private String password;
    private String role;

    public User() {
    }

    public User(String fName, String lName, String phoneNumber, String email, String password, String role) {
        this.fName = fName;
        this.lName = lName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
