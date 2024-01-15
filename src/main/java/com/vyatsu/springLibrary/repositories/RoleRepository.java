package com.vyatsu.springLibrary.repositories;

import com.vyatsu.springLibrary.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
