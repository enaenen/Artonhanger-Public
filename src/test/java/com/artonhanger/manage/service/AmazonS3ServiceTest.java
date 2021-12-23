package com.artonhanger.manage.service;

import com.artonhanger.manage.service.dto.MultipartFileUploadRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "prod")
@ActiveProfiles("prod")
@SpringBootTest
class AmazonS3ServiceTest {
    @Autowired
    private AmazonS3Service amazonS3Service;

    private String testDirName = "testdir";
    private String testFileName = "hello.txt";
    private MultipartFile multipartFile = new MockMultipartFile(
            "image",
            "hello.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, World!".getBytes()
    );

    private String bucket = "artonhanger-test";
    private MultipartFileUploadRequest request;

    @BeforeEach
    public void setUp() {
        request = MultipartFileUploadRequest.builderWithFile(multipartFile)
                .dirName(testDirName)
                .fileName(testFileName)
                .build();
        ReflectionTestUtils.setField(amazonS3Service, "bucket", bucket);
    }

    @Test
    void 멀티파트_파일_업로드_테스트() {
        System.out.println(amazonS3Service.upload(request).getDownloadableUrl());
    }

    @AfterEach
    public void tearDown() {
        amazonS3Service.remove(testDirName + "/" + testFileName);
    }
}