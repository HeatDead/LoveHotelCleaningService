package com.example.lovehotelcleaningservice.repos;

import com.example.lovehotelcleaningservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername();
}
