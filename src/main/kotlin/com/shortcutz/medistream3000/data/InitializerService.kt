package com.shortcutz.medistream3000.data

import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

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


//        var amountOfAppointments = 10
//        for (i in 1 .. amountOfPatients) {
//            staticLogger.info("---> saving appointment $i")
//            appointmentRepo.save(Appointment(0,UUID.randomUUID(), medistreamId = (i%2).toLong()))//PatientEntity(UUID.randomUUID(),i.toLong(),"$i","Nach${i + i}", rooms.get(i)))
////            patientRepo.save(PatientEntity(UUID.randomUUID(),i.toLong(),"$i","Nach${i + i}", RoomEntity(UUID.randomUUID(),"room-$i",i,2)))
//        }

//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val current = LocalDateTime.now().format(formatter)

        val now = LocalDate.now()
        val monday = now.with(ChronoField.DAY_OF_WEEK, 1).atTime(0, 0, 0, 0)
        val sunday = now.with(ChronoField.DAY_OF_WEEK, 7).atTime(23, 59, 59)

        // calender
//        val time = Calendar.getInstance().time
//        val formatterCalendar = SimpleDateFormat("yyyy-MM-dd HH:mm")
//        val currentCalendar = formatter.format(time)

        var appointmentDay = monday
        var appointmentTime = monday
        var amountOfAppointmentsDays = 5
        var amountOfAppointmentsPerDay = 10
        for (day in 1..amountOfAppointmentsDays) {
            appointmentDay = now.with(ChronoField.DAY_OF_WEEK, day.toLong()).atTime(0, 0, 0, 0)
            for (time in 0 ..<amountOfAppointmentsPerDay) {
                appointmentTime = now.with(ChronoField.DAY_OF_WEEK, day.toLong()).atTime(8+time, 0, 0, 0)
                staticLogger.info("---> saving appointment day=$day time=$time")
                // 7:00
                // 7:30
                // 8:00
                // 8:30

                //appointmentTime

                val startDuration = 1.days + 12.hours + 15.minutes
                val endDuration = 8.hours + 45.minutes
                val difference: Duration = startDuration.minus(endDuration)

                appointmentRepo.save(
                    Appointment(0,UUID.randomUUID(),
                        medistreamId = ((day+time)%2).toLong(),
                        startTimestamp = Instant.now().plusMillis((day-1)*1000*60*60*24 + time.toLong()*1000*60*60)))
            }
        }
        appointmentRepo.flush()
        staticLogger.info("---> appointmentRepo initialized")

        staticLogger.info("---> Rooms, Patients and Appointments initialized")

    }

}