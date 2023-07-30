package com.example.denevytest.persistance.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalTime

@Entity
class Room(
    @Id
    var id: Long? = null,

    var availableFrom: LocalTime,

    var availableTo: LocalTime
)