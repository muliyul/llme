package com.muliyul.llme.automation

import dev.langchain4j.agent.tool.Tool
import jakarta.inject.Singleton

@Singleton
class AutomationTools {

	@Tool("Sums 2 given numbers")
	fun sum(a: Double, b: Double): Double {
		return a + b
	}

}
