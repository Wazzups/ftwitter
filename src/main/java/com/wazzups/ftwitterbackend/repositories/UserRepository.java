package com.wazzups.ftwitterbackend.repositories;

import com.wazzups.ftwitterbackend.models.ApplicationUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {

    Optional<ApplicationUser> findByUsername(String username);
}
