package com.example.capitol.entity

import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "capitolimage", schema = "public")
@Entity(name = "capitolimage")
data class CapitolImage (

    @ManyToOne// (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(updatable = false, name = "user_Id")
    var capitolUser:CapitolUser,
    @Id
    @Column(updatable = false, name = "Image_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var image_Id :Int = -1,

    @Column
    var image_name : String = "",

    @Column(nullable=false)
    var url: String,

    @Column
    var thumbnailurl: String = "",

    @Column(nullable=false)
    var uploaded: LocalDateTime = LocalDateTime.now(),

    @Column(nullable=false)
    var updated:LocalDateTime = LocalDateTime.now(),
    ){
}