package com.example.userservice.db.repo;

import com.example.userservice.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
