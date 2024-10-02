package com.clara.challenge.exception.feign;

import com.clara.challenge.exception.IntegrationException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DiscogsErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus statusCode = HttpStatus.valueOf(response.status());

        if (statusCode.is5xxServerError()) {
            return new IntegrationException("Error 500 received in Discogs API");
        }

        return null;
    }
}
