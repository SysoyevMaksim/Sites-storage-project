package com.sitesstorageproject.controllers;

import com.sitesstorageproject.repos.UserRepository;
import com.sitesstorageproject.security.UserCredits;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InformationPagesController {
    private final UserRepository userRepo;

    public InformationPagesController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    String getHomePage(ModelMap map) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        map.addAttribute("authed", !auth.getPrincipal().equals("anonymousUser"));
        map.addAttribute("user", !auth.getPrincipal().equals("anonymousUser") ? userRepo.findByEmail(((UserCredits) auth.getPrincipal()).getUsername()).getLogin() : "anonymous");
        return "home";
    }

    @GetMapping("/about")
    String getAboutPage(ModelMap map) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        map.addAttribute("authed", !auth.getPrincipal().equals("anonymousUser"));
        map.addAttribute("user", !auth.getPrincipal().equals("anonymousUser") ? userRepo.findByEmail(((UserCredits) auth.getPrincipal()).getUsername()).getLogin() : "anonymous");
        return "about";
    }
}
