package com.shortcutz.medistream3000.data

import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
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
        }
        patientRepo.flush()
        staticLogger.info("---> patientRepo initialized")

        val now = LocalDate.now()
        val monday = now.with(ChronoField.DAY_OF_WEEK, 1).atTime(0, 0, 0, 0)
        val sunday = now.with(ChronoField.DAY_OF_WEEK, 7).atTime(23, 59, 59)

        staticLogger.info("---> monday: $monday")
        staticLogger.info("---> sunday: $sunday")

//
//        var appointmentDay = monday
//        var appointmentTime = monday

        var amountOfAppointmentsDays = 5
        var amountOfAppointmentsPerDay = 10

        for (day in 1..amountOfAppointmentsDays) {
//            appointmentDay = now.with(ChronoField.DAY_OF_WEEK, day.toLong()).atTime(0, 0, 0, 0)

            for (time in 0 ..<amountOfAppointmentsPerDay) {
//                appointmentTime = now.with(ChronoField.DAY_OF_WEEK, day.toLong()).atTime(8+time, 0, 0, 0)

                val appointmentTimestamp = monday.atZone(ZoneId.systemDefault()).toInstant()
                //val appointmentTimestamp2 = monday.toInstant(ZoneId.systemDefault().rules.getOffset(Instant.now()))
                staticLogger.info("---> appointmentTimestamp = $appointmentTimestamp")
                //staticLogger.info("---> appointmentTimestamp2 = $appointmentTimestamp2")

                staticLogger.info("---> saving appointment day=$day time=$time")


                staticLogger.info("---> Instant.now + 2 Days = " + Instant.now().plus(2, ChronoUnit.DAYS).toString())
                appointmentRepo.save(
                    Appointment(0,UUID.randomUUID(),
                        medistreamId = ((day+time)%2).toLong(),
                        startTimestamp = Instant.now().truncatedTo(ChronoUnit.HOURS).
                        plus((day-1).toLong(),ChronoUnit.DAYS).
                        plus(time.toLong(),ChronoUnit.HOURS),
                        booked = time%2))
            }
        }
        appointmentRepo.flush()
        staticLogger.info("---> appointmentRepo initialized")

        staticLogger.info("---> Rooms, Patients and Appointments initialized")

    }

}