package com.mcb.service;

import com.mcb.constant.MCBConstant;
import com.mcb.entity.Groups;
import com.mcb.entity.SubjectTeacher;
import com.mcb.entity.Subjects;
import com.mcb.repository.StudentRepository;
import com.mcb.repository.SubjectTeacherRepository;
import com.mcb.utils.MCBUtils;
import com.mcb.wrapper.TeacherRequest;
import com.mcb.wrapper.TeacherResponse;
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
public class SubjectTeacherService {

    @Autowired
    private final SubjectTeacherRepository repository;
    @Autowired
    private final StudentRepository studentRepository;

    public ResponseEntity<String> addTeacher(TeacherRequest request) {
        try {
            if (request != null) {
                repository.save(saveTeacher(request));
                return MCBUtils.getResponse("Teacher ID successfully generated.", HttpStatus.CREATED);
            } else {
                return MCBUtils.getResponse(MCBConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private SubjectTeacher saveTeacher(TeacherRequest request) {
        Groups groups = new Groups();
        groups.setId(request.getGroupId());
        Subjects subjects = new Subjects();
        subjects.setSubjectId(request.getSubjectId());
        return SubjectTeacher.builder()
                .subjects(subjects)
                .groups(groups)
                .build();
    }


    public ResponseEntity<String> deleteTeacher(int id) {
        try {
            Optional<SubjectTeacher> teacher = repository.findById(id);
            if (teacher.isPresent()) {
                repository.deleteById(id);
                return MCBUtils.getResponse("Teacher has been removed.", HttpStatus.NO_CONTENT);
            } else {
                return MCBUtils.getResponse("Teacher id does not exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<TeacherResponse>> getAllTeachers() {
        try {
            List<TeacherResponse> responses = repository.getAllTeachers();
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<TeacherResponse>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getTeacherById(int id) {
        try {
            Optional<TeacherResponse> responses = repository.getTeacherById(id);
            if (responses.isPresent()) {
                return new ResponseEntity<>(responses.get(), HttpStatus.FOUND);
            } else {
                return MCBUtils.getResponse("Teacher not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Long> getStudentCount(int teacherId) {
        try {
            Optional<SubjectTeacher> teacher = repository.findById(teacherId);
            if (teacher.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Long count = studentRepository.countByTeacherId(teacherId);
            System.out.println(count);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
