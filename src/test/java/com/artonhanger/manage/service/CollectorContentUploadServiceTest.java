package com.artonhanger.manage.service;

import com.artonhanger.manage.model.CollectorContents;
import com.artonhanger.manage.model.dto.CollectorContentsDto;
import com.artonhanger.manage.respository.CollectorContentsRepository;
import com.artonhanger.manage.service.dto.MultipartFileUploadRequest;
import com.artonhanger.manage.service.dto.UploadResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CollectorContentUploadServiceTest {
    @InjectMocks
    private CollectorContentUploadService collectorContentUploadService;

    @Mock
    private ObjectStorageService s3Service;

    @Mock
    private CollectorContentsRepository collectorContentsRepository;

    private final CollectorContentsDto collectorContentsDto = new CollectorContentsDto();
    private final ArgumentCaptor<CollectorContents> collectorContentsArgumentCaptor =
            ArgumentCaptor.forClass(CollectorContents.class);

    @BeforeEach
    public void setUp() {
        collectorContentsDto.setCollectorName("테스트수집가이름");
        collectorContentsDto.setArtworkTitle("테스트작품제목");
        collectorContentsDto.setContentTitle("테스트내용제목");
        collectorContentsDto.setIntroduce("테스트소개");
        collectorContentsDto.setTaste("테스트취향");
        collectorContentsDto.setLocation("테스트위치");
        collectorContentsDto.setChoiceStandard("테스트기본선택");
        collectorContentsDto.setChoiceReason("테스트선택이유");
        collectorContentsDto.setCharmingPoint("테스트매력포인트");
        collectorContentsDto.setLocationAfter("테스트위치뒤");
        collectorContentsDto.setTip("테스트팁");
        collectorContentsDto.setRecommend("테스트추천");
        collectorContentsDto.setEtc("테스트기타");
        collectorContentsDto.setTasteUrl(new MockMultipartFile(
                "image",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()));

        given(s3Service.upload(any(MultipartFileUploadRequest.class)))
                .willReturn(UploadResult.builder().downloadableUrl("test-url").build());
        given(collectorContentsRepository.save(collectorContentsArgumentCaptor.capture()))
                .willReturn(CollectorContents.builder()
                        .collectorName("테스트수집가이름")
                        .artworkTitle("테스트작품제목")
                        .contentTitle("테스트내용제목")
                        .introduce(CollectorContents.Introduce.builder().content("테스트소개").build())
                        .taste(CollectorContents.Taste.builder().content("테스트취향").url("test-url").build())
                        .location(CollectorContents.Location.builder().content("테스트위치").build())
                        .choiceStandard(CollectorContents.ChoiceStandard.builder().content("테스트기본선택").build())
                        .choiceReason(CollectorContents.ChoiceReason.builder().content("테스트선택이유").build())
                        .charmingPoint(CollectorContents.CharmingPoint.builder().content("테스트매력포인트").build())
                        .locationAfter(CollectorContents.LocationAfter.builder().content("테스트위치뒤").build())
                        .tip("테스트팁")
                        .recommend("테스트추천")
                        .etc("테스트기타")
                        .build());
    }

    @Test
    public void test() {
        collectorContentUploadService.upload(collectorContentsDto);

        CollectorContents capturedContent = collectorContentsArgumentCaptor.getValue();
        assertEquals("테스트수집가이름", capturedContent.getCollectorName());
        assertEquals("테스트작품제목", capturedContent.getArtworkTitle());
        assertEquals("테스트내용제목", capturedContent.getContentTitle());
        assertEquals("테스트소개", capturedContent.getIntroduce().getContent());
        assertEquals("테스트취향", capturedContent.getTaste().getContent());
        assertEquals("테스트위치", capturedContent.getLocation().getContent());
        assertEquals("테스트기본선택", capturedContent.getChoiceStandard().getContent());
        assertEquals("테스트선택이유", capturedContent.getChoiceReason().getContent());
        assertEquals("테스트매력포인트", capturedContent.getCharmingPoint().getContent());
        assertEquals("테스트위치뒤", capturedContent.getLocationAfter().getContent());
        assertEquals("테스트팁", capturedContent.getTip());
        assertEquals("테스트추천", capturedContent.getRecommend());
        assertEquals("테스트기타", capturedContent.getEtc());
        assertEquals("test-url", capturedContent.getTaste().getUrl());
    }
}