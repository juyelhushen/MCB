package com.mcb.service;

import com.mcb.constant.MCBConstant;
import com.mcb.entity.Marks;
import com.mcb.entity.Students;
import com.mcb.entity.Subjects;
import com.mcb.repository.MarkRepository;
import com.mcb.repository.StudentRepository;
import com.mcb.utils.MCBUtils;
import com.mcb.wrapper.MarkRequest;
import com.mcb.wrapper.MarkResponse;
import com.mcb.wrapper.StudentMarkResponse;
import com.mcb.wrapper.SubjectMarkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarkService {

    @Autowired
    private final MarkRepository repository;

    @Autowired
    private final StudentRepository studentRepository;

    public ResponseEntity<String> addMark(MarkRequest request) {
        try {
            if (request != null) {
                repository.save(saveMark(request));
                return MCBUtils.getResponse("Marks successfully added.", HttpStatus.CREATED);
            } else {
                return MCBUtils.getResponse(MCBConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Marks saveMark(MarkRequest request) {
        Students student = new Students();
        student.setStudentId(request.getStudentId());
        Subjects subjects = new Subjects();
        subjects.setSubjectId(request.getSubjectId());
        return Marks.builder()
                .mark(request.getMark())
                .date(request.getDate())
                .students(student)
                .subjects(subjects)
                .build();
    }


    public ResponseEntity<String> updateMark(int id, MarkRequest request) {
        try {
            Marks marks = repository.findById(id).get();
            marks.setMark(request.getMark());
            marks.setDate(LocalDateTime.now());
            repository.save(marks);
            return MCBUtils.getResponse("Successfully updated students marks", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<String> deleteMarks(int id) {
        try {
            Optional<Marks> results = repository.findById(id);
            if (results.isPresent()) {
                repository.deleteById(id);
                return MCBUtils.getResponse("Marks has been removed.", HttpStatus.NO_CONTENT);
            } else {
                return MCBUtils.getResponse("Marks id not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<List<MarkResponse>> getAllMarks() {
        try {
            List<MarkResponse> result = repository.getAllMarks();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getMarkById(int id) {
        try {
            Optional<MarkResponse> marks = repository.findMarkById(id);
            if (marks.isPresent()) {
                return ResponseEntity.ok(marks.get());
            } else {
                return MCBUtils.getResponse("Marks id not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<?> getMarkByStudentId(int studentId) {
        try {
            List<StudentMarkResponse> markResponse = repository.getMarkByStudentId(studentId);
            if (markResponse != null) {
                return ResponseEntity.ok(markResponse);
            } else {
                return MCBUtils.getResponse("Student id does not exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getSubjectAndMarkByStudentId(int id) {
        try {
            List<SubjectMarkResponse> responses = repository.subjectMarkByStudentId(id);
            if (responses != null) {
                return ResponseEntity.ok(responses);
            } else {
                return MCBUtils.getResponse("Student id does not exist", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}