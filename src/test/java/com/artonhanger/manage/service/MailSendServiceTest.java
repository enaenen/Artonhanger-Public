package com.artonhanger.manage.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "prod")
@ActiveProfiles("prod")
@SpringBootTest
class MailSendServiceTest {
    private static final String[] TEST_EMAIL = { "hygoogi@naver.com" };
    private static final String TEST_TITLE = "이메일 발송 테스트";
    private static final String TEST_CONTENT = "prod 이메일 발송 테스트 내용";

    @Autowired
    MailSendService mailSendService;

    @Test
    public void 메일_전송_테스트() {
        for (String email : TEST_EMAIL) {
            mailSendService.mailSender(email, TEST_TITLE, TEST_CONTENT);
        }
    }
}