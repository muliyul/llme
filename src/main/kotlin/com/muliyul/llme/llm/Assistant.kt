package com.muliyul.llme.llm

import dev.langchain4j.service.*

interface Assistant {
	fun chat(@MemoryId memoryId: Any, @UserMessage prompt: String): String
}
