package com.shortcutz.medistream3000.blog

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var text: String? = null
    var dateAdded: LocalDateTime? = null
    var dateModified: LocalDateTime? = null
}