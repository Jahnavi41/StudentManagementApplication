package com.ju.StudentManagementApplication.service;

import com.ju.StudentManagementApplication.entity.Student;
import com.ju.StudentManagementApplication.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IStudentService{

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public String createStudent(Student studentDetails) {
        studentRepository.save(studentDetails);
        return "Student created and saved successfully!";
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Student with ID-"+id+" is not found!"));
    }

    @Override
    public String updateStudentById(Long id, Student studentDetails) {
        Student student = studentRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Student with ID-"+id+" does not exist!"));

        updateStudentDetails(studentDetails, student);

        studentRepository.save(student);

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
        if(!studentRepository.existsById(id)){
            throw new RuntimeException("Student with ID-"+id+" does not exist!");
        }
        studentRepository.deleteById(id);
        return "Student with ID-"+id+" has been deleted successfully!";
    }
}
