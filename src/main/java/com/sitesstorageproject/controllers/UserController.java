package com.sitesstorageproject.controllers;

import com.sitesstorageproject.entities.Authority;
import com.sitesstorageproject.entities.User;
import com.sitesstorageproject.repos.AuthorityRepository;
import com.sitesstorageproject.repos.UserRepository;
import com.sitesstorageproject.security.UserCredits;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserRepository userRepo;

    private final AuthorityRepository authRepo;

    private final PasswordEncoder encoder;

    public UserController(UserRepository userRepo, AuthorityRepository authRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
        this.encoder = encoder;
    }


    @GetMapping("/register")
    String registerPage(ModelMap map) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/";
        }
        map.addAttribute("logins_taken", userRepo.getLogins());
        map.addAttribute("emails_taken", userRepo.getEmails());
        return "registerForm";
    }

    @GetMapping("/login")
    String loginPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/";
        }
        return "loginForm";
    }

    @PostMapping("/register")
    String register(@RequestParam("name") String name, @RequestParam("surname") String surname,
                    @RequestParam("email") String email, @RequestParam("login") String login,
                    @RequestParam("password") String password) {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(encoder.encode(password));
        userRepo.save(user);
        Authority self = new Authority();
        self.setName("see_site_of_" + user.getId().toString());
        authRepo.save(self);
        user.getAuthorities().add(self);
        userRepo.save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    String profilePage(ModelMap map) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        map.addAttribute("authed", !auth.getPrincipal().equals("anonymousUser"));
        map.addAttribute("user", !auth.getPrincipal().equals("anonymousUser") ? userRepo.findByEmail(((UserCredits) auth.getPrincipal()).getUsername()).getLogin() : "anonymous");
        return "profile";
    }

    @GetMapping("/change_username")
    String changeUsernamePage(ModelMap map) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        map.addAttribute("authed", !auth.getPrincipal().equals("anonymousUser"));
        map.addAttribute("user", !auth.getPrincipal().equals("anonymousUser") ? userRepo.findByEmail(((UserCredits) auth.getPrincipal()).getUsername()).getLogin() : "anonymous");
        map.addAttribute("logins_taken", userRepo.getLogins());
        return "change_username";
    }

    @PostMapping("/change_username")
    String changeUsername(@RequestParam("login") String login) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(((UserCredits) auth.getPrincipal()).getUsername());
        user.setLogin(login);
        userRepo.save(user);
        return "redirect:/";
    }

    @GetMapping("/change_password")
    String changePasswordPage(ModelMap map) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        map.addAttribute("authed", !auth.getPrincipal().equals("anonymousUser"));
        map.addAttribute("user", !auth.getPrincipal().equals("anonymousUser") ? userRepo.findByEmail(((UserCredits) auth.getPrincipal()).getUsername()).getLogin() : "anonymous");
        return "change_password";
    }

    @PostMapping("/change_password")
    String changePassword(@RequestParam("password") String password, @RequestParam("new_password") String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(((UserCredits) auth.getPrincipal()).getUsername());
        if (encoder.matches(password, user.getPassword())) {
            user.setPassword(encoder.encode(newPassword));
            userRepo.save(user);
            return "redirect:/";
        } else {
            return "redirect:/change_password?error";
        }
    }
}
