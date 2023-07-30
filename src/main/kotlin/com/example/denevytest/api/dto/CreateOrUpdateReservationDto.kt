package com.example.denevytest.api.dto

import java.time.LocalDateTime

data class CreateOrUpdateReservationDto(
    val roomId: Long,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val name: String,
    val surname: String
)
