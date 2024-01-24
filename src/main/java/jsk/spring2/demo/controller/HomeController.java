package jsk.spring2.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal UserDetails user, Model model) {
        if (user != null) {
            model.addAttribute("userId", user.getUsername());
            model.addAttribute("userPw", user.getPassword());
            model.addAttribute("userAuth", user.getAuthorities().toString());
        }

        return "/mypage";
    }

    @GetMapping("/status")
    @ResponseBody
    public String status(HttpSession session) {
        return session.getId() + ";";
    }
}