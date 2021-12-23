package com.artonhanger.manage.controller;

import com.artonhanger.manage.model.CollectorContents;
import com.artonhanger.manage.model.dto.CollectorContentsDto;
import com.artonhanger.manage.respository.CollectorContentsRepository;
import com.artonhanger.manage.service.CollectorContentUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.HashMap;

@RequestMapping(value = "/collector")
@Controller
@RequiredArgsConstructor
public class CollectorContentController {
    private final CollectorContentUploadService uploadService;

    @GetMapping(value = "/upload")
    public String indexPage(){
        return "content/contentUpload";
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public String collectorContentUpload(CollectorContentsDto collectorContentsDto) {
        uploadService.upload(collectorContentsDto);
        return "redirect:/collector/upload";
    }

}
