package com.mcb.repository;

import com.mcb.entity.Students;
import com.mcb.wrapper.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Students, Integer> {

    @Query("SELECT new com.mcb.wrapper.StudentResponse(s.studentId,s.firstName,s.lastName,s.groups.id)" +
            " FROM Students s")
    List<StudentResponse> getAllStudent();

    @Query("SELECT new com.mcb.wrapper.StudentResponse(s.studentId,s.firstName,s.lastName,s.groups.id) " +
            "FROM Students s WHERE s.id =:id")
    Optional<StudentResponse> getStudentById(int id);

    @Query("SELECT count(s.studentId) FROM Groups g JOIN g.subjectTeachers st JOIN g.students s WHERE st.teacherId = :teacherId")
    Long countByTeacherId(@Param("teacherId") int teacherId);
}
