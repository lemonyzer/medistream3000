package com.shortcutz.medistream3000.data

import ch.qos.logback.classic.pattern.Abbreviator
import jakarta.persistence.*
import org.hibernate.annotations.Type
import org.hibernate.usertype.UserType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.repository.query.Param
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Repository
import java.io.Serializable
import java.sql.PreparedStatement
import java.time.Instant
import java.util.*


@Entity
@Table(name = "patient")
class PatientEntity(
    @Id
    @Column(nullable = false)
    var patientUuid: UUID,

    @Column(nullable = false)
    val patientFallnummer: Long,

    @Column(nullable = false, length = PATIENT_NAME_MAX_LEN)
    val patientName: String,

    @Column(nullable = false, length = PATIENT_NACHNAME_MAX_LEN)
    val patientNachname: String,

    @OneToOne
    @JoinColumn(name = "roomUuid")
    val room: RoomEntity,

//    @Column(length = FOLDER_TREE_MAX_LEN)
//    var folderTree: String? = null,
//
//    @Column( name = "trace_create_ts", nullable = false)
//    val traceCreateTimestamp: Instant,

) {
    @Version
    var version: Int? = null

    override fun toString(): String {
        return "PatientEntity(patientUuid=$patientUuid, patientName=$patientName, patientNachname=$patientNachname, patientFallnummer=$patientFallnummer, roomUuid=${room.roomUuid}, version=$version)"
    }
}


interface PatientEntityRepository : JpaRepository<PatientEntity, UUID> {

    fun findByPatientUuid(patientUuid: UUID): PatientEntity?

    fun save(entity: PatientEntity): PatientEntity

//    @PostConstruct
//    fun init() {
//
//        var amountOfPatients = 10
//
//        for (i in 1 .. amountOfPatients) {
//            this.save(PatientEntity(UUID.randomUUID(),i.toLong(),"$i","Nach${i + i}", RoomEntity(UUID.randomUUID(),"room-$i",i,2)))
//        }
//    }

}

@Entity
@Table(name = "medistream")
class MediStreamEntity(
    @Id
    @Column(nullable = false)
    var mediStreamUuid: UUID,

    @OneToOne
    @JoinColumn(name = "roomUuid")
    val room: RoomEntity,

    @Column(nullable = false, length = MEDISTREAM_NAME_MAX_LEN)
    val mediStreamName: String,

) {
    @Version
    var version: Int? = null

    override fun toString(): String {
        return "MediStreamEntity(mediStreamName=$mediStreamName, mediStreamUuid=$mediStreamUuid, roomUuid=${room.roomUuid}, version=$version)"
    }
}


interface MediStreamEntityRepository : JpaRepository<MediStreamEntity, UUID> {

    fun findByMediStreamUuid(mediStreamUuid: UUID): MediStreamEntity?

    fun save(entity: MediStreamEntity): MediStreamEntity

}


