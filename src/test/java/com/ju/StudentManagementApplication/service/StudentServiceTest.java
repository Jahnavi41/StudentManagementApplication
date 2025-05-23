package com.ju.StudentManagementApplication.service;

import com.ju.StudentManagementApplication.entity.Student;
import com.ju.StudentManagementApplication.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    private final StudentRepository repository = mock(StudentRepository.class);
    private final StudentService service = new StudentService(repository);
    private Student sampleStudent;

    @BeforeEach
    void setUp() {
        sampleStudent = getStudentDetails();
    }

    @Test
    void createStudent_shouldSaveAndReturnStudent() {
        when(repository.save(sampleStudent)).thenReturn(sampleStudent);

        Student student = service.createStudent(sampleStudent);

        verify(repository).save(sampleStudent);
        assertThat(student)
                .isNotNull()
                .isEqualTo(sampleStudent);
    }

    @Test
    void getAllStudentsAndReturnList() {
        List<Student> studentList = List.of(sampleStudent);
        when(repository.findAll()).thenReturn(studentList);

        List<Student> result = service.getAllStudents();

        verify(repository).findAll();
        assertThat(result).isEqualTo(studentList);
    }

    @Test
    void getSpecificStudentAndReturn() {
        Long id = sampleStudent.getId();
        when(repository.findById(id)).thenReturn(Optional.of(sampleStudent));

        Student result = service.getStudentById(id);

        verify(repository).findById(id);
        assertThat(result).isEqualTo(sampleStudent);
    }

    @Test
    void getStudentById_shouldThrowExceptionWhenNotFound() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.getStudentById(id));

        assertThat(exception.getMessage()).isEqualTo("Student with ID-" + id + " is not found!");
    }

    @Test
    void updateStudentById_shouldUpdateStudentAndReturnMessage() {
        Long id = sampleStudent.getId();
        Student updateDetails = getUpdateDetails();
        when(repository.findById(id)).thenReturn(Optional.of(sampleStudent));

        String result = service.updateStudentById(id, updateDetails);

        verify(repository).findById(id);
        verify(repository).save(sampleStudent);
        assertThat(result).isEqualTo("Student with ID-" + id + " has been updated successfully!");
        assertThat(sampleStudent.getFirstName()).isEqualTo(updateDetails.getFirstName());
    }

    @Test
    void deleteStudentById_shouldDeleteStudent() {
        Long id = sampleStudent.getId();
        when(repository.existsById(id)).thenReturn(true);

        String result = service.deleteStudentById(id);

        verify(repository).existsById(id);
        verify(repository).deleteById(id);
        assertThat(result).isEqualTo("Student with ID-" + id + " has been deleted successfully!");
    }

    @Test
    void deleteStudentById_shouldThrowExceptionIfStudentDoesNotExist() {
        Long id = 404L;
        when(repository.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.deleteStudentById(id));

        assertThat(exception.getMessage()).isEqualTo("Student with ID-" + id + " does not exist!");
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

    private static Student getUpdateDetails() {
        return new Student(
                1L,
                "J",
                "U.",
                LocalDate.of(2003, 10, 10),
                LocalDate.of(2025, 5, 23),
                2,
                "B",
                "ju@gmail.com",
                new BigDecimal("8.5"),
                new BigDecimal("8.2")
        );
    }
}
