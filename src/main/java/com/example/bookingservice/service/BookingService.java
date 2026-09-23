package com.example.bookingservice.service;



import com.example.bookingservice.dto.BookingDto;
import com.example.bookingservice.dto.RoomDetailedDto;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
        List<BookingDto> getAllBookings();
        BookingDto getBookingById(Long id);
        BookingDto createBooking(BookingDto bookingDto);
        BookingDto updateBooking(Long id, BookingDto bookingDto);
        void deleteBooking(Long id);
        List<RoomDetailedDto> searchAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate,
                                                   int numberOfGuests) ;
        boolean hasBookedRoom(Long customerId, Long roomId);
}
