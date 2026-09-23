package com.example.bookingservice.controller;


import com.example.bookingservice.dto.RoomDetailedDto;
import com.example.bookingservice.service.imp.RoomServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final RoomServiceImp roomService;

    @GetMapping("/Rooms")
    public String Rooms(Model model){
        List<RoomDetailedDto> allRooms = roomService.getAllRoom();
        model.addAttribute("allRooms", allRooms);
        return "room";
    }
}