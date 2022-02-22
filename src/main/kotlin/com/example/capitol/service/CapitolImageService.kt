package com.example.capitol.service

import com.example.capitol.entity.CapitolImage
import com.example.capitol.entity.CapitolUser
import com.example.capitol.file.FileEnvConfig
import com.example.capitol.repository.CapitolImageRepository
import com.example.capitol.viewmodel.ThumbnailViewModel
import net.coobird.thumbnailator.Thumbnails
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import javax.imageio.ImageIO


@Service
class CapitolImageService (
    var capitolImageRepository: CapitolImageRepository,
    var fileEnvConfig : FileEnvConfig
        ){
    //var capitolImageRepository : CapitolImageRepository = _capitolImageRepository
    //var fileManagement : FileManagement = _fileManagement
    fun init(){

    }
    fun store(file: MultipartFile, capitolUser: CapitolUser){
        //TODO error check


        if ( ImageIO.read(file.inputStream) == null){
            throw IOException("File does not contain image")
        }

        var capitolImage = CapitolImage(
            capitolUser = capitolUser,
            image_name = file.originalFilename.toString()
        )

        // Gets metadata from file
       // var metadata : ImageMetadata = Imaging.getMetadata(file.bytes)
        /*
        val iis = ImageIO.createImageInputStream(file.inputStream)
        val readers: Iterator<ImageReader> = ImageIO.getImageReaders(iis)
       */


        /*
        *  Saves file to get automatically generated imageID, then updates the database again using the imageID as URL path.
        * Also saves file to disk
         */
        var savedCapitolImage = this.save(capitolImage)
        //TODO change this with file scan
        var myFileType = file.originalFilename.toString().substring(file.originalFilename.toString().lastIndexOf(".") + 1);
        var myImageDirectory: String = fileEnvConfig.rootdirectory + "\\" + capitolUser.username + "\\"
        File(myImageDirectory).mkdirs()

        var myImageFileURL : String = myImageDirectory + savedCapitolImage.image_Id
        File(myImageFileURL+"."+myFileType).createNewFile()
        File(myImageFileURL+"."+myFileType).writeBytes(file.bytes)
        this.updateUrlByimageId( savedCapitolImage.image_Id, myImageFileURL+"."+myFileType )



        //regardless of what the og file type is the thumb gets saved as jpg
        var myThumb = Thumbnails.of(  myImageFileURL+"."+myFileType  )
            .size(200, 200)
            .outputFormat("jpg")
            .asBufferedImage()
        //  myImageFileURL+"_thumb."+myFileType
        ImageIO.write(myThumb, "jpg", File(myImageFileURL+"_thumb.jpg"))
        this.updateThumbnailUrlByimageId( savedCapitolImage.image_Id, myImageFileURL+"_thumb.jpg")

     //figure out a queue system to generate OCR

    }



    fun get20mostRecentImages_Thumbnails(capitolUser: CapitolUser):ArrayList<ThumbnailViewModel>{
        var thumbnails : ArrayList<ThumbnailViewModel> = ArrayList<ThumbnailViewModel>()

        var imgs: Array<CapitolImage> = capitolImageRepository.get20mostRecentCapitolImages(capitolUser.user_Id)
        for (img in imgs){
            var bytes = File(img.thumbnailurl).readBytes()
            thumbnails.add( ThumbnailViewModel(img.image_name, img.updated, bytes, img.image_Id))
        }
        return thumbnails
    }




    fun updateUrlByimageId(image_Id: Int, url: String) {
        capitolImageRepository.updateUrlByimageId(image_Id, url)
    }
    fun updateThumbnailUrlByimageId(image_Id: Int, thumbnailurl: String) {
        capitolImageRepository.updateThumbnailUrlByimageId(image_Id, thumbnailurl)
    }

    fun getCapitolImage(image_Id: Int):CapitolImage?{
        //return capitolImageRepository.findByImageAndUserId(user.user_Id, imageName)
        //TODO remove
        //var capitolImage : CapitolImage? = capitolImageRepository.findByImageAndUserId(user.user_Id, imageName)
        //println("Capitolimage:"+capitolImage!!.url)

        return capitolImageRepository.findByImageId(image_Id)
    }
    private fun saveAll( capitolImage: List<CapitolImage>):List<CapitolImage>{
        return capitolImageRepository.saveAll(capitolImage)
    }

    fun save (capitolImage: CapitolImage): CapitolImage{
        return capitolImageRepository.save(capitolImage)
    }

    fun load(capitolImage: CapitolImage):ByteArray {
        if (!File(capitolImage.url).exists())
            throw FileNotFoundException()
        if (!File(capitolImage.url).canRead())
            throw IOException()
        return File(capitolImage.url).readBytes()
    }
    fun loadThumbnail(capitolImage: CapitolImage):ByteArray {
        if (!File(capitolImage.url).exists())
            throw FileNotFoundException()
        if (!File(capitolImage.url).canRead())
            throw IOException()
        return File(capitolImage.thumbnailurl).readBytes()
    }
    /*
    fun loadAll(capitolImages: Set<CapitolImage>):Set<Resource> {
        for (capitolImage in capitolImages) {
            fileManagement.loadAsResource(capitolImage.url)
        }
    }
*/
    fun loadAsResource(filename: String): Resource{
        var urlResource: UrlResource = UrlResource(filename)
        if (!urlResource.exists()){}
        //TODO throw exception
        return urlResource
    }
    fun deleteAll(){
        var myImageDirectory: String = fileEnvConfig.rootdirectory
        if (!File(myImageDirectory).isDirectory)
            throw IOException("User directory not found")
        capitolImageRepository.deleteAll()
    }
    fun deleteAllByUser(capitolUser: CapitolUser):Boolean{
        var myImageDirectory: String = fileEnvConfig.rootdirectory + "\\" + capitolUser.username + "\\"
        if (!File(myImageDirectory).isDirectory)
            throw IOException("User directory not found")
        return File(myImageDirectory).delete()
    }
}

