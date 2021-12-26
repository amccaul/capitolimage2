package com.example.capitol.viewmodel

data class DetailsViewModel (
    val image_name:String,
    val updated: String,
    val uploaded: String,
    val image: ByteArray,
    var thumbnails:List<ByteArray>,
    var metadata:List<MetadataViewModel>,
)

