package com.muliyul.llme

import dev.langchain4j.data.message.*
import dev.langchain4j.model.*
import dev.langchain4j.model.ollama.*
import dev.langchain4j.model.output.Response
import io.dropwizard.auth.*
import jakarta.inject.*
import jakarta.ws.rs.*
import jakarta.ws.rs.core.*
import org.slf4j.*
import java.io.*
import java.security.*
import java.util.concurrent.*

@Path("/stream")
@Consumes(MediaType.TEXT_PLAIN)
@Produces("text/event-stream")
class StreamingLlmResource @Inject constructor(
	private val llm: OllamaStreamingChatModel
) {
	@POST
	@Path("/command")
	fun handle(command: String, @Auth user: Principal): StreamingOutput = StreamingOutput {
		StreamingLlmHandler(it).use {
			llm.generate(command, it)
		}
	}

	@POST
	@Path("/teach")
	fun teach(toolName: String, @Auth user: Principal): StreamingOutput = StreamingOutput {
		StreamingLlmHandler(it).use {
			llm.generate(toolName, it)
		}
	}
}

private class StreamingLlmHandler(
	private val out: OutputStream
) : StreamingResponseHandler<AiMessage>, AutoCloseable {
	private val logger = LoggerFactory.getLogger(javaClass)

	private val lock = CountDownLatch(1)

	override fun onNext(token: String) {
		logger.info(token)
		out.write(token.toByteArray())
	}

	override fun onError(error: Throwable) {
		logger.error(error.message, error)
	}

	override fun onComplete(response: Response<AiMessage>) {
		lock.countDown()
	}

	override fun close() {
		lock.await()
	}
}
