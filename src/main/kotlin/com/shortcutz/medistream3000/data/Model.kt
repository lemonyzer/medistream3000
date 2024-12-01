package com.shortcutz.medistream3000.data

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
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

    @Column(nullable = true)
    var duration: Int = 25,

    @Column(nullable = true)
    var medistreamId: Long,

    @Column( name = "start_ts", nullable = false)
    val startTimestamp: Instant,

    //    @Column(name = "created_at", nullable = false, updatable = false)
    //    var  createdAt : LocalDateTime = LocalDateTime.now(),
    //
    //    @Column(name = "updated_at")
    //    var updatedAt : LocalDateTime,
)

interface AppointmentRepository : JpaRepository<Appointment, Long> {

    //fun findById(id: Long): Appointment?

    fun findByAppointmentUuid(appointmentUuid: UUID): Appointment?

    fun findAllByAppointmentDate(appointmentDate: Date): List<Appointment>?

    fun findByAppointmentDate(appointmentDate: Date): Appointment?

    fun findByMedistreamId(medistreamId: Long) : List<Appointment>?


    fun save(entity: Appointment): Appointment

}

class Student : Serializable {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private val birthDate: Date? = null

}