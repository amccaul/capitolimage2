package com.example.capitol.viewmodel

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "file")
class ImageUpload {
    lateinit var uploadDir:String;
}