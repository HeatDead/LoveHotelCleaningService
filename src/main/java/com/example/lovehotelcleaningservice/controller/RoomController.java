package com.example.lovehotelcleaningservice.controller;

import com.example.lovehotelcleaningservice.domain.Role;
import com.example.lovehotelcleaningservice.domain.Room;
import com.example.lovehotelcleaningservice.domain.RoomType;
import com.example.lovehotelcleaningservice.domain.User;
import com.example.lovehotelcleaningservice.repos.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RoomController {
    @Autowired
    RoomRepo roomRepo;

    @GetMapping("/")
    public String userList(Model model) {
        model.addAttribute("rooms", roomRepo.findAll());
        return "roomsList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addRoom")
    public String addRoom()
    {
        return "addRoom";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addRoom")
    public String addRoom(Room room, Map<String, Object> model)
    {
        Room roomFromDb = roomRepo.findByNumber(room.getNumber());
        if(roomFromDb != null)
        {
            model.put("message", "Room already exists!");
            return "addRoom";
        }
        room.setType(RoomType.STANDART);
        roomRepo.save(room);
        return "redirect:/";
    }
}
