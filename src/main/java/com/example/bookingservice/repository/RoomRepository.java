package com.example.bookingservice.repository;

import com.example.bookingservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room getRoomById(Long id);
    boolean existsById(Long id);
}
