package com.clara.challenge.exception.feign;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscogsClientConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new DiscogsErrorDecoder();
    }
}
