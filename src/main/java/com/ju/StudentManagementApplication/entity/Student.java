package com.ju.StudentManagementApplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private LocalDate enrollmentDate;

    @Column(nullable = false)
    private int semester;

    @Column(nullable = false)
    private char section;

    @Column(nullable = false)
    private String email;

    @Column(precision = 3, scale=2)
    private BigDecimal sgpa;

    @Column(precision=3, scale=2)
    private BigDecimal cgpa;
}
