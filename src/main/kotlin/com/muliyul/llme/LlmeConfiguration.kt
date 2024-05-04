package com.muliyul.llme

import com.muliyul.llme.llm.*
import io.dropwizard.core.Configuration

data class LlmeConfiguration(
	val llm: Map<String, LlmChatConfiguration>
): Configuration()
