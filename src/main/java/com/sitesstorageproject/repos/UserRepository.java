package com.sitesstorageproject.repos;

import com.sitesstorageproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByLogin(String login);

    @Query(value = "select login from users", nativeQuery = true)
    List<String> getLogins();

    @Query(value = "select email from users", nativeQuery = true)
    List<String> getEmails();
}
