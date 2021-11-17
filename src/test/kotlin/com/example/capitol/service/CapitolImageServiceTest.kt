package com.example.capitol.service

import com.example.capitol.config.CapitolUserDetailsService
import com.example.capitol.entity.CapitolImage
import com.example.capitol.entity.CapitolUser
import com.example.capitol.repository.CapitolImageRepository
import com.example.capitol.repository.CapitolUserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.io.File


internal class CapitolImageServiceTest {

    var capitolImageRepository:CapitolImageRepository = Mockito.mock(CapitolImageRepository::class.java)
    var capitolImageService:CapitolImageService = CapitolImageService(capitolImageRepository)

    var capitolUserRepository:CapitolUserRepository = Mockito.mock(CapitolUserRepository::class.java)

    var capitalUserDetailsService:CapitolUserDetailsService = CapitolUserDetailsService(capitolUserRepository);

    lateinit var exampleImage : MultipartFile
    var cu : CapitolUser = CapitolUser( user_Id = 1, username = "TestImages", password = "123")
    var ci : CapitolImage = CapitolImage(capitolUser = cu)
    @BeforeEach
    fun setupAll(){
        //load multipart exampleImage file from disk
        //Mockito.`when`(capitolImageRepository.saveAll(listOf(cu))).thenReturn()
    }
    @Test
    fun save(){
        assertThat(capitolImageService.save(ci)).isSameAs(ci)
    }


    @Test
    fun store() {
        var img : File = File("C:\\Users\\Alec\\Pictures\\chad.jpg")
        var image : MultipartFile = MockMultipartFile("Test file", "chad.jpg", MediaType.IMAGE_JPEG_VALUE, img.readBytes())
        capitolImageService.store(image, cu)


    }
    /*

    @Test
    @Throws(Exception::class)
    fun shouldSaveUploadedFile() {
        val multipartFile = MockMultipartFile(
            "file", "test.txt",
            "text/plain", "Spring Framework".toByteArray()
        )
        this.mvc.perform(multipart("/").file(multipartFile))
            .andExpect(status().isFound())
            .andExpect(header().string("Location", "/"))
        then(this.storageService).should().store(multipartFile)
    }
*/

    /*
    @Test
    fun load() {
    }
    */
}