package com.example.capitol.file

import com.example.capitol.entity.CapitolUser
import com.example.capitol.service.CapitolImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOError
import java.io.IOException
import kotlin.jvm.Throws

//TODO put all directory/file management logic in this class
@Service
class FileManagement (var fileEnvConfig : FileEnvConfig) {

    /**
     * @return url to save at
     */
    //TODO add IO error checking
    fun store ( file:MultipartFile, capitolUser: CapitolUser ):String{
        //var imagePath = checkUserDirectory( capitolUser ) + "\\" + file.originalFilename!!
        var myImageDirectory :String= fileEnvConfig.rootdirectory +"\\"+ capitolUser.username + "\\"
        File(myImageDirectory).mkdirs()
        var myImageFile :String = myImageDirectory.plus(file.originalFilename)
        File(myImageDirectory, file.originalFilename)
        file.transferTo(File(myImageFile))
        return myImageFile
    }

    //TODO add IO error checking
    fun load( url:String ) {

    }
}