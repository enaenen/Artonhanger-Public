package com.artonhanger.manage.controller;

import com.artonhanger.manage.model.Artist;
import com.artonhanger.manage.model.CollectorContents;
import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.UserDetailsImpl;
import com.artonhanger.manage.model.dto.*;
import com.artonhanger.manage.respository.CollectorContentsRepository;
import com.artonhanger.manage.respository.MemberRepository;
import com.artonhanger.manage.service.ArtistService;
import com.artonhanger.manage.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {
    private final MemberRepository memberRepository;
    private final ArtistService artistService;
    private final ArtworkService artworkService;
    private final CollectorContentsRepository collectorContentsRepository;

    @GetMapping(value = "/artists")
    public String artistInfoLoad(@PageableDefault Pageable pageable, Model model) {
        Long memberId = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember().getId();
        Member member = memberRepository.findMemberWithArtistById(memberId);
        model.addAttribute("member", member);
        Page<Artist> artists = artistService.loadArtistList(pageable);
        model.addAttribute("artistList", artists);
        return "manage/admin/artistInfo";
    }

    @GetMapping(value = "/artists/search")
    public String artistInfoSearch(@RequestParam String keyword, @PageableDefault Pageable pageable, Model model) {
        Page<Artist> artists = artistService.searchArtist(pageable, ArtistSearchCondition
                .builder()
                .name(keyword)
                .nickname(keyword)
                .email(keyword)
                .build());
        model.addAttribute("artistList", artists);
        return "manage/admin/artistInfo";
    }

    @GetMapping(value = "/members")
    public String memberInfoLoad(@PageableDefault Pageable pageable, Model model) {
        Long memberId = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember().getId();
//        Page<Member> allMemberWithArtist = memberRepository.findAllMemberWithArtist(pageable);
        return "";
    }

    @GetMapping(value = "/artworks")
    public String artworkListLoad(@PageableDefault(direction = Sort.Direction.DESC, size = 12) Pageable pageable, Model model) {
        Long memberId = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember().getId();
        Member member = memberRepository.findMemberById(memberId);
        Page<ArtworkListDto> artworkListDtos = artworkService.loadAllArtworks(pageable);
        model.addAttribute("member", member);
        model.addAttribute("artworks", artworkListDtos);
        return "manage/admin/artworkList";
    }

    @GetMapping(value = "/artworks/search")
    public String artworkSearch(@RequestParam("keyword") String keyword,
                                @PageableDefault(direction = Sort.Direction.DESC, size = 12) Pageable pageable,
                                Model model) {
        Page<ArtworkListDto> artworkListDtos = artworkService.searchArtworks(ArtworkSearchCondition.builder()
                .nickname(keyword)
                .title(keyword)
                .build(), pageable);
        model.addAttribute("artworks", artworkListDtos);
        return "manage/admin/artworkList";
    }

    @GetMapping(value = "/artworks/del/{artworkId}")
    public String artworkDelete(@PathVariable("artworkId") Long artworkId, Model model) {
        artworkService.artworkDelete(artworkId);
        return "redirect:/admin/artworks";
    }

    @GetMapping(value = "/artworks/modify/{artworkId}")
    public String artworkModifyByAdmin(@PathVariable("artworkId") Long artworkId, Model model) {
        Long memberId = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember().getId();
        Member member = memberRepository.findMemberWithArtistById(memberId);
        ArtworkModifyViewDto artworkByArtworkId = artworkService.findArtworkByArtworkId(artworkId);
        model.addAttribute("artwork", artworkByArtworkId);
        model.addAttribute("member", member);
        return "manage/admin/artworkModify";
    }

    @GetMapping(value = "/contents")
    public String collectorContentsLoad(@PageableDefault(direction = Sort.Direction.DESC, size = 30) Pageable pageable, Model model) {
        Page<CollectorContents> contents = collectorContentsRepository.findAll(pageable);
        model.addAttribute("contents", contents);
        return "manage/admin/contentList";
    }

    @GetMapping(value = "/contents/{contentsId}")
    public String collectorContentsLoad(@PathVariable("contentsId") Long collectorContentId, Model model) {
        Optional<CollectorContents> collectorContents = collectorContentsRepository.findById(collectorContentId);
        model.addAttribute("contents", collectorContents.orElseThrow());
        return "manage/admin/collectorContentsView";
    }
}
