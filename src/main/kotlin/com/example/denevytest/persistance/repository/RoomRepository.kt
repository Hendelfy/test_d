package com.example.denevytest.persistance.repository

import com.example.denevytest.persistance.entity.Room
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.*

interface RoomRepository : JpaRepository<Room, Long> {

    @Query("select r from Room r where r.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByIdWithLock(id: Long): Optional<Room>

}