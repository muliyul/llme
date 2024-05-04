package com.muliyul.llme

import com.fasterxml.jackson.module.kotlin.*
import com.muliyul.llme.llm.*
import io.dropwizard.configuration.EnvironmentVariableSubstitutor
import io.dropwizard.configuration.ResourceConfigurationSourceProvider
import io.dropwizard.configuration.SubstitutingSourceProvider
import io.dropwizard.core.*
import io.dropwizard.core.setup.*
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource
import org.glassfish.jersey.server.*
import ru.vyarus.dropwizard.guice.*


fun main(args: Array<String>) = LlmeServer().run(*args)

class LlmeServer : Application<LlmeConfiguration>() {

	override fun initialize(bootstrap: Bootstrap<LlmeConfiguration>) {
		bootstrap.configurationSourceProvider =
			SubstitutingSourceProvider(ResourceConfigurationSourceProvider(), EnvironmentVariableSubstitutor(false))

		bootstrap.objectMapper.registerKotlinModule {
			enable(KotlinFeature.SingletonSupport)
			enable(KotlinFeature.StrictNullChecks)
		}

		bootstrap.addBundle(
			GuiceBundle.builder()
				.modules(LlmModule)
				.printExtensionsHelp()
				.searchCommands()
				.enableAutoConfig()
				.build()
		)
	}

	override fun run(configuration: LlmeConfiguration, environment: Environment) {
		environment.jersey().run {
			register(OpenApiResource::class.java)
			register(AcceptHeaderOpenApiResource::class.java)
			property(ServerProperties.OUTBOUND_CONTENT_LENGTH_BUFFER, 0);
		}
	}
}
