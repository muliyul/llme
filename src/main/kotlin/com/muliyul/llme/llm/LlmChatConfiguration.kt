package com.muliyul.llme.llm

data class LlmChatConfiguration(
	val baseUrl: String,
	val timeoutInSeconds: Long,
	val modelName: String,
	val apiKey: String?,
	val maxMemoryMessages: Int = 500,
	val seed: Int?,
	val repeatPenalty: Double?,
	val numCtx: Int?,
	val format: String?,
	val temperature: Double?,
	val topK: Int?,
	val topP: Double?
)
