package com.example.capitol.repository

import com.example.capitol.entity.CapitolImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface CapitolImageRepository : JpaRepository<CapitolImage, Int> {
    @Query("SELECT i FROM capitolimage i WHERE i.capitolUser.user_Id = :user_Id AND i.image_name LIKE :imageName" )
    fun findByImageAndUserId(user_Id: Int, imageName: String): CapitolImage?

    @Transactional
    @Modifying
    @Query("UPDATE capitolimage i SET i.url = :url WHERE i.image_Id = :image_Id ")
    fun updateUrlByimageId(image_Id: Int, url: String)

    @Transactional
    @Modifying
    @Query("UPDATE capitolimage i SET i.thumbnailurl = :thumbnailurl WHERE i.image_Id = :image_Id ")
    fun updateThumbnailUrlByimageId(image_Id: Int, thumbnailurl: String)

    @Query ("SELECT * FROM capitolimage i WHERE i.user_Id = :user_Id " +
            "ORDER BY updated DESC LIMIT 20", nativeQuery=true)
    fun get20mostRecentCapitolImages( user_Id: Int ):Array<CapitolImage>
}
