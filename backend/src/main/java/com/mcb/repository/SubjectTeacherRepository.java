package com.mcb.repository;

import com.mcb.entity.SubjectTeacher;
import com.mcb.wrapper.TeacherResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectTeacherRepository extends JpaRepository<SubjectTeacher,Integer> {
    @Query("SELECT new com.mcb.wrapper.TeacherResponse(st.id, st.groups.id, st.subjects.subjectId)" +
            " FROM SubjectTeacher st")
    List<TeacherResponse> getAllTeachers();

    @Query("SELECT new com.mcb.wrapper.TeacherResponse(st.id, st.groups.id, st.subjects.subjectId)" +
            " FROM SubjectTeacher st WHERE st.teacherId =:id")
    Optional<TeacherResponse> getTeacherById(int id);
}
