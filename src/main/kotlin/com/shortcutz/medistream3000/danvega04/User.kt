package com.shortcutz.medistream3000.danvega04



data class User(
    val name: String,
    val title: String,
    val email: String,
    val role: String,
    val id: Int=0,
)

class UserFormData(
    var name: String,
    var title: String,
    var email: String,
    var role: String,
)