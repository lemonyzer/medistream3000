package com.shortcutz.medistream3000.fullCalendar

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Repository
internal interface EventJpaRepository : JpaRepository<Event?, Long?> {


    /* This one uses an @Query */
    @Query("select b from Event b where b.start >= ?1 and b.finish <= ?2")
    fun findByDateBetween(start: LocalDateTime?, end: LocalDateTime?): List<Event?>?
}