/*
 * This file was generated by the Gradle("init") task.
 */

plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    compileOnly("javax.xml.bind:jaxb-api:2.3.0")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    api("org.springframework.boot:spring-boot-starter-web:2.2.2.RELEASE")
    api("org.springframework.boot:spring-boot-starter-data-jpa:2.2.2.RELEASE")
    api("org.json:json:20230227")
    runtimeOnly("com.h2database:h2:1.4.200")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.0")
    testImplementation("org.junit.vintage:junit-vintage-engine:5.5.2")
    compileOnly("org.projectlombok:lombok:1.18.26")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.3")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.3")
    annotationProcessor("jakarta.xml.bind:jakarta.xml.bind-api:2.3.3")
    annotationProcessor("org.glassfish.jaxb:jaxb-runtime:2.3.3")
    annotationProcessor("javax.annotation:javax.annotation-api:1.3.2")
}

group = "org.example"
version = "1.0-SNAPSHOT"
description = "testtask"
java.sourceCompatibility = JavaVersion.VERSION_11

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
