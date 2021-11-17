package com.example.capitol.viewmodel

import com.example.capitol.entity.CapitolImage
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

/**
 * For returning images in the gallery
 */
data class ImageViewModel (
    var name: String,
    var fileSize : String,
    var uploadDate : LocalDateTime,
        ){
    //TODO define a default picture
    lateinit var image : MultipartFile

    constructor(capitolImage: CapitolImage): this("Not found","Not found",LocalDateTime.MIN){
        this.uploadDate = LocalDateTime.now()
        this.name = capitolImage.url
    }
    constructor( name: String, fileSize : String, uploadDate : LocalDateTime, image:MultipartFile ): this("Not found", "Not found", LocalDateTime.MIN){
        this.uploadDate = LocalDateTime.now()
        this.name = name
        this.fileSize = image.size.toString()
        this.image = image
    }
    override fun toString(): String {
        return "Name:"+ name + "  Filesize:"+fileSize+"  Upload date:"+uploadDate.toString()+" File URL:"+name
    }
}