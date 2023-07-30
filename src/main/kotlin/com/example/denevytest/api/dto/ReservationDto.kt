package com.example.denevytest.api.dto

import java.time.LocalDateTime

data class ReservationDto(
    val id: Long,
    val roomId: Long,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val name: String,
    val surname: String
)
