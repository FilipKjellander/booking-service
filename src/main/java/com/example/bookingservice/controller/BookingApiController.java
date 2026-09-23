package com.example.bookingservice.controller;

import com.example.bookingservice.dto.BookingCheckResponseDto;
import com.example.bookingservice.service.imp.BookingServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingApiController {

    private final BookingServiceImp bookingService;

    @GetMapping("/check")
    public BookingCheckResponseDto checkBooking(
            @RequestParam Long customerId,
            @RequestParam Long roomId) {

        boolean booked = bookingService.hasBookedRoom(customerId, roomId);

        return new BookingCheckResponseDto(booked);
    }
}