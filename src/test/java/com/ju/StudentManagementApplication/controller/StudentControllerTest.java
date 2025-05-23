package com.ju.StudentManagementApplication.controller;

import com.ju.StudentManagementApplication.entity.Student;
import com.ju.StudentManagementApplication.service.IStudentService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class StudentControllerTest {

    private final TestService service = new TestService();
    private final StudentController controller = new StudentController(service);

    @Test
    void shouldReturnAllStudents() {
        service.clearData();
        Student actual = getStudentDetails();
        service.addData(actual);

        ResponseEntity<List<Student>> students =controller.getAllStudents();

        List<Student> result = students.getBody();

        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .containsExactly(actual);

    }


    @Test
    void shouldReturnSpecificStudent() {
        service.clearData();
        Student actual = getStudentDetails();
        service.addData(actual);

        ResponseEntity<Student> student = controller.getStudentById(actual.getId());

        Student result = student.getBody();

        assertThat(result)
                .isNotNull()
                .isEqualTo(actual);
    }

    @Test
    void shouldCreateStudent() {
        service.clearData();
        Student actual = getStudentDetails();
        service.addData(actual);

        ResponseEntity<Student> response = controller.createStudent(actual);

        assertThat(response.getBody()).isEqualTo(actual);
    }

    @Test
    void shouldUpdateStudent() {
        service.clearData();
        Student actual = getStudentDetails();
        service.addData(actual);

        ResponseEntity<String> response = controller.updateStudentById(actual.getId(), actual);

        assertThat(response.getBody()).isEqualTo("Student Updated successfully!");
    }

    @Test
    void shouldDeleteStudent() {
        service.clearData();
        Student actual = getStudentDetails();
        service.addData(actual);

        ResponseEntity<String> response = controller.deleteStudentById(actual.getId());

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
