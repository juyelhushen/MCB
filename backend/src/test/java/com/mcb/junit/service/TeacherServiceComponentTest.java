package com.mcb.junit.service;

import com.mcb.entity.Groups;
import com.mcb.entity.SubjectTeacher;
import com.mcb.entity.Subjects;
import com.mcb.repository.StudentRepository;
import com.mcb.repository.SubjectTeacherRepository;
import com.mcb.service.SubjectTeacherService;
import com.mcb.wrapper.TeacherRequest;
import com.mcb.wrapper.TeacherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TeacherServiceComponentTest {

    @Autowired
    private SubjectTeacherService teacherService;

    @MockBean
    private SubjectTeacherRepository teacherRepository;

    @MockBean
    private StudentRepository studentRepository;

    private SubjectTeacher teacher;

    @BeforeEach
    public void setUp() {
//        Subjects subjects = new Subjects();
//        subjects.setSubjectId(1);
//        Groups groups = new Groups();
//        groups.setId(1);
        teacher = SubjectTeacher.builder()
                .teacherId(1)
                .build();
    }

    @DisplayName("JUnit Test for save The Teacher")
    @Test
    public void addTeacher() {
        TeacherRequest request = new TeacherRequest();
        request.setGroupId(1);
        request.setSubjectId(1);
        ResponseEntity<?> response = teacherService.addTeacher(request);
        verify(teacherRepository,times(1)).save(any(SubjectTeacher.class));
        assertEquals("{\"message\":\""+"Teacher ID successfully generated."+"\"}",response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @DisplayName("JUnit test for delete Teacher")
    @Test
    public void deleteTeacherTest() {
        int teacherId = 1;
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        ResponseEntity<?> response = teacherService.deleteTeacher(teacherId);
        verify(teacherRepository,times(1)).deleteById(teacherId);
        assertEquals("{\"message\":\""+"Teacher has been removed."+"\"}",response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @DisplayName("Junit Test for Get All Teacher")
    @Test
    public void getAllTeacherTest() {
        when(teacherRepository.getAllTeachers()).thenReturn(Stream.of(new TeacherResponse(1,1,1),
                new TeacherResponse(2,5,1)).collect(Collectors.toList()));
        ResponseEntity<List<TeacherResponse>> response = teacherService.getAllTeachers();
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @DisplayName("JUnit test to get Teacher by Id")
    @Test
    public void getTeacherByIdTest() {
        int teacherId = 1;
        TeacherResponse teacher = new TeacherResponse(1,4,6);
        when(teacherRepository.getTeacherById(teacherId)).thenReturn(Optional.of(teacher));
        ResponseEntity<?> response = teacherService.getTeacherById(teacherId);
        assertEquals(teacher, response.getBody());
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @DisplayName("JUnit Test for student count by teacher Id")
    @Test
    public void testGetStudentCount_existingTeacher() {
        int teacherId = 1;
        Long noOfStudent = 10L;
        SubjectTeacher teacher = new SubjectTeacher(); // Create a mock SubjectTeacher object
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(studentRepository.countByTeacherId(teacherId)).thenReturn(noOfStudent);

        ResponseEntity<Long> response = teacherService.getStudentCount(teacherId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(noOfStudent, Objects.requireNonNull(response.getBody()).longValue());
        verify(teacherRepository, times(1)).findById(teacherId);
        verify(studentRepository, times(1)).countByTeacherId(teacherId);
    }

}
