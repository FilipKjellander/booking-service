package com.example.bookingservice.dto;

import com.example.bookingservice.utility.RoomSize;
import com.example.bookingservice.utility.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDetailedDto {

    private Long id;
    private RoomType type;
    private RoomSize size;
    private List<RoomReservationDto> roomReservations;

}
