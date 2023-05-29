package com.mcb.junit.service;

import com.mcb.entity.Marks;
import com.mcb.entity.Students;
import com.mcb.entity.Subjects;
import com.mcb.repository.MarkRepository;
import com.mcb.service.MarkService;
import com.mcb.wrapper.MarkRequest;
import com.mcb.wrapper.MarkResponse;
import com.mcb.wrapper.StudentMarkResponse;
import com.mcb.wrapper.SubjectMarkResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class MarkServiceComponentTest {

    @MockBean
    private MarkRepository repository;

    @Autowired
    private MarkService service;

    private Marks marks;

    @BeforeEach
    public void setUp() {
        Students student = new Students();
        student.setStudentId(1);
        Subjects subjects = new Subjects();
        subjects.setSubjectId(1);
        marks = Marks.builder()
                .markId(1)
                .mark(90)
                .date(LocalDateTime.parse("2023-04-16T05:52:51.447"))
                .students(student)
                .subjects(subjects)
                .build();
    }


    @DisplayName("JUnit Test for saving Marks")
    @Test
    public void addMarkTest() {
        MarkRequest request = new MarkRequest();
        request.setMark(90);
        request.setDate(LocalDateTime.parse("2023-04-16T05:52:51.447"));
        request.setStudentId(2);
        request.setSubjectId(3);
        ResponseEntity<String> response = service.addMark(request);
        verify(repository,times(1)).save(any(Marks.class));
        assertEquals("{\"message\":\"" + "Marks successfully added." + "\"}", response.getBody());
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }


    @DisplayName("JUnit Test for Marks Update")
    @Test
    public void updateMarksTest() {
        int id = 1;
        MarkRequest request = new MarkRequest();
        request.setMark(79);
        marks.setMark(request.getMark());
        when(repository.findById(id)).thenReturn(Optional.of(marks));
        ResponseEntity<String> response = service.updateMark(id, request);
        assertEquals("{\"message\":\"" + "Successfully updated students marks" + "\"}", response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(request.getMark(), marks.getMark());

    }

    @DisplayName("Junit Test for Delete Mark")
    @Test
    public void deleteMarkTest() {
        int id = 1;
        when(repository.findById(id)).thenReturn(Optional.of(marks));
        ResponseEntity<String> response = service.deleteMarks(id);
        verify(repository, times(1)).deleteById(id);
        assertEquals("{\"message\":\"" + "Marks has been removed." + "\"}", response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @DisplayName("JUnit Test for Mark By Id")
    @Test
    public void getMarkByIdTest() {
        int id = 1;
        MarkResponse markResponse = new MarkResponse(1,LocalDateTime.parse("2023-03-16T05:52:51.447"),78,2,2);
        when(repository.findMarkById(id)).thenReturn(Optional.of(markResponse));
        ResponseEntity<?> response = service.getMarkById(id);
        assertEquals(markResponse,response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("JUnit Test for Get All Mark Details")
    @Test
    public void getAllMarkTest() {
        when(repository.getAllMarks()).thenReturn(Stream.of(new MarkResponse(1, LocalDateTime.parse("2023-04-16T05:52:51.447"),
                30, 1, 1), new MarkResponse(2, LocalDateTime.parse("2023-03-16T05:52:51.447"),
                56, 2, 1)).collect(Collectors.toList()));
        assertEquals(2, Objects.requireNonNull(service.getAllMarks().getBody()).size());
    }

    @DisplayName("JUnit Test for the mark by student id")
    @Test
    public void getMarkStudentByIdTest() {
        int studentId = 1;
        List<StudentMarkResponse> markList = List.of(new StudentMarkResponse(56),new StudentMarkResponse(80));
        when(repository.getMarkByStudentId(studentId)).thenReturn(markList);
        ResponseEntity<?> response = service.getMarkByStudentId(studentId);
        assertEquals(markList,response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("JUnit Test for Subject Wise Mark by Student Id")
    @Test
    public void getSubjectAndMarkByStudentIdTest() {
        int studentId = 1;
        List<SubjectMarkResponse> expectedList = List.of(new SubjectMarkResponse("English",56),
                new SubjectMarkResponse("Math",70));
        when(repository.subjectMarkByStudentId(studentId)).thenReturn(expectedList);
        ResponseEntity<?> response = service.getSubjectAndMarkByStudentId(studentId);
        assertEquals(expectedList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
