package com.example.zion23182.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Police {

    @GeneratedValue
    @Id
    private Long id;
    @NotBlank(message = "First Name is required!")
    private String firstName;
    @NotBlank(message = "Last Name is required!")
    private String lastName;
    @NotBlank(message = "Address is required!")
    private String address;
    @NotBlank(message = "Email is required!")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Martial Status is required!")
    private String martialStatus;
    @NotBlank(message = "Gender is mandatory")
    private String gender;
    @NotBlank(message = "Phone is mandatory")
    private String phone;
    private String dob;

}
