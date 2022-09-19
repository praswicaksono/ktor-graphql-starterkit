description = "An Example GraphQL service served by Ktor"

plugins {
    id("application")
    id("java")
    kotlin("jvm") version "1.7.10"
    id("com.expediagroup.graphql") version "6.2.2"
    id("io.ktor.plugin") version "2.1.1"
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
}

apply {
    plugin("com.google.devtools.ksp")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

ktor {
    fatJar {
        archiveFileName.set("ktor-gql-fat.jar")
    }
}

repositories {
    mavenCentral()
}

val kotlinCoroutinesVersion: String by project
val ktorVersion: String by project
val logbackVersion: String by project
val kotlinVersion: String by project
val koinVersion: String by project
val koinAnnotationVersion: String by project
val kMongoVersion: String by project
val hopliteVersion: String by project

dependencies {
    // https://mvnrepository.com/artifact/com.expediagroup/graphql-kotlin-server
    implementation("com.expediagroup", "graphql-kotlin-server", "6.2.2")
    implementation("io.ktor", "ktor-server-core", ktorVersion)
    implementation("io.ktor", "ktor-server-netty", ktorVersion)
    implementation("ch.qos.logback", "logback-classic", logbackVersion)
    implementation(kotlin("stdlib", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$kotlinCoroutinesVersion")
    implementation("io.ktor", "ktor-server-websockets", ktorVersion)
    implementation("io.ktor", "ktor-server-cors", ktorVersion)
    implementation("io.ktor", "ktor-server-auth", ktorVersion)
    implementation("io.ktor", "ktor-server-auth-jwt", ktorVersion)
    implementation("com.sksamuel.hoplite", "hoplite-core", hopliteVersion)
    implementation("com.sksamuel.hoplite", "hoplite-yaml", hopliteVersion)
    implementation("org.litote.kmongo", "kmongo-serialization", kMongoVersion)
    implementation("org.litote.kmongo", "kmongo-coroutine-serialization", kMongoVersion)
    compileOnly("io.insert-koin","koin-core", koinVersion)
    implementation("io.insert-koin","koin-ktor", koinVersion)
    compileOnly("io.insert-koin","koin-annotations", koinAnnotationVersion)
    ksp("io.insert-koin", "koin-ksp-compiler", koinAnnotationVersion)

}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

graphql {
    schema {
        packages = listOf("com.graphql.ktor")
    }
}
