package com.sitesstorageproject.repos;

import com.sitesstorageproject.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
