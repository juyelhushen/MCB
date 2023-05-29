package com.mcb.repository;

import com.mcb.entity.Marks;
import com.mcb.wrapper.MarkResponse;
import com.mcb.wrapper.StudentMarkResponse;
import com.mcb.wrapper.SubjectMarkResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Marks,Integer> {

    @Query("SELECT new com.mcb.wrapper.MarkResponse(m.id,m.date,m.mark,m.students.studentId,m.subjects.subjectId) FROM Marks m")
    List<MarkResponse> getAllMarks();

    @Query("SELECT new com.mcb.wrapper.MarkResponse(m.id,m.date,m.mark,m.students.studentId,m.subjects.subjectId)" +
            " FROM Marks m WHERE m.id = :id")
    Optional<MarkResponse> findMarkById(int id);

    @Query("Select new com.mcb.wrapper.StudentMarkResponse(m.mark) FROM Marks m WHERE m.students.studentId = :studentId")
    List<StudentMarkResponse> getMarkByStudentId(int studentId);

    @Query("SELECT new com.mcb.wrapper.SubjectMarkResponse(m.subjects.title,m.mark) FROM Marks m WHERE m.students.studentId = :id")
    List<SubjectMarkResponse> subjectMarkByStudentId(int id);
}
