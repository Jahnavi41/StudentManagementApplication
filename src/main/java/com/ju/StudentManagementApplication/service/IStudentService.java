package com.ju.StudentManagementApplication.service;

import com.ju.StudentManagementApplication.entity.Student;

import java.util.List;

public interface IStudentService {
    String createStudent(Student studentDetails);
    List<Student> getAllStudents();
    Student getStudentById(Long id);
    String updateStudentById(Long id, Student studentDetails);
    String deleteStudentById(Long id);
}
