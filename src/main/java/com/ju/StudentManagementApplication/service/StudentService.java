package com.ju.StudentManagementApplication.service;

import com.ju.StudentManagementApplication.entity.Student;
import com.ju.StudentManagementApplication.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IStudentService{

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public String createStudent(Student studentDetails) {
        repository.save(studentDetails);
        return "Student created and saved successfully!";
    }

    @Override
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Student with ID-"+id+" is not found!"));
    }

    @Override
    public String updateStudentById(Long id, Student studentDetails) {
        Student student = repository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Student with ID-"+id+" does not exist!"));

        updateStudentDetails(studentDetails, student);

        repository.save(student);

        return "Student with ID-"+id+" has been updated successfully!";
    }

    private static void updateStudentDetails(Student studentDetails, Student student) {
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setSemester(studentDetails.getSemester());
        student.setSgpa(studentDetails.getSgpa());
        student.setCgpa(studentDetails.getCgpa());
        student.setDateOfBirth(studentDetails.getDateOfBirth());
        student.setSection(studentDetails.getSection());
        student.setEnrollmentDate(studentDetails.getEnrollmentDate());
    }

    @Override
    public String deleteStudentById(Long id) {
        if(!repository.existsById(id)){
            throw new RuntimeException("Student with ID-"+id+" does not exist!");
        }
        repository.deleteById(id);
        return "Student with ID-"+id+" has been deleted successfully!";
    }
}
