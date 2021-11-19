package com.example.capitol.service

import com.example.capitol.config.CapitolUserDetailsService
import com.example.capitol.entity.CapitolImage
import com.example.capitol.entity.CapitolUser
import com.example.capitol.file.FileManagement
import com.example.capitol.repository.CapitolImageRepository
import com.example.capitol.repository.CapitolUserRepository
import org.springframework.beans.factory.annotation.Autowired
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
        var capitolImageRepository: CapitolImageRepository,
        var fileManagement: FileManagement
        ){
    //var capitolImageRepository : CapitolImageRepository = _capitolImageRepository
    //var fileManagement : FileManagement = _fileManagement
    fun init(){

    }
    fun store(file: MultipartFile, capitolUser: CapitolUser){
        //TODO error check
        var url =  fileManagement.store(file, capitolUser)

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
    fun load(): Stream<Path?>?{
        return null;
    }
    fun loadAll(capitolImages: Set<CapitolImage>) {
        for (capitolImage in capitolImages) {
            fileManagement.load(capitolImage.url)
        }
    }

    fun loadAsResource(filename: String?): Resource?{
        return null;
    }
    fun deleteAll(){
    }
}

