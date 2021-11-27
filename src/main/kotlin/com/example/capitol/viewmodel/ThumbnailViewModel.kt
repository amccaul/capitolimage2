package com.example.capitol.viewmodel

import java.time.LocalDateTime

data class ThumbnailViewModel (
    var image_name:String,
    var updated: LocalDateTime,
    var image: ByteArray
        )