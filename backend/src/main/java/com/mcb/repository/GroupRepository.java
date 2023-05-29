package com.mcb.repository;

import com.mcb.entity.Groups;
import com.mcb.wrapper.GroupResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Groups,Integer> {
    @Query("Select new com.mcb.wrapper.GroupResponse(g.id, g.name) FROM Groups g WHERE g.id = :id")
    Optional<GroupResponse> findGroupById(int id);

    @Query("Select new com.mcb.wrapper.GroupResponse(g.id, g.name) FROM Groups g")
    List<GroupResponse> getAllGroups();
}
