package com.ju.StudentManagementApplication.controller;

import com.ju.StudentManagementApplication.entity.Student;
import com.ju.StudentManagementApplication.service.IStudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DisplayName("Student Controller Tests")
public class StudentControllerTest {

    private final TestService service = new TestService();
    private final StudentController controller = new StudentController(service);
    Student sampleStudent;

    @BeforeEach
    void setUp() {
        service.clearData();
        sampleStudent = getStudentDetails();
        service.addData(sampleStudent);
    }

    @Test
    void shouldReturnAllStudents() {
        ResponseEntity<List<Student>> students =controller.getAllStudents();

        List<Student> result = students.getBody();

        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .containsExactly(sampleStudent);
    }


    @Test
    void shouldReturnSpecificStudent() {
        ResponseEntity<Student> student = controller.getStudentById(sampleStudent.getId());

        Student result = student.getBody();

        assertThat(result)
                .isNotNull()
                .isEqualTo(sampleStudent);
    }

    @Test
    void shouldCreateStudent() {
        ResponseEntity<Student> response = controller.createStudent(sampleStudent);
        assertThat(response.getBody()).isEqualTo(sampleStudent);
    }

    @Test
    void shouldUpdateStudent() {
        ResponseEntity<String> response = controller.updateStudentById(sampleStudent.getId(), sampleStudent);
        assertThat(response.getBody()).isEqualTo("Student Updated successfully!");
    }

    @Test
    void shouldDeleteStudent() {
        ResponseEntity<String> response = controller.deleteStudentById(sampleStudent.getId());
        assertThat(response.getBody()).isEqualTo("Student Deleted successfully!");
    }

    private static Student getStudentDetails() {
        return new Student(
                1L,
                "Jahnavi",
                "Undela",
                LocalDate.of(2003, 10, 10),
                LocalDate.of(2025, 5, 23),
                1,
                "A",
                "abc@gmail.com",
                new BigDecimal(0),
                new BigDecimal(0)
        );
    }

    public static class TestService implements IStudentService {

        List<Student> data = new ArrayList<>();

        void clearData() {
            data.clear();
        }

        void addData(Student student) {
            data.add(student);
        }

        @Override
        public Student createStudent(Student studentDetails) {
            addData(studentDetails);
            return data.getLast();
        }

        @Override
        public List<Student> getAllStudents() {
            return data;
        }

        @Override
        public Student getStudentById(Long id) {
            for(Student i : data) {
                if(i.getId().equals(id)) {
                    return i;
                }
            }
            return null;
        }

        @Override
        public String updateStudentById(Long id, Student studentDetails) {
            for(int i =0; i<data.size();i++) {
                if(data.get(i).getId().equals(id)) {
                    data.set(i, studentDetails);
                    return "Student Updated successfully!";
                }
            }
            return "Student not found!";
        }

        @Override
        public String deleteStudentById(Long id) {
            for(int i =0; i<data.size();i++) {
                if(data.get(i).getId().equals(id)) {
                    data.remove(i);
                    return "Student Deleted successfully!";
                }
            }
            return "Student not found!";
        }
    }
}
