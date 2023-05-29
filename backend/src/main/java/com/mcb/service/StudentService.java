package com.mcb.service;

import com.mcb.constant.MCBConstant;
import com.mcb.entity.Groups;
import com.mcb.entity.Students;
import com.mcb.repository.StudentRepository;
import com.mcb.utils.MCBUtils;
import com.mcb.wrapper.StudentRequest;
import com.mcb.wrapper.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private final StudentRepository repository;

    public ResponseEntity<String> addStudent(StudentRequest request) {
        try {
            if (request != null) {
                repository.save(saveStudent(request));
                return MCBUtils.getResponse("Student added successfully.", HttpStatus.CREATED);
            } else {
                return MCBUtils.getResponse(MCBConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Students saveStudent(StudentRequest request) {
        Groups groups = new Groups();
        groups.setId(request.getGroupId());
        return Students.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .groups(groups)
                .build();
    }


    public ResponseEntity<String> updateStudent(int id, StudentRequest request) {
        try {
            Students students = repository.findById(id).get();
            students.setFirstName(request.getFirstName());
            students.setLastName(request.getLastName());
            repository.save(students);
            return MCBUtils.getResponse("Student id " + id + "'s has been updated successfully", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteStudent(int id) {
        try {
            Optional<Students> result = repository.findById(id);
            if (result.isPresent()) {
                repository.deleteById(id);
                return MCBUtils.getResponse("Student id " + id + " has been removed from group.", HttpStatus.NO_CONTENT);
            } else {
                return MCBUtils.getResponse("Student id " + id + " does not exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<StudentResponse>> getAllStudent() {
        try {
            List<StudentResponse> response = repository.getAllStudent();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<StudentResponse>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getStudentById(int id) {
        try {
            Optional<StudentResponse> response = repository.getStudentById(id);
            if (response.isPresent()) {
                return new ResponseEntity<>(response.get(), HttpStatus.FOUND);
            } else {
                return MCBUtils.getResponse("Student Id not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
