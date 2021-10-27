package com.example.capitol.viewmodel

import java.time.DateTimeException
import java.time.LocalDateTime
import java.util.*

class UserModel (
    var userId: Int,
    var username: String,
    var role: String,
    var created: LocalDateTime,
    var updated: LocalDateTime,
    var token: String?
)
