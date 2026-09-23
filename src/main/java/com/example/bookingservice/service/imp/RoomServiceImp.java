package com.example.bookingservice.service.imp;

import com.example.bookingservice.client.CustomerClient;
import com.example.bookingservice.dto.RoomDetailedDto;
import com.example.bookingservice.dto.RoomReservationDto;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.model.Room;
import com.example.bookingservice.repository.RoomRepository;
import com.example.bookingservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImp implements RoomService {

    private final RoomRepository roomRepo;
    private final CustomerClient customerClient;

    @Override
    public List<RoomReservationDto> bookingsToReservations(List<Booking> bookings) {

        List<RoomReservationDto> listOfReservations = new ArrayList<>();

        for (Booking booking : bookings) {
            String customerName;
            try {
                customerName = customerClient.findCustomerById(booking.getCustomerId()).getName();
            } catch (ResourceAccessException e) {
                customerName = "Data unavailable";
            }

            listOfReservations.add(
                    RoomReservationDto.builder()
                            .id(booking.getId())
                            .customerName(customerName)
                            .checkInDate(booking.getCheckInDate())
                            .checkOutDate(booking.getCheckOutDate())
                            .numberOfGuests(booking.getNumberOfGuests())
                            .extraBeds(booking.getExtraBeds())
                            .build()
            );
        }

        return listOfReservations;
    }


    @Override
    public RoomDetailedDto roomToRoomDto(Room room) {

        List<RoomReservationDto> roomBookings;

        if(room.getBooking() != null) {
            roomBookings = bookingsToReservations(room.getBooking());
        }else roomBookings = new ArrayList<>();

        return RoomDetailedDto.builder()
                .id(room.getId())
                .type(room.getType())
                .size(room.getSize())
                .roomReservations(roomBookings)
                .build();
    }


    @Override
    @Transactional(readOnly = true)
    public List<RoomDetailedDto> getAllRoom(){
        return roomRepo.findAll().stream().map(room -> roomToRoomDto(room)).toList();
    }

    @Override
    public boolean existsById(Long id) {
        return roomRepo.existsById(id);
    }

}
