package com.seikim.spring.readonly.common.strategy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenerateIdentifierStrategyConfig {

	private final Map<Class<?>, GenerateIdentifierStrategy<?>> generateIdentifierStrategies;

	@Autowired
	public GenerateIdentifierStrategyConfig(List<GenerateIdentifierStrategy<?>> generateIdentifierStrategies) {
		this.generateIdentifierStrategies = generateIdentifierStrategies.stream()
				.collect(Collectors.toMap(GenerateIdentifierStrategy::getIdType, Function.identity()));
	}

	@Bean
	public Map<Class<?>, GenerateIdentifierStrategy<?>> generateIdentifierStrategies() {
		return generateIdentifierStrategies;
	}
}
