package com.shortcutz.medistream3000.danvega04

import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository


@Repository
class UserRepository() {
    private val users: MutableList<User> = ArrayList<User>()

    fun findAll(): List<User> {
        return users
    }

    fun getUser(i: Int) : User {
        return users[i]
    }

    fun updateUser(i: Int, usr: User) {
        users[i] = usr
    }

    fun addUser(user : User) {
        users.add(user)
    }

    fun createFakeUser(): User {
        return User("fullname", "jobTitle", "fake@mail.com", "User")
    }

    fun createFakeUser2(): User {
        return User("fullname22", "jobTitle22", "fake@mail2.com", "User")
    }

    @PostConstruct
    private fun init() {
        val fakers: List<User> = java.util.List.of<User>(createFakeUser(), createFakeUser(), createFakeUser())
        users.addAll(fakers)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserRepository::class.java)
    }
}