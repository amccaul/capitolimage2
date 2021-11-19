package com.example.capitol.file

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration("FileConfig")
@ConfigurationProperties(prefix = "file.image")
class FileEnvConfig {
    lateinit var rootdirectory:String
}