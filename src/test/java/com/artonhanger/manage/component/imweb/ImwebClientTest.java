package com.artonhanger.manage.component.imweb;

import com.artonhanger.manage.component.imweb.dto.ImwebRequest;
import com.artonhanger.manage.component.imweb.dto.ImwebResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "prod")
@ActiveProfiles("prod")
@SpringBootTest
class ImwebClientTest {
    @Autowired
    private ImwebClient imwebClient;

    @Test
    public void get_테스트() {
        ImwebResponse response = imwebClient.get(ImwebRequest.builder()
                .uri("/v2/shop/products")
                .build());
        assertEquals("SUCCESS", response.getMsg());
    }
}