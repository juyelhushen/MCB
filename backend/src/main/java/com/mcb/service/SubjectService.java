package com.mcb.service;

import com.mcb.constant.MCBConstant;
import com.mcb.entity.Subjects;
import com.mcb.repository.SubjectRepository;
import com.mcb.utils.MCBUtils;
import com.mcb.wrapper.SubjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.error.Mark;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService {


    @Autowired
    private final SubjectRepository repository;

    public ResponseEntity<String> addSubject(SubjectRequest request) {
        try {
            if (request != null) {
                Subjects subjects = Subjects.builder()
                        .title(request.getTitle())
                        .build();
                repository.save(subjects);
                return MCBUtils.getResponse("Subject Successfully created.", HttpStatus.CREATED);
            } else {
                return MCBUtils.getResponse(MCBConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<String> updateSubject(int id, SubjectRequest request) {
        try {
            if (request != null) {
                Subjects subject = repository.findById(id).get();
                subject.setTitle(request.getTitle());
                repository.save(subject);
                return MCBUtils.getResponse("Subject has been updated.", HttpStatus.ACCEPTED);
            } else {
                return MCBUtils.getResponse(MCBConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<String> deleteSubject(int id) {
        try {
            Optional<Subjects> subjects = repository.findById(id);
            if (subjects.isPresent()) {
                repository.deleteById(id);
                return MCBUtils.getResponse("Subjects has removed from list.", HttpStatus.NO_CONTENT);
            } else {
                return MCBUtils.getResponse("Subjects id not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Subjects>> getAllSubjects() {
        try {
            List<Subjects> result = repository.findAll();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Subjects>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getSubjectById(int id) {
        try {
            Optional<Subjects> subject = repository.findById(id);
            if (subject.isPresent()) {
                Subjects subjectById = subject.get();
                return ResponseEntity.ok(subjectById);
            } else {
                return MCBUtils.getResponse("Subject not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
