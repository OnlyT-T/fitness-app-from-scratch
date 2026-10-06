package com.progressfit.backend.repository;

import com.progressfit.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // save()
    // findAll()
    // findById()
    // deleteById()
    // existsById()
    // count()
}