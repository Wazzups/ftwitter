package com.wazzups.ftwitterbackend.repositories;

import com.wazzups.ftwitterbackend.models.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findRoleByAuthority(String authority);
}
