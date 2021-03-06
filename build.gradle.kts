import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    war
    //kotlin("jvm") version "1.4.21"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.4.21"
    kotlin("plugin.jpa") version "1.4.21"

    kotlin("plugin.serialization") version "1.5.31"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()

}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    //Stuff I added in
    implementation ("org.springframework:spring-context-support")
    implementation("javax.mail:mail:1.5.0-b01")
    implementation ("javax.mail:javax.mail-api:1.6.2")
    //implementation ("org.springframework.security:spring-security-crypto:5.4.2")
    implementation ("javax.validation:validation-api:2.0.1.Final")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.0.RELEASE")
// https://mvnrepository.com/artifact/commons-io/commons-io
    implementation("commons-io:commons-io:2.11.0")

    //doesnt work for some reason
    //implementation ("org.springframework.security:spring-security-config:5.4.2")
    //use this isntead
    implementation("org.springframework.boot:spring-boot-starter-security:2.4.2")
    implementation("junit:junit:4.13.1")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-configuration-processor
    //implementation("org.springframework.boot:spring-boot-configuration-processor:2.5.6")
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
    //There should be json parsing/gson built into spring framework now?  Maybe?
    //implementation("com.google.code.gson:gson:2.7")
    //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
    implementation ("com.google.code.gson:gson:2.8.5")

    //Library to read image data
    // https://mvnrepository.com/artifact/org.apache.commons/commons-imaging
    implementation("org.apache.commons:commons-imaging:1.0-alpha2")

    developmentOnly("org.springframework.boot:spring-boot-devtools")


    /* Database driver */
    runtimeOnly("org.postgresql:postgresql")
    //runtime("org.postgresql:postgresql")
    //implementation("org.postgresql:postgresql")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    //runtime(group = "org.postgresql", name = "postgresql", version = "42.1.4")


    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test:5.5.3")
    testImplementation ("org.junit.jupiter:junit-jupiter:5.6.0")

// https://mvnrepository.com/artifact/org.springframework/spring-mock
    //testImplementation("org.springframework:spring-mock:2.0.8")
    implementation("org.springframework:spring-mock:2.0.8")

    implementation("com.drewnoakes:metadata-extractor:2.16.0")


    //too complicated to use
    // https://mvnrepository.com/artifact/net.imagej/ij
    //implementation("net.imagej:ij:1.53j")

    // https://mvnrepository.com/artifact/net.coobird/thumbnailator
    implementation("net.coobird:thumbnailator:0.4.17")

    //can't get this working for some reason, dependency won't resolve
    // https://mvnrepository.com/artifact/com.github.downgoon/marvin
    //    implementation("com.github.downgoon:marvin:1.5.5")


}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

