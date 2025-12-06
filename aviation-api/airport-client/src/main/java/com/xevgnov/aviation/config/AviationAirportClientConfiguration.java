package com.xevgnov.aviation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xevgnov.aviation.exception.AviationAirportErrorDecoder;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;


@Configuration
@EnableFeignClients(basePackages = "com.xevgnov.aviation")
@ConditionalOnProperty(prefix = "airport.client", name = "enabled", matchIfMissing = true)
public class AviationAirportClientConfiguration {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE);
        mapper.setTimeZone(TimeZone.getDefault());
        return mapper;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new AviationAirportErrorDecoder();
    }

}
