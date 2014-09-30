package com.attemper.emr.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HateosRestClient {
	 
	public RestTemplate restTemplate() {
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(halConverter());
 
		RestTemplate restTemplate = new RestTemplate();
 
		restTemplate.setMessageConverters(converters);
 
		return restTemplate;
	}
 
	private MappingJackson2HttpMessageConverter halConverter() { 
		ObjectMapper halObjectMapper = new ObjectMapper();
		halObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		halObjectMapper.registerModule(new Jackson2HalModule());
 
		MappingJackson2HttpMessageConverter halConverter = new MappingJackson2HttpMessageConverter();
		halConverter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
		halConverter.setObjectMapper(halObjectMapper);
		return halConverter;
	}
}