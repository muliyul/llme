package com.muliyul.llme.llm

import com.google.inject.Provides
import com.muliyul.llme.*
import com.muliyul.llme.automation.*
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.model.ollama.OllamaStreamingChatModel
import dev.langchain4j.service.AiServices
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore
import jakarta.inject.Singleton
import ru.vyarus.dropwizard.guice.module.support.*
import java.time.*

object LlmModule : DropwizardAwareModule<LlmeConfiguration>() {
	@Provides
	@Singleton
	fun ollamaStreamingLlm(configuration: LlmeConfiguration): OllamaStreamingChatModel =
		with(configuration.llm.getValue("ollama")) {
			OllamaStreamingChatModel.builder()
				.baseUrl(baseUrl)
				.modelName(modelName)
				.seed(seed)
				.timeout(Duration.ofSeconds(timeoutInSeconds))
				.repeatPenalty(repeatPenalty)
				.numCtx(numCtx)
				.format(format)
				.temperature(temperature)
				.topK(topK)
				.topP(topP)
				.build()
		}

	@Provides
	@Singleton
	fun ollama(
		configuration: LlmeConfiguration,
		streamingChatModel: OllamaStreamingChatModel,
		automationTools: AutomationTools
	): Assistant =
		with(configuration.llm.getValue("ollama")) {
			val model = OllamaChatModel.builder()
				.baseUrl(baseUrl)
				.modelName(modelName)
				.seed(seed)
				.timeout(Duration.ofSeconds(timeoutInSeconds))
				.repeatPenalty(repeatPenalty)
				.numCtx(numCtx)
				.format(format)
				.temperature(temperature)
				.topK(topK)
				.topP(topP)
				.build()

			val memoryProvider = { id: Any ->
				MessageWindowChatMemory.builder()
					.id(id)
					.chatMemoryStore(InMemoryChatMemoryStore())
					.maxMessages(maxMemoryMessages)
					.build();
			}

			AiServices.builder(Assistant::class.java)
//				.chatLanguageModel(model)
				.streamingChatLanguageModel(streamingChatModel)
				.chatMemoryProvider(memoryProvider)
				.tools(automationTools)
				.build()
		}
}
