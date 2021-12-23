package com.artonhanger.manage.controller;

import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.UserDetailsImpl;
import com.artonhanger.manage.model.dto.ModifyProfileDto;
import com.artonhanger.manage.model.dto.PasswordModifyDto;
import com.artonhanger.manage.respository.MemberRepository;
import com.artonhanger.manage.service.MemberModifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;

@Controller
@RequestMapping("/modify")
@RequiredArgsConstructor
public class AccountManageController {
    private final MemberModifyService memberModifyService;
    private final MemberRepository memberRepository;

    @GetMapping(value = "/pw")
    public String modifyMember(Model model){
        Long memberId = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember().getId();
        Member member = memberRepository.findMemberWithArtistById(memberId);
        model.addAttribute("member",member);
        return "manage/modify/passwordModify";
    }
    @ResponseBody
    @PostMapping(value = "/pw")
    public ResponseEntity passwordModify(PasswordModifyDto passwordModifyDto){
        memberModifyService.modifyPassword(passwordModifyDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/profile")
    public String getModifyProfile(Model model){
        Long memberId = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember().getId();
        Member member = memberRepository.findMemberWithArtistById(memberId);
        model.addAttribute("member",member);
        return "manage/modify/myProfile";
    }
    @PostMapping(value = "/profile")
    public ResponseEntity modifyProfile(ModifyProfileDto modifyProfileDto) throws IOException {
        memberModifyService.modifyProfile(modifyProfileDto);
        return ResponseEntity.ok().build();
    }
}
