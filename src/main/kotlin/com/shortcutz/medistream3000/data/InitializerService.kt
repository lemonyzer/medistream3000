package com.shortcutz.medistream3000.data

import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.util.*

@Service
class InitializerService(
    val roomRepo : RoomEntityRepository,
    val patientRepo : PatientEntityRepository,
    val appointmentRepo : AppointmentRepository
) {

    @PostConstruct
    fun init() {

        val staticLogger = KotlinLogging.logger {}
        staticLogger.info("xxx initializing Repos")

        var amountOfRooms = 20
        for (i in 1 .. amountOfRooms) {
            roomRepo.save(RoomEntity(UUID.randomUUID(), "Room-$i", i, 2))
        }
        roomRepo.flush()
        staticLogger.info("---> roomRepo initialized")

        var rooms = roomRepo.findAll()


        var amountOfPatients = 10
        for (i in 1 .. amountOfPatients) {
            patientRepo.save(PatientEntity(UUID.randomUUID(),i.toLong(),"$i","Nach${i + i}", rooms.get(i)))
//            patientRepo.save(PatientEntity(UUID.randomUUID(),i.toLong(),"$i","Nach${i + i}", RoomEntity(UUID.randomUUID(),"room-$i",i,2)))
        }
        patientRepo.flush()
        staticLogger.info("---> patientRepo initialized")


        var amountOfAppointments = 10
        for (i in 1 .. amountOfPatients) {
            staticLogger.info("---> saving appointment $i")
            appointmentRepo.save(Appointment(0,UUID.randomUUID(), medistreamId = (i%2).toLong()))//PatientEntity(UUID.randomUUID(),i.toLong(),"$i","Nach${i + i}", rooms.get(i)))
//            patientRepo.save(PatientEntity(UUID.randomUUID(),i.toLong(),"$i","Nach${i + i}", RoomEntity(UUID.randomUUID(),"room-$i",i,2)))
        }
        appointmentRepo.flush()
        staticLogger.info("---> appointmentRepo initialized")

        staticLogger.info("---> Rooms, Patients and Appointments initialized")

    }

}