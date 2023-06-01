package com.jskool.limitloginattempts.users.dao;

import com.jskool.limitloginattempts.users.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
