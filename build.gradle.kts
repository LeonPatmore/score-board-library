plugins {
    kotlin("jvm") version "1.6.10"
}

group = "me.leon"
version = "1.0-SNAPSHOT"

tasks.test {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.4.0")
    testImplementation("org.slf4j:slf4j-simple:2.0.0-alpha7")
}
