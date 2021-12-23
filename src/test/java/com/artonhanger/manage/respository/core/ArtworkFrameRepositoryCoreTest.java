package com.artonhanger.manage.respository.core;

import com.artonhanger.manage.model.Artist;
import com.artonhanger.manage.model.ArtworkFrame;
import com.artonhanger.manage.respository.ArtworkFrameRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DatabaseSetup({
        "classpath:dbunit/dataset/artwork_frame.xml"
})
class ArtworkFrameRepositoryCoreTest extends RepositoryCoreTest {

    @Autowired
    ArtworkFrameRepository artworkFrameRepository;

    @Test
    public void 전체_레코드_조회_테스트() {
        List<ArtworkFrame> artworkFrames = artworkFrameRepository.findAll();

        assertEquals(10, artworkFrames.size());
        assertEquals(1L, artworkFrames.get(0).getId());
        assertEquals(10L, artworkFrames.get(9).getId());
    }

    @Test
    public void 특정_레코드_조회_테스트() {
        ArtworkFrame artworkFrame = artworkFrameRepository.findById(1L).orElseThrow();

        assertEquals(1L, artworkFrame.getId());
        assertEquals("스테인리스", artworkFrame.getMaterial());
        assertEquals(20L, artworkFrame.getSize().getHeight());
        assertEquals(21L, artworkFrame.getSize().getWidth());
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/ArtworkFrameRepositoryCoreTest/레코드_추가_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_추가_테스트() {
        ArtworkFrame artworkFrame = ArtworkFrame.builder()
                .material("테스트재질")
                .height(12L)
                .width(13L)
                .build();

        artworkFrameRepository.saveAndFlush(artworkFrame);
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/ArtworkFrameRepositoryCoreTest/레코드_수정_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_수정_테스트() {
        ArtworkFrame artworkFrame = artworkFrameRepository.findById(1L).orElseThrow();
        artworkFrame.changeMaterial("재질변경");
        artworkFrameRepository.flush();
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/ArtworkFrameRepositoryCoreTest/레코드_삭제_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_삭제_테스트() {
        ArtworkFrame artworkFrame = artworkFrameRepository.findById(1L).orElseThrow();
        artworkFrameRepository.delete(artworkFrame);
        artworkFrameRepository.flush();
    }
}