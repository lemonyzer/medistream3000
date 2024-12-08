package com.shortcutz.medistream3000.fullCalendar

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "event")
class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var title: String? = null
    var description: String? = null
    var start: LocalDateTime? = null
    var finish: LocalDateTime? = null

    constructor(id: Long?, title: String?, description: String?, start: LocalDateTime?, finish: LocalDateTime?) : super() {
        this.id = id
        this.title = title
        this.description = description
        this.start = start
        this.finish = finish
    }

    constructor() : super()

    override fun toString(): String {
        return ("Event [id=" + id + ", title=" + title + ", description=" + description + ", start=" + start
            + ", finish=" + finish + "]")
    }
}