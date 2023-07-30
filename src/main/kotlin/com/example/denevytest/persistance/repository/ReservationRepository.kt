package com.example.denevytest.persistance.repository

import com.example.denevytest.persistance.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface ReservationRepository : JpaRepository<Reservation, Long> {
    @Query(
        "select r from Reservation r where " +
                "(:id is null or r.id = :id) and " +
                "(:roomId is null or r.room.id = :roomId) and" +
                "(r.endTime < :currentDate and " +
                "(cast(:date as timestamp ) is null or cast(:date as timestamp) between r.startTime and r.endTime)) and" +
                "(:name is null or lower(r.name) = lower(:name)) and" +
                "(:surname is null or lower(r.surname) = lower(:surname))"
    )
    fun findAll(
        id: Long?,
        roomId: Long?,
        date: LocalDateTime?,
        name: String?,
        surname: String?,
        currentDate: LocalDateTime = LocalDateTime.now()
    ): List<Reservation>

    @Query(
        "select r from Reservation r where" +
                " r.room.id = ?1 " +
                "and (?2 between r.startTime and r.endTime or ?3 between r.startTime and r.endTime)"
    )
    fun find(roomId: Long, startTime: LocalDateTime, endTime: LocalDateTime): List<Reservation>

}