package com.shortcutz.medistream3000.data

import jakarta.annotation.PostConstruct
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
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