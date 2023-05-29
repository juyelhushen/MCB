package com.mcb.controller;

import com.mcb.constant.MCBConstant;
import com.mcb.service.StudentService;
import com.mcb.utils.MCBUtils;
import com.mcb.wrapper.StudentRequest;
import com.mcb.wrapper.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    @Autowired
    private final StudentService service;

    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody StudentRequest request) {
        try {
            return service.addStudent(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable int id,@RequestBody StudentRequest request) {
        try {
            return service.updateStudent(id,request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        try {
            return service.deleteStudent(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentResponse>> getAllStudent() {
        try {
            return service.getAllStudent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<StudentResponse>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id) {
        try {
            return service.getStudentById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
