package com.security.Jwt.Implementation.Project.repository;



import com.security.Jwt.Implementation.Project.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}

