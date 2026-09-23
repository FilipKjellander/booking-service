package com.example.bookingservice.service.imp;

import com.example.bookingservice.client.CustomerClient;
import com.example.bookingservice.dto.BookingDto;
import com.example.bookingservice.dto.CustomerDto;
import com.example.bookingservice.dto.CustomerFullDto;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {

    private final CustomerClient customerClient;
    private final BookingRepository bookingRepository;


    @Override
    public CustomerFullDto getCustomerFullDto(Long id) {
        CustomerDto customer = customerClient.findCustomerById(id);
        List<BookingDto> bookingDtos = bookingRepository.findBookingsByCustomerId(id)
                .stream()
                .map(this::bookingToBookingDto).toList();

        return CustomerFullDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .bookings(bookingDtos)
                .build();
    }

    @Override
    public List<CustomerFullDto> getAllCustomers() {
        return customerClient.getAllCustomers()
                .stream()
                .map(customerDto -> getCustomerFullDto(customerDto.getId()))
                .toList();
    }

    @Override
    public CustomerDto addCustomer(String name) {
        return customerClient.addCustomer(name);
    }

    @Override
    public void updateCustomerName(Long id, String newName) {
        customerClient.updateCustomerName(id, newName);
    }

    @Override
    public CustomerDto findCustomerById(Long id) {
        return customerClient.findCustomerById(id);
    }

    @Override
    public void deleteCustomer (Long customerId){

        boolean hasBooking = bookingRepository.existsByCustomerId(customerId);

        if (hasBooking){
            throw new IllegalArgumentException();
        }

        customerClient.deleteCustomer(customerId);
    }

    private BookingDto bookingToBookingDto(Booking booking) {
        return BookingDto.builder().id(booking.getId()).customerId(booking.getCustomerId())
                .roomId(booking.getRoom().getId()).checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate()).numberOfGuests(booking.getNumberOfGuests())
                .extraBeds(booking.getExtraBeds()).build();
    }
}
