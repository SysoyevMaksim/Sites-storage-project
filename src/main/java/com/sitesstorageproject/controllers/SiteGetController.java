package com.sitesstorageproject.controllers;


import com.sitesstorageproject.repos.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
public class SiteGetController {

    private final UserRepository userRepo;

    public SiteGetController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    private String decodeURL(String url) {
        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }

    @GetMapping("/site/id/{id}/**")
    @ResponseBody
    public byte[] siteById(@PathVariable String id, HttpServletRequest request) throws IOException {
        String url = decodeURL(request.getRequestURI());
        File file = new File("src/main/resources/" + id + "/" + url.substring(request.getRequestURI().indexOf("site/" + id) + id.length() + 6));
        try (InputStream input = new FileInputStream(file)) {
            return input.readAllBytes();
        } catch (FileNotFoundException e) {
            return new byte[0];
        }
    }

    @GetMapping("/site/{username}")
    public String mainPageByUsername(@PathVariable String username) {
        return "redirect:/site/" + username + "/index.html";
    }

    @GetMapping("/site/{username}/**")
    @ResponseBody
    public byte[] siteByUsername(@PathVariable String username, HttpServletRequest request) throws IOException {
        String url = decodeURL(request.getRequestURI());
        File file = new File("src/main/resources/" + userRepo.findByLogin(username).getId().toString() + "/" + url.substring(request.getRequestURI().indexOf("site/" + username) + username.length() + 6));
        try (InputStream input = new FileInputStream(file)) {
            return input.readAllBytes();
        } catch (FileNotFoundException e) {
            return new byte[0];
        }
    }
}
