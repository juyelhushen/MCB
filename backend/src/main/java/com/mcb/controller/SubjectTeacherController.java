package com.mcb.controller;

import com.mcb.constant.MCBConstant;
import com.mcb.entity.SubjectTeacher;
import com.mcb.service.SubjectTeacherService;
import com.mcb.utils.MCBUtils;
import com.mcb.wrapper.TeacherRequest;
import com.mcb.wrapper.TeacherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class SubjectTeacherController {

    @Autowired
    private final SubjectTeacherService service;

    @PostMapping("/add")
    public ResponseEntity<String> addTeacher(@RequestBody TeacherRequest request) {
        try {
            return service.addTeacher(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable int id) {
        try {
            return service.deleteTeacher(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TeacherResponse>> getAllTeachers() {
        try {
            return service.getAllTeachers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable int id) {
        try {
            return service.getTeacherById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/student/count/{teacherId}")
    public ResponseEntity<Long> getStudentCount(@PathVariable int teacherId) {
        try {
            return service.getStudentCount(teacherId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
