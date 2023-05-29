package com.mcb.repository;

import com.mcb.entity.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subjects,Integer> {
}
