package com.example.lovehotelcleaningservice.repos;

import com.example.lovehotelcleaningservice.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room, Long> {
    Room findByNumber(int number);
}
