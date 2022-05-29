package com.example.lovehotelcleaningservice.repos;

import com.example.lovehotelcleaningservice.domain.Cleanup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CleanupRepo extends JpaRepository<Cleanup, Long> {
    @Query ("SELECT c FROM Cleanup c WHERE c.user_id = :user_id")
    List<Cleanup> findAllByUserid(@Param("user_id") Long user_id);
}
