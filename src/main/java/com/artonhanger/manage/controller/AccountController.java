package com.artonhanger.manage.controller;


import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.UserDetailsImpl;
import com.artonhanger.manage.model.dto.RegisterUserDto;
import com.artonhanger.manage.service.MemberModifyService;
import com.artonhanger.manage.service.MemberRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final MemberRegisterService memberRegisterService;
    private final MemberModifyService memberModifyService;

    @GetMapping(value = "/login")
    public String loginByGet(Model model, HttpServletRequest req) {
        model.addAttribute("message", req.getServletContext());
        return "manage/login";
    }

    @GetMapping(value = "/logintest")
    public String loginTest(Model model, HttpServletRequest req) {
        model.addAttribute("message", req.getServletContext());
        return "manage/login";
    }

    @PostMapping(value = "/slogin")
    public String socialLoginByPost(Model model, HttpServletRequest req) {
        model.addAttribute("message", req.getServletContext());
        return "manage/login";
    }

    @GetMapping(value = "")
    public String index(Model model) {
        Member member = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember();
        System.out.println(member.getEmail());
        model.addAttribute("member", member);
        return "manage/index";
    }

    @GetMapping(value = "/icons")
    public String icons(Model model) {
        System.out.println("??");
        Member member = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getMember();
        System.out.println(member.getEmail());
        return "manage/icons";
    }

    @GetMapping(value = "/forgot")
    public String forgotPassword() {
        return "manage/forgot";
    }

    @PostMapping(value = "/forgot")
    public String resetPassword(String email, Model model) {
        System.out.println(email);
        memberModifyService.resetPassword(email);
        model.addAttribute("email", email);
        return "manage/forgotComplete";
    }

    @GetMapping(value = "/register")
    public String userRegister(Model model) {
        return "manage/register/register";
    }

    @PostMapping(value = "/register")
    public ResponseEntity userRegisterConfirm(RegisterUserDto registerUserDto) throws IOException {
        memberRegisterService.register(registerUserDto);
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/logout")
    public String logoutPageGet(HttpServletRequest request, HttpServletResponse response) {
        return logoutPage(request, response);
    }

    @PostMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

}
