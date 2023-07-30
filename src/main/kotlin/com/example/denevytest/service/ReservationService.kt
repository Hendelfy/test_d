package com.example.denevytest.service

import com.example.denevytest.persistance.entity.Reservation
import com.example.denevytest.persistance.entity.Room
import com.example.denevytest.persistance.repository.ReservationRepository
import com.example.denevytest.persistance.repository.RoomRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val roomRepository: RoomRepository
) {
    fun getReservations(id: Long?, roomId: Long?, date: LocalDateTime?, name: String?, surname: String?) =
        reservationRepository.findAll(id, roomId, date, name, surname)

    fun delete(id: Long) {
        reservationRepository.deleteById(id)
    }

    @Transactional
    fun createReservation(
        roomId: Long,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        name: String,
        surname: String
    ): Reservation {
        val room = validateAndLockRoom(roomId, startTime, endTime)
        if (reservationRepository.find(roomId, startTime, endTime).isNotEmpty()) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Room with id $roomId is busy")
        }
        val reservation = Reservation(null, room, startTime, endTime, name, surname)
        return reservationRepository.save(reservation)
    }

    @Transactional
    fun changeReservation(
        id: Long,
        roomId: Long,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        name: String,
        surname: String
    ): Reservation {
        val reservation = reservationRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation with id $id doesnt exist")
        }
        val room = validateAndLockRoom(roomId, startTime, endTime)
        val existingReservations = reservationRepository.find(roomId, startTime, endTime)
        if (existingReservations.any { it.id != reservation.id }) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Room with id $roomId is busy")
        }

        reservation.room = room
        reservation.startTime = startTime
        reservation.endTime = endTime
        reservation.name = name
        reservation.surname = surname

        return reservationRepository.save(reservation)
    }

    private fun validateAndLockRoom(
        roomId: Long,
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): Room {
        val room = roomRepository.findByIdWithLock(roomId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Room with id $roomId doesnt exist")
        }
        if (startTime.toLocalDate() != endTime.toLocalDate()) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Start is different day that end")
        }
        if (room.availableFrom > startTime.toLocalTime()) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Room with id $roomId available from ${room.availableFrom}")
        }
        if (room.availableTo < endTime.toLocalTime()) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Room with id $roomId available to ${room.availableTo}")
        }
        return room
    }


}