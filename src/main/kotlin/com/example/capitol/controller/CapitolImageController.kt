package com.example.capitol.controller

import com.example.capitol.config.CapitolUserDetails
import com.example.capitol.entity.CapitolImage
import com.example.capitol.service.CapitolImageService
import com.example.capitol.viewmodel.ThumbnailViewModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException


@RestController
@RequestMapping("/api/user/images")
@CrossOrigin(origins = ["http://localhost:4200"], allowedHeaders = ["*"])
class CapitolImageController (var capitolImageService: CapitolImageService) {
    /*
    @PostMapping("/upload")
    fun handleFileUpload(@RequestParam file: MultipartFile): String {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is CapitolUserDetails) {
            capitolImageService.store(file, principal.capitolUser)
        }
        return "";}
        */

    @PostMapping("/upload")
    fun handleFileUpload(@RequestParam files: Array<MultipartFile>): String {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is CapitolUserDetails) {
            for ( file in files ) {
                capitolImageService.store(file, principal.capitolUser)
            }
        } else {
            //TODO replace with error code
            return "Unsuccessful"
        }
        return "Successful upload";
    }

        //TODO figure out what this stuff means
        /*
    redirectAttributes.addFlashAttribute(
        "message",
        "You successfully uploaded " + file.originalFilename + "!"
    )
    return "redirect:/"*/


    /**
     * By default list only 20 most recently edited
     */
    @GetMapping("/recent")
    @Throws(IOException::class)
    fun listUploadedFiles_first20(): ArrayList<ThumbnailViewModel>?{
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is CapitolUserDetails) {
           return capitolImageService.get20mostRecentImages_Thumbnails(capitolUser = principal.capitolUser)
        }
        //TODO add error code
        return null
    }
    /*
        model.addAttribute("files", capitolImageService.loadAll()?.map { path ->
            MvcUriComponentsBuilder.fromMethodName(
                FileUploadController::class.java,
                "serveFile", path!!.getFileName().toString()
            ).build().toUri().toString()
        }
            ?.collect(Collectors.toList()))
        return "uploadForm"
    }*/


    @GetMapping("/thumbnails")
    @Throws(IOException::class)
    fun listOfFiles(): String? {
        /*model.addAttribute("files", capitolImageService.loadAll()?.map { path ->
            MvcUriComponentsBuilder.fromMethodName(
                CapitolImageController::class.java,
                "serveFile", path!!.getFileName().toString()
            ).build().toUri().toString()
        }
            ?.collect(Collectors.toList()))
        return "uploadForm"*/
        return null
    }

   @GetMapping("/get/{imageName}")
    fun getImage( @PathVariable imageName : String ): ResponseEntity<ByteArray> {
       val principal = SecurityContextHolder.getContext().authentication.principal
       var capitolImage : CapitolImage? = null
       if (principal is CapitolUserDetails) {
           capitolImage = capitolImageService.getCapitolImageByImageName(principal.capitolUser, imageName)
       }
       if (capitolImage == null){
           return ResponseEntity<ByteArray>(HttpStatus.NOT_FOUND)
       }

        var bytes : ByteArray = capitolImageService.load(capitolImage!!)
        return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(bytes)

    }
/*
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    fun serveFile(@PathVariable filename: String?): ResponseEntity<Resource> {
        val file: Resource? = capitolImageService.loadAsResource(filename)
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file!!.filename.toString() + "\"")
            .body<Resource>(file)
    }
*/


    /*
    @ExceptionHandler(StorageFileNotFoundException::class)
    fun handleStorageFileNotFound(exc: StorageFileNotFoundException?): ResponseEntity<*> {
        return ResponseEntity.notFound().build<Any>()
    }*/
}