package com.example.capitol.repository

import com.example.capitol.entity.CapitolImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CapitolImageRepository : JpaRepository<CapitolImage, Int> {}