@Entity
@Table(name = "appointment")
class AppointmentEntity(
    @Id
    @Column(nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    var appointmentUuid: UUID,

    @Column(nullable = false)
    var patientUuid: UUID,

    // TODO: many to many??
    @ManyToOne
    @JoinColumn(name = "mediStreamUuid")
    var mediStreamEntity: MediStreamEntity,

    @Column( name = "begin_ts", nullable = false)
    val beginTimestamp: Instant,

    @Column( name = "duration", nullable = false)
    val duration: Instant,

//    @Column(length = DATA_TYPE_MAX_LEN)
//    var dataType: String? = null,

) {
    @Version
    var version: Int? = null

    override fun toString(): String {
        return "AppointmentEntity(appointmentUuid=$appointmentUuid, patientUuid=$patientUuid, beginTs=$beginTimestamp, duration=$duration, version=$version)"
    }
}


interface AppointmentEntityRepository : JpaRepository<AppointmentEntity, UUID> {

    fun findByAppointmentUuid(appointmentUuid: UUID): AppointmentEntity?

    fun save(entity: AppointmentEntity): AppointmentEntity

}



@Entity
@Table(name = "room")
class RoomEntity(
    @Id
    @Column(nullable = false)
    var roomUuid: UUID,

    @Column(nullable = false, length = ROOM_NAME_MAX_LEN)
    val roomName: String,

    @Column(nullable = false)
    val roomNumber: Int,

    @Column(nullable = false)
    val beds: Int,

    ) {
    @Version
    var version: Int? = null

    override fun toString(): String {
        return "RoomEntity(roomNumber=$roomNumber, roomName=$roomName, beds=$beds, roomUuid=$roomUuid, version=$version)"
    }
}


interface RoomEntityRepository : JpaRepository<RoomEntity, UUID> {

    fun findByRoomUuid(roomUuid: UUID): RoomEntity?

//    fun findEmptyRoom(): RoomEntity? {
//
//    }

    fun save(entity: RoomEntity): RoomEntity

//    @PostConstruct
//    fun init() {
//
//        var amountOfRooms = 20
//        for (i in 1 .. amountOfRooms) {
//            this.save(RoomEntity(UUID.randomUUID(), "Room-$i", i, 2))
//        }
//    }

}


// WARE ! SHARE IT
//
//// TODO: many to many??
////    @ManyToOne
////    @JoinColumn(name = "mediStreamUuid")
////    var mediStreamEntity: MediStreamEntity,
//
//@Entity
//@Table(name = "appointments")
//class WareEntity(
// 	      @Id
//	      @GeneratedValue(strategy = GenerationType.AUTO)
//	      private long id;
//    @Id
//    @Column(nullable = false)
//    var wareUuid: UUID,
//
//
////    @JoinColumn(name = "mediStreamUuid")
////    var roomUuid: RoomEntity,
////
////    @JoinColumn(name = "mediStreamUuid")
////    var mediStreamUuid: MediStreamEntity,
//
//    @Column(nullable = false)//, length = DATA_TYPE_MAX_LEN)
//    val dataType: String,
//
//    @Column(nullable = false)//, length = PARTNER_ID_MAX_LEN)
//    val userId: String,
//
//    @Column(nullable = false, unique = true)
//    var lastEventUuid: UUID,
//
//    @Column( name = "last_event_ts", nullable = false)
//    var lastEventTs: Instant,
//
//    @Column( name = "first_event_ts", nullable = false)
//    val firstEventTs: Instant,
//
//    @Column( name = "first_appointment", nullable = false)
//    val firstAppointmentTime: Instant,
//
////    //date, place / room aka  medistream 1 / 2
////    // val localDate = LocalDate.of(2022, 12, 31)
////    //val sqlDate = java.sql.Date.valueOf(localDate)
////    //var date: ,
////    val localDate: Any = LocalDate.of(2022, 12, 31)
////    val sqlDate = java.sql.Date.valueOf(localDate)
//
//    @Column(nullable = false)//, length = ROOM_NAME_MAX_LEN)
//    val medistreamId: Int = 1,
//
//    @Column(nullable = false)
//    val roomNumber: Int = 123,
//
//    @Column(nullable = false)
//    val beds: Int = 1,
//
//    ) {
//    @Version
//    var version: Int? = null
//
//    override fun toString(): String {
//        return "Ware(appointment=$firstAppointmentTime, roomNumber=$roomNumber, firstEventTs=$firstEventTs, beds=$beds, beds=$beds, version=$version)"
//    }
//}
//
//
//interface WareRepository : JpaRepository<WareEntity, UUID> {
//
//    fun findByRoomUuid(wareUuid: UUID): WareEntity?
//
////    fun findEmptyRoom(): WareEntity? {
////
////    }
//
//    fun save(entity: WareEntity): WareEntity
//
////    @PostConstruct
////    fun init() {
////
////        var amountOfRooms = 20
////        for (i in 1 .. amountOfRooms) {
////            this.save(WareEntity(UUID.randomUUID(), "Room-$i", i, 2))
////        }
////    }
//
//}


@Entity
@Table
class CalenderDayData (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var weekDay: String,

    @Column(nullable = false)
    var dayOfMonth: String,

)

@Repository
interface CalenderDayDataRepository : JpaRepository<CalenderDayData, Long> {

    override fun findById(id: Long): Optional<CalenderDayData>
}


/**
 * Kotlin and Spring Boot
 * In your Kotlin-based Spring Boot application, you can create an entity class with a JSONB column using the @Type annotation and referencing your custom UserType or AttributeConverter implementation.
 *
 * Example Entity Class
 *
 * @Entity
 * @Table(name = "my_table")
 * data class MyEntity(
 *     @Id
 *     val id: Long,
 *
 *     @Type(MyJsonType::class) // or AttributeConverter implementation
 *     val jsonProperty: MyJson
 * )
 *
 * Custom UserType or AttributeConverter Implementation
 * You’ll need to create a custom UserType or AttributeConverter to map your MyJson object to a JSONB column. Here’s an example implementation for a UserType:
 *
 * class MyJsonType : UserType() {
 *     override fun nullSafeSet(param: PreparedStatement, value: Any?, index: Int) {
 *         // implementation to set JSONB value
 *     }
 *
 *     override fun deepCopy(value: Any?): Any {
 *         // implementation to create a deep copy of the JSON object
 *     }
 * }
 *
 *
 *
 */

// https://www.baeldung.com/spring-boot-jpa-storing-postgresql-jsonb

/*class WeekDay : UserType() {
    fun nullSafeSet(param: PreparedStatement, value: Any?, index: Int) {
        // implementation to set JSONB value
    }

    fun deepCopy(value: Any?): Any {
        // implementation to create a deep copy of the JSON object
    }
}

class WeekDaySimple (
    name : String,
    dayOfMonth: String,
    dayLabel: String
)*/

// AppointmentEntity
@Entity
@Table(name = "appointments_simple")
class Appointment (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var appointmentUuid: UUID,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var appointmentDate: Date? = null,

    // Mittwoch 10. Feb.
    @Column(nullable = false)
    var dayLabel: String,

    @Column(nullable = false)
    val dayName: String,

    @Column(nullable = false)
    val dayNameAbbreviator: String,

    @Column(nullable = false)
    val monthName: String,

    @Column(nullable = false)
    val dayOfMonth: String,

    @Column(nullable = true)
    var duration: Long = 25,

    @Column(nullable = true)
    var medistreamId: Long,

    @Column(nullable = true)
    var booked: Int = 0,

    @Column( name = "start_ts", nullable = false)
    var startTimestamp: Instant,

    // https://www.baeldung.com/spring-data-jpa-query-by-date
    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    val startTime: Date,

    // https://www.baeldung.com/spring-data-jpa-query-by-date
    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    val finishTime: Date,

//    @Temporal(TemporalType.TIME)
//    val publicationTime : Date,
//
//    @Temporal(TemporalType.TIMESTAMP)
//    val creationDateTime : Date



    //    @Column(name = "created_at", nullable = false, updatable = false)
    //    var  createdAt : LocalDateTime = LocalDateTime.now(),
    //
    //    @Column(name = "updated_at")
    //    var updatedAt : LocalDateTime,
)

// DTO
data class AppointmentExt (
    var appointments: List<Appointment>,
    // https://www.baeldung.com/spring-data-jpa-query-by-date
    @Temporal(TemporalType.TIME)
    val startTime: Date,


)

//// DTO
//data class AppointmentExt (
//    var id: Long,
//
//    var appointmentUuid: UUID,
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    var appointmentDate: Date? = null,
//
//    var duration: Int = 25,
//
//    var medistreamId: Long,
//
//    var booked: Int = 0,
//
//    val startTimestamp: Instant,
//
//    var day_date: Date,
//
//)

@Repository
interface AppointmentRepository : JpaRepository<Appointment, Long> {

    //fun findById(id: Long): Appointment?

    fun findByAppointmentUuid(appointmentUuid: UUID): Appointment?
    //override fun findById(id: Long): Appointment?

    fun findTop3ByStartTimestampGreaterThanEqual(startTimestamp: Instant): List<Appointment>?

    fun findAllByAppointmentDate(appointmentDate: Date): List<Appointment>?

    fun findByStartTimestampAfter(startTimestamp: Instant): List<Appointment>?
    fun findByStartTimestampGreaterThanEqual(startTimestamp: Instant): List<Appointment>?

    fun findAllByStartTimestampBetweenOrderByStartTimestampAsc(startTimestamp: Instant, endTimestamp: Instant): List<Appointment>?
    fun findTop3ByStartTimestampBetweenOrderByStartTimestampAsc(startTimestamp: Instant, endTimestamp: Instant): List<Appointment>?

    fun findAllByStartTimestampBetweenAndStartTimeBetweenOrderByStartTimestampAsc(startTimestamp: Instant, endTimestamp: Instant, startTime: Date, endTime: Date): List<Appointment>?


    // fun findByStartTimestampBetweenOrderByStartTimestampAsc(startTimestamp: Instant, endTimestamp: Instant): List<Appointment>?
    //.   <----- between in jpa query string?
    fun findByStartTimestampBetweenOrderByStartTimestampAsc(startTimestamp: Instant, endTimestamp: Instant): List<Appointment>?


    //fun findNextXDaysScheduledByDay() : List<Appointment>

//    @org.springframework.data.jpa.repository.Query(
//        """SELECT TO_CHAR(a.startTimestamp,'Day') AS day_name, TO_CHAR(a.startTimestamp,'Mon') AS month_name_abbr, TO_CHAR(a.startTimestamp,'Day') || '. ' || TO_CHAR(a.a.startTimestamp,'Mon')
//        FROM appointments_simple a
//        WHERE
//        a.startTimestamp is between :begin and :finish
//        GROUP BY DATE_TRUNC('day', a.startTimestamp)
//        ORDER BY a.startTimestamp ASC""")

    // SELECT DATE_TRUNC('day', a.start_ts) as date,
    //            a.start_ts,
    //            TO_CHAR(a.start_ts,'Day') AS day_name,
    //            TO_CHAR(a.start_ts,'Mon') AS month_name_abbr,
    //            TO_CHAR(a.start_ts,'Day') || ' ' || TO_CHAR(a.start_ts,'D') || '. ' || TO_CHAR(a.start_ts,'Mon') as day_label
    //
    //            FROM appointments_simple a
    //            WHERE a.start_ts between CURRENT_DATE and DATEADD('day',6,CURRENT_DATE)
    //            ORDER BY a.start_ts

    // SELECT DATE_TRUNC('day', a.start_ts) as date,
    //            a.start_ts,
    //            TO_CHAR(a.start_ts,'Day') AS day_name,
    //            TO_CHAR(a.start_ts,'Mon') AS month_name_abbr,
    //            TO_CHAR(a.start_ts,'Day') || ' ' || TO_CHAR(a.start_ts,'D') || '. ' || TO_CHAR(a.start_ts,'Mon') as day_label
    //
    //            FROM appointments_simple a
    //            WHERE a.start_ts between CURRENT_DATE and DATEADD('day',6,CURRENT_DATE)
    //            ORDER BY a.start_ts

    // # DATEADD(DAY, 1, DATE '2001-01-31')

    @org.springframework.data.jpa.repository.Query(
        """SELECT DATE_TRUNC('day', a.start_ts) as date,
            a.start_ts,
            TO_CHAR(a.start_ts,'Day') AS day_name,
            TO_CHAR(a.start_ts,'Mon') AS month_name_abbr,
            TO_CHAR(a.start_ts,'Day') || ' ' || TO_CHAR(a.start_ts,'D') || '. ' || TO_CHAR(a.start_ts,'Mon') as day_label
            
            FROM appointments_simple a
            WHERE a.start_ts between :begin and :finish
            
            ORDER BY a.start_ts ASC
        """, nativeQuery = true)
    fun getAllAppointmentsAkaNextXDaysScheduledByDay(@Param("begin") begin: String?,
                                                      @Param("finish") finish: String?): List<Objects>? // neue spalten hinzugekommen keine AppointmentObjekt mehr!


    fun findByAppointmentDate(appointmentDate: Date): Appointment?

    fun findByMedistreamId(medistreamId: Long) : List<Appointment>?


    fun save(entity: Appointment): Appointment

}

class Student : Serializable {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private val birthDate: Date? = null

}