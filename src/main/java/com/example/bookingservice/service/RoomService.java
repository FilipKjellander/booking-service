package com.example.bookingservice.service;



import com.example.bookingservice.dto.RoomDetailedDto;
import com.example.bookingservice.dto.RoomReservationDto;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.model.Room;

import java.util.List;

public interface RoomService {

    List<RoomReservationDto> bookingsToReservations(List<Booking> bookings);

    List<RoomDetailedDto> getAllRoom();

    RoomDetailedDto roomToRoomDto(Room room);

    boolean existsById(Long id);

}