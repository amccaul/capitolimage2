package com.example.capitol.controller

import com.example.capitol.config.CapitolUserDetails
import com.example.capitol.entity.CapitolImage
import com.example.capitol.service.CapitolImageService
import com.example.capitol.viewmodel.DetailsViewModel
import com.example.capitol.viewmodel.ThumbnailViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException


@RestController
@RequestMapping("/api/user/images")
@CrossOrigin(origins = ["http://localhost:4200"], allowedHeaders = ["*"])
class CapitolImageController (
    @Autowired var capitolImageService: CapitolImageService,
    @Autowired var passwordEncoder: PasswordEncoder,
    @Autowired var authenticationManager: AuthenticationManager
) {
    /*
    @PostMapping("/upload")
    fun handleFileUpload(@RequestParam file: MultipartFile): String {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is CapitolUserDetails) {
            capitolImageService.store(file, principal.capitolUser)
        }
        return "";}
        */
   /* @ResponseBody
    @RequestMapping(
        path = ["fileupload"],
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE]
    )
    @Throws(
        IOException::class
    )
    open fun fileUpload(request: HttpServletRequest) {
        Files.copy(request.inputStream, Paths.get("myfilename"))
    }*/

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
    /**
     * By default list only 20 most recently edited
     */
    @GetMapping("/recent")
    @Throws(IOException::class)
    fun listUploadedFiles_first20(): ResponseEntity<ArrayList<ThumbnailViewModel>?>{
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is CapitolUserDetails) {
           return ResponseEntity
               .ok()
               .body(capitolImageService.get20mostRecentImages_Thumbnails(capitolUser = principal.capitolUser))
        }
        //TODO add error code
        return ResponseEntity
            .notFound()
            .build()

    }


   //@GetMapping("/get/{image_Id}")
   @GetMapping(value = arrayOf("/get/{image_Id}"), produces = arrayOf(MediaType.IMAGE_JPEG_VALUE))
    fun getImage( @PathVariable image_Id: String ): ResponseEntity<ByteArray> {
       var capitolImage: CapitolImage?
       try {
            capitolImage = capitolImageService.getCapitolImage(Integer.parseInt(image_Id))
       } catch( exception:NumberFormatException){
           return ResponseEntity<ByteArray>(HttpStatus.NOT_FOUND)
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
    @GetMapping("/details/{image_Id}")
    fun getImageDetails( @PathVariable image_Id : String ): ResponseEntity<DetailsViewModel> {

        var capitolImage : CapitolImage? = null
        try {
            capitolImage = capitolImageService.getCapitolImage(Integer.parseInt(image_Id))
        } catch( exception:NumberFormatException){
            return ResponseEntity<DetailsViewModel>(HttpStatus.NOT_FOUND)
        }
        if (capitolImage == null){
            return ResponseEntity<DetailsViewModel>(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity
            .ok()
            .body(capitolImageService.loadDetails(capitolImage!!))
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