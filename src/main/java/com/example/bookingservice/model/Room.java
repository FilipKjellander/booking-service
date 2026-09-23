package com.example.bookingservice.model;

import com.example.bookingservice.utility.RoomSize;
import com.example.bookingservice.utility.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RoomType type;
    private RoomSize size;

    @OneToMany(mappedBy = "room")
    private List<Booking> booking;

}


