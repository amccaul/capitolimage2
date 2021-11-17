package com.example.capitol.service

import com.example.capitol.config.CapitolUserDetailsService
import com.example.capitol.entity.CapitolImage
import com.example.capitol.entity.CapitolUser
import com.example.capitol.repository.CapitolImageRepository
import com.example.capitol.repository.CapitolUserRepository
import org.springframework.core.io.Resource
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOError
import java.nio.file.Path
import java.util.stream.Stream


@Service
class CapitolImageService (
        _capitolImageRepository: CapitolImageRepository
        ){
    var capitolImageRepository : CapitolImageRepository = _capitolImageRepository

    //TODO figure out how to get this from application.properties
    var ROOT_PATH = "C:\\Users\\Alec\\Pictures\\output"

    fun init(){

    }
    fun store(file: MultipartFile, capitolUser: CapitolUser){
        //TODO remove this and replace with error check
        /*if (!File(ROOT_PATH).isFile)
            File(ROOT_PATH).createNewFile()
        if (!File(ROOT_PATH+capitolUser.username).isFile)
            File(ROOT_PATH+capitolUser.username).createNewFile()*/
        var url:String =  ROOT_PATH + capitolUser.username + "/" + file.originalFilename
        //var url =  "C:\\Users\\Alec\\Pictures\\output\\chad.jpg"
        File( url ).createNewFile()
        file.transferTo(File(url))
        var capitolImage = CapitolImage(
            capitolUser = capitolUser,
            image_name = file.originalFilename.toString(),
            url = url)
        this.save(capitolImage)

        //TODO figure out a queue system to generate thumbnail and OCR

    }
    private fun saveAll( capitolImage: List<CapitolImage>):List<CapitolImage>{
        return capitolImageRepository.saveAll(capitolImage)
    }
    fun save (capitolImage: CapitolImage): CapitolImage{
        return capitolImageRepository.save(capitolImage)
    }
    //TODO remove
    /*fun save (capitolUser: CapitolUser):CapitolUser {
        var listCapitolUser:List<CapitolUser> = listOf(capitolUser)
        return this.saveAll(listCapitolUser).get(0)
    }*/
    fun loadAll(): Stream<Path?>?{
        return null;
    }
    fun load(filename: String?): Path?{
        return null;
    }
    fun loadAsResource(filename: String?): Resource?{
        return null;
    }
    fun deleteAll(){

    }
}