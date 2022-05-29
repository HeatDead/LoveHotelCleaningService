package com.example.lovehotelcleaningservice.controller;

import com.example.lovehotelcleaningservice.domain.*;
import com.example.lovehotelcleaningservice.repos.CleanupRepo;
import com.example.lovehotelcleaningservice.repos.RoomRepo;
import com.example.lovehotelcleaningservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ProfileController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CleanupRepo cleanupRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/userProfile")
    public String userProfile(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByUsername(auth.getName());
        List<Cleanup> cleanups = cleanupRepo.findAllByUserid(user.getId());

        List<CleanupDisplay> cleanupDisplays = new ArrayList<>();
        for (Cleanup c : cleanups)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String pend_date = c.getPend_date().format(formatter);
            String cleanup_date = c.getClean_date().format(formatter);
            Room room = roomRepo.findById(c.getRoom_id()).get();
            int room_number = room.getNumber();
            String room_name = room.getName();

            CleanupDisplay cud = new CleanupDisplay(pend_date, cleanup_date, room_number, room_name);
            cleanupDisplays.add(cud);
        }

        Rank userRank = Rank.BEGINNER;
        int cus = cleanupDisplays.size();

        if(cus > 320)
            userRank = Rank.LEGENDARY;
        else if(cus > 160)
            userRank = Rank.GREATEST;
        else if(cus > 80)
            userRank = Rank.GURU;
        else if(cus > 40)
            userRank = Rank.PRO;
        else if(cus > 20)
            userRank = Rank.MASTER;
        else if(cus > 10)
            userRank = Rank.INTERMEDIATE;
        else userRank = Rank.BEGINNER;

        user.setRank(userRank);
        userRepo.save(user);

        model.addAttribute("user", user);
        model.addAttribute("cleanups", cleanupDisplays);
        return "profile/userProfile";
    }

    @GetMapping("/profileEdit")
    public String userEditForm(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByUsername(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "profile/userEdit";
    }

    @PostMapping("/profileSave")
    public String userSave(
            @RequestParam String password,
            @RequestParam String p_confirm,
            @RequestParam String name,
            @RequestParam String surname,
            Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByUsername(auth.getName());
        user.setName(name);
        user.setSurname(surname);

        if(password.compareTo(p_confirm) != 0)
        {
            model.addAttribute("message", "Passwords don't match!");
            model.addAttribute("user", user);
            return "profile/userEdit";
        }else user.setPassword(passwordEncoder.encode(password));

        userRepo.save(user);
        return "redirect:/userProfile";
    }
}
