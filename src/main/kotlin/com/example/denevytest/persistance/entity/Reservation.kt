package com.example.denevytest.persistance.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var room: Room,

    @Column(nullable = false)
    var startTime: LocalDateTime,

    @Column(nullable = false)
    var endTime: LocalDateTime,

    var name: String,
    var surname: String
)