package com.jyhun.CommunityConnect;


import com.jyhun.CommunityConnect.domain.member.dto.LoginDTO;
import com.jyhun.CommunityConnect.domain.member.dto.RegisterDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("member",new LoginDTO());
        return "member/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("member",new RegisterDTO());
        return "member/register";
    }

}
