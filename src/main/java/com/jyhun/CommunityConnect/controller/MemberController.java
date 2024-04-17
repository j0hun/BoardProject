package com.jyhun.CommunityConnect.controller;

import com.jyhun.CommunityConnect.dto.MemberFormDTO;
import com.jyhun.CommunityConnect.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signupView(Model model) {
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "member/signup";
        }

        try {
            memberService.addMember(memberFormDTO);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/signup";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginView(){
        return "member/login";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("errorMessage","아이디 또는 비밀번호를 확인해주세요.");
        return "member/login";
    }

}