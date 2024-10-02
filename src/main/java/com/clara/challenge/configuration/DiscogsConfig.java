package com.clara.challenge.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class DiscogsConfig {
    @Value("${discogs.consumer.key}")
    private String consumerKey;

    @Value("${discogs.consumer.secret}")
    private String consumerSecret;
}
