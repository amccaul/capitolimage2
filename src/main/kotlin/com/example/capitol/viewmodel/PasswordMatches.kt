package com.example.capitol.viewmodel

import org.aspectj.weaver.AnnotationTargetKind
import org.jvnet.staxex.StAxSOAPBody.Payload

import java.lang.annotation.Documented

import org.aspectj.weaver.AnnotationTargetKind.ANNOTATION_TYPE
import java.lang.annotation.ElementType.TYPE


import javax.validation.Constraint
import kotlin.reflect.KClass

//TODO figure out what these annotations do
//@Target([TYPE, ANNOTATION_TYPE])
//@Constraint(validatedBy = PasswordMatchesValidator::class)

@Retention(AnnotationRetention.RUNTIME)
@Documented
annotation class PasswordMatches(
    val message: String = "Passwords don't match",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Payload>> = []
)
