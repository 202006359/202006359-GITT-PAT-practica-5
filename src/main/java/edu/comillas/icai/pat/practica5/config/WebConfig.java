package edu.comillas.icai.pat.practica5.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

	@Bean
	public RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder();
	}
	@Bean
	public RestTemplate restTemplate(final RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}
}