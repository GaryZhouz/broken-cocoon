plugins {
    id("java")
}

group = "com.thoughtworks"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.hibernate:hibernate-core:5.4.24.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:2.2.2")
    implementation("org.hsqldb:hsqldb:2.7.1")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

tasks.test {
    useJUnitPlatform()
}
