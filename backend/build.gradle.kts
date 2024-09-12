plugins {
    application
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.ivcode.gradle-publish") version "0.1-SNAPSHOT"
}

java {
    withSourcesJar()
}

repositories {
    mavenCentral()
}

// Write version to the build's /resources
tasks.register("write-version") {
    dependsOn(":backend:processResources")
    doLast {
        File(layout.buildDirectory.dir("resources").get().asFile, "main/version.txt").writeText(text = version as String)
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    explicitApi()
    jvmToolchain(21)
}

application {
    mainClass.set("org.ivcode.admin.MainKt")
}


publish {
    groupId = "${project.group}"
    artifactId = rootProject.name
    version = "${project.version}"
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // Liquibase
    implementation("org.liquibase:liquibase-core:4.25.1")

    // MyBatis
    implementation("org.mybatis:mybatis:3.5.14")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")

    implementation("com.mysql:mysql-connector-j:8.2.0")
    implementation("com.h2database:h2:2.2.224")

    // Test
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}
