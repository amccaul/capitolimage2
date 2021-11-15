package com.example.capitol.service
import com.example.capitol.repository.StorageRepository
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@Service
class CapitolImageService (
        _storageRepository: StorageRepository
        ){
    var storageRepository : StorageRepository = _storageRepository

    fun init(){

    }
    fun store(file: MultipartFile?){

    }
    fun loadAll(): Stream<Path?>?{

    }
    fun load(filename: String?): Path?{

    }
    fun loadAsResource(filename: String?): Resource?{

    }
    fun deleteAll(){

    }
}