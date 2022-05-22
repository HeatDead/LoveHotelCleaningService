package com.example.lovehotelcleaningservice.controller;

import com.example.lovehotelcleaningservice.domain.Role;
import com.example.lovehotelcleaningservice.domain.User;
import com.example.lovehotelcleaningservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "userList";
    }

    @GetMapping("/userEdit")
    public String userEditForm(@RequestParam("id") String userId, Model model) {
        model.addAttribute("user", userRepo.findById(Long.parseLong(userId)).get());
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("/userSave")
    public String userSave(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String p_confirm,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam(value = "rl" , required = false) String[] form,
            @RequestParam("userId") User user,
            Model model)
    {
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String role : form) {
            if(roles.contains(role)){
                user.getRoles().add(Role.valueOf(role));
            }
        }

        User userFromDb = userRepo.findByUsername(username);
        if(userFromDb != null)
        {
            if(userFromDb.getId() != user.getId()) {
                model.addAttribute("message", "The login is already taken!");
                model.addAttribute("user", user);
                model.addAttribute("roles", Role.values());
                return "userEdit";
            }
        }

        if(password.compareTo(p_confirm) != 0)
        {
            model.addAttribute("message", "Passwords don't match!");
            model.addAttribute("user", user);
            model.addAttribute("roles", Role.values());
            return "userEdit";
        }else user.setPassword(password);

        userRepo.save(user);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addUser")
    public String addUser(Model model)
    {
        model.addAttribute("roles", Role.values());
        return "addUser";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addUser")
    public String addUser(@RequestParam String p_confirm,
                          User user,
                          @RequestParam(value = "rl" , required = false) String[] form,
                          Model model)
    {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if(userFromDb != null)
        {
            model.addAttribute("message", "User exists!");
            return "addUser";
        }

        if(user.getPassword().compareTo(p_confirm) != 0)
        {
            model.addAttribute("message", "Passwords don't match!");
            //model.addAttribute("user", user);
            //model.addAttribute("roles", Role.values());
            return "addUser";
        }

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        Set<Role> rls = new HashSet<>();

        for (String role : form) {
            if(roles.contains(role)){
                rls.add(Role.valueOf(role));
            }
        }

        //user.setRoles(Collections.singleton(Role.CLEANER));
        user.setRoles(rls);

        user.setActive(true);

        userRepo.save(user);
        return "redirect:/";
    }
}
