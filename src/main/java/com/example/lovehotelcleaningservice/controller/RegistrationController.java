package com.example.lovehotelcleaningservice.controller;

import com.example.lovehotelcleaningservice.domain.Role;
import com.example.lovehotelcleaningservice.domain.User;
import com.example.lovehotelcleaningservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/addUser")
    public String addUser()
    {
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(User user, Map<String, Object> model)
    {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if(userFromDb != null)
        {
            model.put("message", "User exists!");
            return "addUser";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.CLEANER));
        userRepo.save(user);
        return "redirect:/";
    }
}
