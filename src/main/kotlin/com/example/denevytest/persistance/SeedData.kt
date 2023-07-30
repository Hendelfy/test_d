package com.example.denevytest.persistance

import com.example.denevytest.persistance.entity.Room
import com.example.denevytest.persistance.repository.RoomRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class SeedData(val roomRepository: RoomRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        roomRepository.saveAll(
            listOf(
                Room(1, LocalTime.of(8, 0), LocalTime.of(17, 0)),
                Room(2, LocalTime.of(8, 0), LocalTime.of(17, 0)),
                Room(3, LocalTime.of(8, 0), LocalTime.of(17, 0)),
                Room(4, LocalTime.of(8, 0), LocalTime.of(17, 0))
            )
        )
    }

}