package com.mcb.junit.service;


import com.mcb.entity.Subjects;
import com.mcb.repository.SubjectRepository;
import com.mcb.service.SubjectService;
import com.mcb.wrapper.SubjectRequest;
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
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SubjectServiceComponentTest {

    @MockBean
    private SubjectRepository repository;

    @Autowired
    private SubjectService subjectService;

    private Subjects subjects;

    @BeforeEach
    public void setUp() {
        subjects = Subjects.builder()
                .subjectId(1)
                .title("English")
                .build();
    }

    @DisplayName("JUnit Test for save subject")
    @Test
    public void saveSubjectTest() {
        SubjectRequest request = new SubjectRequest();
        request.setTitle("Java Fundamental");
        ResponseEntity<String> response = subjectService.addSubject(request);
        verify(repository,times(1)).save(any(Subjects.class));
        assertEquals("{\"message\":\""+"Subject Successfully created."+"\"}", response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @DisplayName("Junit test for invalid data in save Subjects")
    @Test
    public void testAddSubjectNullRequest() {
        SubjectRequest request = null;
        ResponseEntity<String> response = subjectService.addSubject(request);

        verify(repository, never()).save(any(Subjects.class));
        assertEquals("{\"message\":\""+"Invalid data"+"\"}", response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }



    @DisplayName("JUnit Test for update subjects by id")
    @Test
    public void updateSubjectTest() {
        int id = 1;
        SubjectRequest request = new SubjectRequest();
        request.setTitle("Mathematics");
        subjects.setTitle(request.getTitle());
        when(repository.findById(id)).thenReturn(Optional.of(subjects));
        ResponseEntity<String> response = subjectService.updateSubject(id,request);
        assertEquals("{\"message\":\""+"Subject has been updated."+"\"}", response.getBody());
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertEquals(request.getTitle(),subjects.getTitle());
    }


    @DisplayName("JUnit Test for delete subjects by id")   //1
    @Test
    public void deleteSubjectTest() {
        int id = 1;
        when(repository.findById(id)).thenReturn(Optional.of(subjects));
        ResponseEntity<String> response = subjectService.deleteSubject(id);
        verify(repository, times(1)).deleteById(id);  //1
        assertEquals("{\"message\":\""+"Subjects has removed from list."+"\"}", response.getBody());  //2
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); //3
    }

    @DisplayName("JUnit Test for get subject by id")
    @Test
    public void getSubjectsByIdTest() {
        int id = 1;
        when(repository.findById(id)).thenReturn(Optional.of(subjects));
        ResponseEntity<?> response = subjectService.getSubjectById(id);
        assertEquals(subjects, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @DisplayName("JUnit Test for get all Subjects")
    @Test
    public void getAllSubjectsTest() {
        when(repository.findAll()).thenReturn(Stream.of(new Subjects(1, "English"),
                new Subjects(2, "Physics")).collect(Collectors.toList()));
        assertEquals(2, Objects.requireNonNull(subjectService.getAllSubjects().getBody()).size());
    }

}
