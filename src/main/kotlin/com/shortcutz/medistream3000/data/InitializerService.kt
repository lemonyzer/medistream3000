package com.shortcutz.medistream3000.data

import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue


@Service
class InitializerService(
    val roomRepo : RoomEntityRepository,
    val patientRepo : PatientEntityRepository,
    val appointmentRepo : AppointmentRepository,
    val calenderDayDataRepository: CalenderDayDataRepository
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



        var amountOfAppointmentsDays = 6
        var amountOfAppointmentsPerDay = 6

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

                var startTs = Instant.now().truncatedTo(ChronoUnit.DAYS).
                plus(7, ChronoUnit.HOURS).                      // appointments starting at 7
                plus((day-1).toLong(),ChronoUnit.DAYS).                      // today + 24h
                plus(time.toLong()*30L,ChronoUnit.MINUTES)      // 30min * n

                staticLogger.info("startTs=$startTs")


                val dayName = "${startTs.atZone((ZoneId.systemDefault())).dayOfWeek.name}"

                val dayNameAbbr = startTs.atZone((ZoneId.systemDefault())).dayOfWeek.name.subSequence(0,3).toString()
                val dayOfMonth = startTs.atZone((ZoneId.systemDefault())).dayOfMonth.absoluteValue.toString()

                val monthName = startTs.atZone((ZoneId.systemDefault())).month.name
                val dayLabel = "${dayNameAbbr} ${dayOfMonth}. ${monthName.subSequence(0,3)}"

                staticLogger.info("dayLabel=$dayLabel")
                staticLogger.info("dayName=$dayName")
                staticLogger.info("dayNameAbbr=$dayNameAbbr")
                staticLogger.info("dayOfMonth=$dayOfMonth")
                staticLogger.info("monthName=$monthName")

                // TODO: not necessary!
                calenderDayDataRepository.save(CalenderDayData(0, weekDay = dayName, dayOfMonth = dayOfMonth))


                // Note that:
                //
                //Instant.truncatedTo(ChronoUnit.SECONDS) reduces the precision to seconds, which is sufficient for most use cases.
                //atZone(ZoneId.systemDefault()) adjusts the LocalDateTime to the system default time zone.
                //DateTimeFormatter is used to format the LocalDateTime into a string with the desired pattern.
                //SimpleDateFormat is used to parse the resulting string back into a Date object.
                //Keep in mind that SimpleDateFormat is a legacy class and has known issues (e.g., thread-safety, cultural sensitivity). If possible, consider using the modern java.time API and its DateTimeFormatter for formatting and parsing dates and times.
//                val instant = Instant.now()
//                val localDateTime = instant.truncatedTo(ChronoUnit.SECONDS).atZone(ZoneId.systemDefault()).toLocalDateTime()
//
//                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//                val formattedString = localDateTime.format(formatter)
//
//                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//                val date = sdf.parse(formattedString)

                // https://search.brave.com/search?q=java.time+API+and+its+DateTimeFormatter+for+formatting+and+parsing+dates+and+times&source=web&summary=1&conversation=b186d281fb5b40d1322d4c
                //val formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss.SSSSSSZ")
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault())
                val formattedDateTime = formatter.format(startTs)

                //val dateTimeString = "2022-07-25 14:30:00"
                val parsedDateTime = LocalDateTime.parse(formattedDateTime, formatter)
                staticLogger.info("---> parsedDateTime = $parsedDateTime with formattedDateTime = $formattedDateTime")

                val durationInMinutes = 25L
//                val startTime = SimpleDateFormat("HH:mm").parse(startTs.toString())
//                val finishTime = SimpleDateFormat("HH:mm").parse(startTs.plus(durationInMinutes,ChronoUnit.MINUTES).toString())

                val startTime = LocalDateTime.parse(formattedDateTime, formatter)
                val finishTime = LocalDateTime.parse(formatter.format(startTs.plus(durationInMinutes,ChronoUnit.MINUTES)), formatter)

                val startTimeAsDate = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());

                staticLogger.info("---> startTime = $startTime to finishTime = $finishTime")

                val amountOfMediStreams = 1
                val appointmentsForAllMedistreams = ArrayList<Appointment>()

                for(i in 1..amountOfMediStreams) {
                    appointmentsForAllMedistreams.add(
                        Appointment(0,UUID.randomUUID(),
                            medistreamId = i.toLong(),
                            startTimestamp = startTs,
                            booked = time%2,
                            appointmentDate = Date.from(startTs),
                            dayLabel = dayLabel,
                            dayName = dayName,
                            dayNameAbbreviator = dayNameAbbr,
                            dayOfMonth = dayOfMonth,
                            monthName= monthName,
                            startTime = startTimeAsDate,
                            duration = durationInMinutes,
                            finishTime = Date(startTimeAsDate.time.plus(durationInMinutes*60L*1000L))))
                }

                appointmentRepo.saveAll(appointmentsForAllMedistreams)

            }
        }
        appointmentRepo.flush()
        staticLogger.info("---> appointmentRepo initialized")

        staticLogger.info("---> Rooms, Patients and Appointments initialized")

    }

}