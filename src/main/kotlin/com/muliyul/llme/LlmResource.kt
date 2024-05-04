package com.muliyul.llme

import com.muliyul.llme.llm.*
import dev.langchain4j.model.ollama.*
import io.dropwizard.auth.Auth
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import java.security.Principal

@Path("/")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
class LlmResource @Inject constructor(
	private val assistant: Assistant
) {
	@POST
	@Path("/command")
	fun handle(command: String, @Auth user: Principal): String =
		assistant.chat(user.name, command)

	@POST
	@Path("/teach")
	fun teach(toolName: String, @Auth user: Principal): String =
		assistant.chat(user.name, toolName)
}
