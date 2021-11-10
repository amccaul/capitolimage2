import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    war
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    kotlin("plugin.jpa") version "1.4.21"
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

    //doesnt work for some reason
    //implementation ("org.springframework.security:spring-security-config:5.4.2")
    //use this isntead
    implementation("org.springframework.boot:spring-boot-starter-security:2.4.2")
    implementation("junit:junit:4.13.1")

    testImplementation ("org.junit.jupiter:junit-jupiter:5.6.0")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    /* Database driver */
    runtimeOnly("org.postgresql:postgresql")
    //runtime("org.postgresql:postgresql")
    //implementation("org.postgresql:postgresql")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    //runtime(group = "org.postgresql", name = "postgresql", version = "42.1.4")


    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

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

