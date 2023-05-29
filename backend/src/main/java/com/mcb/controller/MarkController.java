package com.mcb.controller;

import com.mcb.constant.MCBConstant;
import com.mcb.service.MarkService;
import com.mcb.utils.MCBUtils;
import com.mcb.wrapper.MarkRequest;
import com.mcb.wrapper.MarkResponse;
import com.mcb.wrapper.StudentMarkResponse;
import com.mcb.wrapper.SubjectMarkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/marks")
@RequiredArgsConstructor
public class MarkController {

    @Autowired
    private final MarkService service;

    @PostMapping("/add")
    public ResponseEntity<String> addMark(@RequestBody MarkRequest request) {
        try {
            return service.addMark(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMark(@PathVariable int id,@RequestBody MarkRequest request) {
        try {
            return service.updateMark(id,request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        try {
            return service.deleteMarks(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/all")
    public ResponseEntity<List<MarkResponse>> getAllMarks() {
        try {
            return service.getAllMarks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMarkById(@PathVariable int id) {
        try {
            return service.getMarkById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    //5
    @GetMapping("/mark/{studentId}")
    public ResponseEntity<?> getMarkByStudentId(@PathVariable int studentId) {
        try {
            return service.getMarkByStudentId(studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/subject/mark/{studentId}")
    public ResponseEntity<?> getSubjectMark(@PathVariable int studentId) {
        try {
            return service.getSubjectAndMarkByStudentId(studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
