package com.sitesstorageproject.repos;

import com.sitesstorageproject.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
