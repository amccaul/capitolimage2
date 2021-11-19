package com.example.capitol.controller

import com.example.capitol.config.CapitolUserDetails
import com.example.capitol.service.CapitolImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.io.IOException
import java.util.stream.Collectors


@RestController
@RequestMapping("/api/user/images")
@CrossOrigin(origins = ["http://localhost:4200"], allowedHeaders = ["*"])
class CapitolImageController (var capitolImageService: CapitolImageService) {
    @PostMapping("/upload")
    fun handleFileUpload(@RequestParam file: MultipartFile): String {
        //TODO figure out a better way of getting better username
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is CapitolUserDetails) {
            capitolImageService.store(file, principal.capitolUser)
        }
        return "";}
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
    @GetMapping("/list")
    @Throws(IOException::class)
    fun listUploadedFiles_first20(): String {
        //capitolImageService.loadFirst20()
        return "";
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

    @GetMapping("/images/thumbnails")
    @Throws(IOException::class)
    fun listUploadedFiles(model: Model): String {
        model.addAttribute("files", capitolImageService.loadAll()?.map { path ->
            MvcUriComponentsBuilder.fromMethodName(
                CapitolImageController::class.java,
                "serveFile", path!!.getFileName().toString()
            ).build().toUri().toString()
        }
            ?.collect(Collectors.toList()))
        return "uploadForm"
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    fun serveFile(@PathVariable filename: String?): ResponseEntity<Resource> {
        val file: Resource? = capitolImageService.loadAsResource(filename)
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file!!.filename.toString() + "\"")
            .body<Resource>(file)
    }


    /*
    @ExceptionHandler(StorageFileNotFoundException::class)
    fun handleStorageFileNotFound(exc: StorageFileNotFoundException?): ResponseEntity<*> {
        return ResponseEntity.notFound().build<Any>()
    }*/
}