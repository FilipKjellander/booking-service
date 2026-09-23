package com.example.bookingservice.controller;


import com.example.bookingservice.dto.BookingDto;
import com.example.bookingservice.dto.CustomerFullDto;
import com.example.bookingservice.service.imp.BookingServiceImp;
import com.example.bookingservice.service.imp.CustomerServiceImp;
import com.example.bookingservice.service.imp.RoomServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("booking")

public class BookingController {

    private final BookingServiceImp bookingService;
    private final CustomerServiceImp customerService;
    private final RoomServiceImp roomService;

    @RequestMapping
    public String allBookings(Model model) {

        List<CustomerFullDto> customerList = checkServiceAvailability(
                "Customer service is not available, new bookings can not be created.", model);

        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("bookingDto", new BookingDto());
        model.addAttribute("customers", customerList);
        model.addAttribute("rooms", roomService.getAllRoom());

        return "booking";
    }

    @PostMapping("add")
    public String addBooking(@Valid @ModelAttribute BookingDto bookingDto,
                             RedirectAttributes redirectAttributes) {

        try {
            bookingService.createBooking(bookingDto);
            redirectAttributes.addFlashAttribute("message", "Booking created!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/booking";
    }

    @GetMapping("edit/{id}")
    public String editBooking(@PathVariable Long id, Model model) {
        BookingDto bookingDto = bookingService.getBookingById(id);
        List<CustomerFullDto> customerList = checkServiceAvailability(
                "Customer service is not available, editing bookings will not be possible.", model);

        model.addAttribute("bookingDto", bookingDto);
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("customers", customerList);
        model.addAttribute("rooms", roomService.getAllRoom());

        return "booking";
    }

    @PostMapping("update/{id}")
    public String updateBooking(@PathVariable Long id,
                                @Valid @ModelAttribute BookingDto bookingDto,
                                RedirectAttributes redirectAttributes) {

        try {
            bookingService.updateBooking(id, bookingDto);
            redirectAttributes.addFlashAttribute("message", "Booking updated!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/booking";
    }

    @PostMapping("delete/{id}")
    public String deleteBooking(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {

        try {
            bookingService.deleteBooking(id);
            redirectAttributes.addFlashAttribute("message", "Booking deleted!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/booking";
    }

    @RequestMapping("available")
    public String searchAvailableRooms(@RequestParam LocalDate checkInDate,
                                       @RequestParam LocalDate checkOutDate,
                                       @RequestParam int numberOfGuests,
                                       Model model, RedirectAttributes redirectAttributes) {
        List<CustomerFullDto> customerList = checkServiceAvailability(
                "Customer service is not available, new bookings can not be created.", model);
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("bookingDto", new BookingDto());
        model.addAttribute("customers", customerList);
        model.addAttribute("rooms", roomService.getAllRoom());

        try {
            model.addAttribute("availableRooms", bookingService.searchAvailableRooms(
                    checkInDate,
                    checkOutDate,
                    numberOfGuests
            ));
        } catch (IllegalArgumentException e) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "booking";
    }

    private List<CustomerFullDto> checkServiceAvailability(String message, Model model) {
        List<CustomerFullDto> customerList;
        try {
            customerList = customerService.getAllCustomers();
        } catch (ResourceAccessException e) {
            customerList = new ArrayList<>();
            model.addAttribute("errorMessage", message);
        }
        return customerList;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public String handleValidationException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(
                "errorMessage",
                "Verify that customer, room, dates " +
                        "and amount of visitors is properly filled out thank you."
        );

        return "redirect:/booking";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleArgumentException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage",
                "Check in date can't be in the past.");
        return "redirect:/booking";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingServletRequestParameterException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage",
                "You must fill in check-in and check-out date");
        return "redirect:/booking";
    }


}