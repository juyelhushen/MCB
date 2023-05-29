package com.mcb.controller;

import com.mcb.constant.MCBConstant;
import com.mcb.entity.Subjects;
import com.mcb.service.SubjectService;
import com.mcb.utils.MCBUtils;
import com.mcb.wrapper.SubjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {

    @Autowired
    private final SubjectService service;

    @PostMapping("/add")
    public ResponseEntity<String> addSubject(@RequestBody SubjectRequest request) {
        try {
            return service.addSubject(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSubject(@PathVariable int id, @RequestBody SubjectRequest request) {
        try {
            return service.updateSubject(id, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String>  deleteSubject(@PathVariable int id) {
        try {
            return service.deleteSubject(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subjects>> getAll() {
        try {
            return service.getAllSubjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Subjects>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable int id) {
        try {
            return service.getSubjectById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
