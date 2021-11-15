package com.example.capitol.entity

import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "capitolimages")
@Entity(name = "CapitolImage")
data class CapitolImage (

    @Id
    @Column(updatable = false, name = "Image_Id")
    var imageId :Int = -1,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(updatable = false, name = "userId")
    var capitolUser:CapitolUser,

    @Column(nullable=false)
    var uploaded: LocalDateTime = LocalDateTime.now(),

    @Column(nullable=false)
    var updated:LocalDateTime = LocalDateTime.now(),

    @Column(nullable=false)
    var url: String = ""

    ){}