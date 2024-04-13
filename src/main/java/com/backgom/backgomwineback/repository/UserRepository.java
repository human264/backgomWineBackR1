package com.backgom.backgomwineback.repository;

import com.backgom.backgomwineback.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
