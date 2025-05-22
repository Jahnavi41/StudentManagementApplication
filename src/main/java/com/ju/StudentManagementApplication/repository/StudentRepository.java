package com.ju.StudentManagementApplication.repository;

import com.ju.StudentManagementApplication.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
