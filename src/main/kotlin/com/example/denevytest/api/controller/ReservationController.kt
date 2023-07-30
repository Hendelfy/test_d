package com.example.denevytest.api.controller

import com.example.denevytest.api.dto.CreateOrUpdateReservationDto
import com.example.denevytest.api.dto.ReservationDto
import com.example.denevytest.persistance.entity.Reservation
import com.example.denevytest.service.ReservationService
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("reservation")
class ReservationController(val reservationService: ReservationService) {
    @GetMapping
    fun getReservation(
        @RequestParam id: Long?,
        @RequestParam roomId: Long?,
        @RequestParam date: LocalDateTime?,
        @RequestParam name: String?,
        @RequestParam surname: String?
    ): List<ReservationDto> {
        val reservations = reservationService.getReservations(id, roomId, date, name, surname)
        return reservations.map {
            getReservationResponseDto(it)
        }
    }

    @DeleteMapping("{id}")
    fun deleteReservation(@PathVariable id: Long) {
        reservationService.delete(id)
    }

    @PutMapping("{id}")
    fun changeReservation(@PathVariable id: Long, dto: CreateOrUpdateReservationDto): ReservationDto {
        val reservation =
            reservationService.changeReservation(id, dto.roomId, dto.startTime, dto.endTime, dto.name, dto.surname)
        return getReservationResponseDto(reservation)
    }

    @PostMapping
    fun createReservation(@RequestBody dto: CreateOrUpdateReservationDto): ReservationDto {
        val reservation =
            reservationService.createReservation(dto.roomId, dto.startTime, dto.endTime, dto.name, dto.surname)
        return getReservationResponseDto(reservation)
    }

    private fun getReservationResponseDto(reservation: Reservation) =
        ReservationDto(
            id = reservation.id!!,
            roomId = reservation.room.id!!,
            start = reservation.startTime,
            end = reservation.endTime,
            name = reservation.name,
            surname = reservation.surname
        )
}