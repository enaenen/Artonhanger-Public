package com.artonhanger.manage.controller;

import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.UserDetailsImpl;
import com.artonhanger.manage.model.dto.ArtworkListDto;
import com.artonhanger.manage.model.dto.ArtworkModifyDto;
import com.artonhanger.manage.model.dto.ArtworkModifyViewDto;
import com.artonhanger.manage.model.dto.PageRequestDto;
import com.artonhanger.manage.respository.MemberRepository;
import com.artonhanger.manage.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/artwork")
public class ArtworkManageController {
    private final ArtworkService artworkService;
    private final MemberRepository memberRepository;

    @GetMapping(value = "/list")
    public String getArtworkList(Model model){
        Long memberId = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember().getId();
        Member member = memberRepository.findMemberWithArtistById(memberId);
        List<ArtworkListDto> artworkList = artworkService.findArtworkListByMemberIdAndImwebId(memberId, new PageRequestDto().getNotSortedPageable()).getContent();
        model.addAttribute("artworks",artworkList);
        model.addAttribute("member",member);
        return "manage/modify/artwork/artworkList";
    }
    @GetMapping(value = "/more")
    public ResponseEntity<Page<ArtworkListDto>> loadArtworks(Model model, PageRequestDto pageRequestDto){
        Long memberId = ((UserDetailsImpl)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember().getId();
        return ResponseEntity.ok(artworkService.findArtworkListByMemberId(memberId,pageRequestDto.getPageable()));
    }
    @GetMapping(value = "/{artworkId}")
    public String getModifyArtwork(@PathVariable("artworkId") Long artworkId, Model model){
        Member member = ((UserDetailsImpl)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember();
        ArtworkModifyViewDto artworkModifyDto = artworkService.findArtworkByArtworkId(artworkId);
        model.addAttribute("artwork",artworkModifyDto);
        model.addAttribute("member",member);
        return "manage/modify/artwork/artworkModify";
    }
    @PostMapping(value = "/{artworkId}")
    public ResponseEntity modifyArtwork(@PathVariable("artworkId")Long artworkId, ArtworkModifyDto artworkModifyDto){
        artworkService.artworkModify(artworkId, artworkModifyDto);
        return ResponseEntity.ok().build();
    }

}
