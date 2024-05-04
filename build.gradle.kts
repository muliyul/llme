plugins {
	kotlin("jvm") version "1.9.0"
	application
}

group = "com.muliyul"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform("ru.vyarus.guicey:guicey-bom:7.1.3"))
	implementation("ru.vyarus:dropwizard-guicey")
	implementation("io.dropwizard:dropwizard-core")
	implementation("io.dropwizard:dropwizard-auth")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.+")
	// https://mvnrepository.com/artifact/io.swagger/swagger-jaxrs
	implementation("io.swagger.core.v3:swagger-jaxrs2-jakarta:2.2.7")
	implementation("io.swagger.core.v3:swagger-jaxrs2-jakarta-servlet-initializer-v2:2.2.7")

	// https://mvnrepository.com/artifact/dev.langchain4j/langchain4j-mistral-ai
	implementation("dev.langchain4j:langchain4j:0.30.0")
	implementation("dev.langchain4j:langchain4j-ollama:0.30.0")
	implementation("dev.langchain4j:langchain4j-open-ai:0.30.0")
	// https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
	implementation("org.seleniumhq.selenium:selenium-java:4.20.0")

	testImplementation(kotlin("test"))
	testImplementation("io.dropwizard:dropwizard-testing")
}

tasks.test {
	useJUnitPlatform()
}

kotlin {

}

application {
	mainClass = "com.muliyul.llme.LlmeServerKt"
}

