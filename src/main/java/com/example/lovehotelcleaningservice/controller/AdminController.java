package com.example.lovehotelcleaningservice.controller;

import com.example.lovehotelcleaningservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @Autowired
    private UserRepo userRepo;

    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('MODERATOR')")
    @GetMapping("/adminPanel")
    public String adminPanel(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userRepo.findByUsername(auth.getName()));
        return "control_panel/adminPanel";
    }
}
