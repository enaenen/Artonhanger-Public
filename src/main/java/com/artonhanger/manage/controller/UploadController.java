package com.artonhanger.manage.controller;

import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.UserDetailsImpl;
import com.artonhanger.manage.model.dto.ArtworkUploadDto;
import com.artonhanger.manage.service.ArtworkUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {
    private final ArtworkUploadService artworkUploadService;

    @GetMapping
    public String uploadPage(Model model){
        Member member = ((UserDetailsImpl)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember();
        model.addAttribute("member",member);
        return "manage/upload/artworkUpload";
    }

    @PostMapping(value = "/artwork", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public String artworkUpload(ArtworkUploadDto artworkUploadDto) {
        Member member = ((UserDetailsImpl)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember();
        artworkUploadDto.setMember(member);
        artworkUploadService.artworkUpload(artworkUploadDto);
        return "redirect:/upload";
    }

    @GetMapping(value = "/edition")
    public String artworkEditionUploadPage(Model model){
        Member member = ((UserDetailsImpl)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember();
        model.addAttribute("member",member);
        return "manage/upload/artworkEditionUpload";
    }
}
