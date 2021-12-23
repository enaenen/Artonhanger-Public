package com.artonhanger.manage.component.imweb;

import com.artonhanger.manage.component.imweb.dto.ImwebRequest;
import com.artonhanger.manage.enums.ErrorEnum;
import com.artonhanger.manage.exception.AOHException;
import com.artonhanger.manage.component.imweb.dto.ImwebAccessTokenResponse;
import com.artonhanger.manage.component.imweb.dto.ImwebResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.MediaType.TEXT_HTML;

@Component
public class ImwebClient {
    private static final String BASE_URL = "https://api.imweb.me";
    private static final String URL_AUTH = "/v2/auth";

    private final WebClient webClient;

    @Value("${thirdPartyProperties.imweb-api.key}")
    private String apiKey;

    @Value("${thirdPartyProperties.imweb-api.secret}")
    private String apiSecret;

    public ImwebClient() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder().codecs(this::acceptedCodecs).build())
                .build();
    }

    private void acceptedCodecs(ClientCodecConfigurer clientCodecConfigurer) {
        clientCodecConfigurer.customCodecs().register(new Jackson2JsonEncoder(new ObjectMapper(), TEXT_HTML));
        clientCodecConfigurer.customCodecs().register(new Jackson2JsonDecoder(new ObjectMapper(), TEXT_HTML));
    }

    public ImwebResponse get(ImwebRequest request) {
        String accessToken = retrieveAccessToken();
        Mono<ImwebResponse> imwebResponseMono = webClient
                .get()
                .uri(request.getUri())
                .header(org.springframework.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ImwebResponse.class);
        return Optional.ofNullable(imwebResponseMono.block())
                .orElseThrow(() -> new AOHException(ErrorEnum.ETC, "Api Error"));
    }

    public ImwebResponse post(ImwebRequest request) {
        String accessToken = retrieveAccessToken();
        Mono<ImwebResponse> imwebResponseMono = webClient
                .post()
                .uri(request.getUri())
                .header(org.springframework.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", accessToken)
                .bodyValue(request.getBody())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ImwebResponse.class);
        return Optional.ofNullable(imwebResponseMono.block())
                .orElseThrow(() -> new AOHException(ErrorEnum.ETC, "Api Error"));
    }

    public ImwebResponse patch(ImwebRequest request) {
        String accessToken = retrieveAccessToken();
        Mono<ImwebResponse> imwebResponseMono = webClient
                .patch()
                .uri(request.getUri())
                .header(org.springframework.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", accessToken)
                .bodyValue(request.getBody())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ImwebResponse.class);
        return Optional.ofNullable(imwebResponseMono.block())
                .orElseThrow(() -> new AOHException(ErrorEnum.ETC, "Api Error"));
    }

    public ImwebResponse delete(ImwebRequest request) {
        String accessToken = retrieveAccessToken();
        Mono<ImwebResponse> imwebResponseMono = webClient
                .delete()
                .uri(request.getUri())
                .header(org.springframework.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ImwebResponse.class);
        return Optional.ofNullable(imwebResponseMono.block())
                .orElseThrow(() -> new AOHException(ErrorEnum.ETC, "Api Error"));
    }

    private String retrieveAccessToken(){
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("key", apiKey);
        map.add("secret", apiSecret);

        Mono<ImwebAccessTokenResponse> responseMono = webClient
                .post()
                .uri(URL_AUTH)
                .header(org.springframework.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromMultipartData(map))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ImwebAccessTokenResponse.class);
        return Optional.ofNullable(responseMono.block())
                .orElseThrow(() -> new AOHException(ErrorEnum.ETC, "Api Error"))
                .getAccessToken();
    }
}
