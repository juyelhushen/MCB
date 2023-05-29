package com.mcb.junit.service;

import com.mcb.entity.Groups;
import com.mcb.entity.Students;
import com.mcb.repository.StudentRepository;
import com.mcb.service.StudentService;
import com.mcb.wrapper.StudentRequest;
import com.mcb.wrapper.StudentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
public class StudentServiceComponentTest {

    @Autowired
    private StudentService service;

    @MockBean
    private StudentRepository repository;

    private Students students;

    @BeforeEach
    public void setUp() {
        Groups groups = new Groups();
        groups.setId(1);
        students = Students.builder()
                .studentId(1)
                .firstName("Munnuware")
                .lastName("Haque")
                .groups(groups)
                .build();
    }

    @DisplayName("JUnit Test for save student")
    @Test
    public void addStudentTest() {
        StudentRequest request = new StudentRequest();
        request.setFirstName("Sakil");
        request.setLastName("Hushen");
        request.setGroupId(1);
        ResponseEntity<?> response = service.addStudent(request);
        verify(repository, times(1)).save(any(Students.class));
        assertEquals("{\"message\":\"" + "Student added successfully." + "\"}", response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @DisplayName("JUnit Test for Student Update")
    @Test
    public void updateStudentTest() {
        int studentId = 1;
        StudentRequest request = new StudentRequest();
        request.setLastName("Hassan");
        when(repository.findById(studentId)).thenReturn(Optional.of(students));
        ResponseEntity<?> response = service.updateStudent(studentId, request);
        assertEquals("{\"message\":\"" + "Student id " + studentId + "'s has been updated successfully" + "\"}", response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(request.getLastName(), students.getLastName());
    }

    @DisplayName("Junit Test for Student Delete")
    @Test
    public void deleteStudentTest() {
        int studentId = 1;
        when(repository.findById(studentId)).thenReturn(Optional.of(students));
        ResponseEntity<?> response = service.deleteStudent(studentId);
        assertEquals("{\"message\":\"" + "Student id " + studentId + " has been removed from group." + "\"}", response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @DisplayName("JUnit Test for Get All Student")
    @Test
    public void getAllStudentTest() {
        when(repository.getAllStudent()).thenReturn(Stream.of(new StudentResponse(1, "Rahim", "Miah", 1),
                new StudentResponse(2, "Karthik", "Ariyan", 1)).collect(Collectors.toList()));
        assertEquals(2, Objects.requireNonNull(service.getAllStudent().getBody()).size());
    }

    @DisplayName("JUnit Test for get student By id")
    @Test
    public void getStudentByIdTest() {
        int studentId = 1;
        StudentResponse student = new StudentResponse(1, "Somz", "Sundharam", 1);
        when(repository.getStudentById(studentId)).thenReturn(Optional.of(student));
        ResponseEntity<?> response = service.getStudentById(studentId);
        assertEquals(student,response.getBody());
        assertEquals(HttpStatus.FOUND,response.getStatusCode());
    }

}
