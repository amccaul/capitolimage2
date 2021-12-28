package com.example.capitol.service

import com.drew.imaging.ImageMetadataReader
import com.example.capitol.entity.CapitolImage
import com.example.capitol.entity.CapitolUser
import com.example.capitol.file.FileEnvConfig
import com.example.capitol.repository.CapitolImageRepository
import com.example.capitol.viewmodel.DetailsViewModel
import com.example.capitol.viewmodel.MetadataViewModel
import com.example.capitol.viewmodel.ThumbnailViewModel
import org.apache.commons.imaging.Imaging
import org.apache.commons.imaging.common.ImageMetadata
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
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



        //figure out a queue system to generate OCR

    }

    fun get20mostRecentImages_Thumbnails(capitolUser: CapitolUser):ArrayList<ThumbnailViewModel>{
        var thumbnails : ArrayList<ThumbnailViewModel> = ArrayList<ThumbnailViewModel>()

        var imgs: Array<CapitolImage> = capitolImageRepository.get20mostRecentCapitolImages(capitolUser.user_Id)
        for (img in imgs){
            //val bytes :ByteArray = File(img.thumbnailurl).readBytes()
            var metadata: ImageMetadata = Imaging.getMetadata(File(img.url).readBytes())
            var thumbnail =File("C:\\Users\\Alec\\Pictures\\output\\qmark.jpg").readBytes()
            //val inputData = contentResolver.openInputStream(uri)?.readBytes()

            if (metadata is JpegImageMetadata) {
                println("if (metadata is JpegImageMetadata) { ")
                if (metadata.exifThumbnailData!=null) {
                    println("if (metadata.exifThumbnailData!=null) {")
                    thumbnail = metadata.exifThumbnailData
                    println(metadata.exifThumbnailData)
                }
            }
            thumbnails.add( ThumbnailViewModel(img.image_name, img.updated, thumbnail))
        }
        return thumbnails
    }

    fun loadDetails(capitolImage: CapitolImage):DetailsViewModel{
        /*
        var thumbnails : ArrayList<ThumbnailViewModel> = ArrayList<ThumbnailViewModel>()

        var imgs: Array<CapitolImage> = capitolImageRepository.get20mostRecentCapitolImages(capitolUser.user_Id)
        for (img in imgs){
            //val bytes :ByteArray = File(img.thumbnailurl).readBytes()
            var metadata: ImageMetadata = Imaging.getMetadata(File(img.url).readBytes())
            var thumbnail : ByteArray = File("C:\\Users\\Alec\\Pictures\\output\\qmark.jpg").readBytes()
            if (metadata is JpegImageMetadata) {
                if (metadata.exifThumbnailData!=null) {
                    thumbnail = metadata.exifThumbnailData
                }
            }
            thumbnails.add( ThumbnailViewModel(img.image_name, img.updated, thumbnail))
        }
        */
        var myImageFile = File(capitolImage.url)
        var directories  = ImageMetadataReader.readMetadata(myImageFile)
        var myMetadata:List<MetadataViewModel> = ArrayList<MetadataViewModel>()

        //TODO make this actually output metadata
        for (directory in directories.directories){
            for ( tag in directory.tags ){
                myMetadata.plus( MetadataViewModel( tag.tagName , tag.description  ) )
            }
        }

        var img = ImageIO.read(myImageFile)
        val baos = ByteArrayOutputStream()
        ImageIO.write(img, "jpg", baos)
        val bytes: ByteArray = baos.toByteArray()

        var output = DetailsViewModel(capitolImage.image_name,
            capitolImage.updated.toString(),
            capitolImage.uploaded.toString(),
            bytes,
            ArrayList<ByteArray>(),
            myMetadata
        )
        return output
    }




    fun updateUrlByimageId(image_Id: Int, url: String) {
        capitolImageRepository.updateUrlByimageId(image_Id, url)
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

