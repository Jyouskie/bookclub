package com.bookclub.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping("/logout-success")
    public String logout() {
        return "redirect:/login?logout";
    }
}
