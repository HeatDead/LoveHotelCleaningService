package com.example.lovehotelcleaningservice.controller;

import com.example.lovehotelcleaningservice.domain.Role;
import com.example.lovehotelcleaningservice.domain.Room;
import com.example.lovehotelcleaningservice.domain.RoomType;
import com.example.lovehotelcleaningservice.domain.User;
import com.example.lovehotelcleaningservice.repos.RoomRepo;
import com.example.lovehotelcleaningservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RoomController {
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String roomList(Model model) {
        List<Room> rooms = roomsSort(roomRepo.findAll());
        model.addAttribute("rooms", rooms);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userRepo.findByUsername(auth.getName()));
        return "roomsList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/rooms")
    public String adminRoomList(Model model) {
        List<Room> rooms = roomsSort(roomRepo.findAll());
        model.addAttribute("rooms", rooms);
        return "control_panel/roomsList";
    }

    public static List<Room> roomsSort(List<Room> rooms)
    {
        int c = 1;
        while (c > 0)
        {
            c = 0;
            for(int i = 0; i < rooms.size() - 1; i++)
            {
                if(rooms.get(i).getNumber() > rooms.get(i + 1).getNumber())
                {
                    Room r = rooms.get(i);
                    rooms.set(i,  rooms.get(i + 1));
                    rooms.set(i + 1,  r);
                    c++;
                }
            }
        }
        return rooms;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addRoom")
    public String addRoom(Model model)
    {
        model.addAttribute("types", RoomType.values());
        model.addAttribute("users", userRepo.findAllByRoles(Role.CLEANER));
        return "control_panel/addRoom";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addRoom")
    public String addRoom(Room room,
                          @RequestParam String ty,
                          @RequestParam String rs,
                          Model model)
    {
        Room roomFromDb = roomRepo.findByNumber(room.getNumber());
        if(roomFromDb != null)
        {
            model.addAttribute("message", "Room already exists!");
            model.addAttribute("types", RoomType.values());
            return "control_panel/addRoom";
        }
        if(room.getName().isEmpty())
        {
            model.addAttribute("message", "Enter a name!");
            model.addAttribute("types", RoomType.values());
            return "control_panel/addRoom";
        }
        room.setClean_pend(false);
        if(!rs.isEmpty())
            room.setResponsible(userRepo.findUserById(Long.parseLong(rs)));
        room.setType(RoomType.valueOf(ty));
        roomRepo.save(room);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/cleanPend")
    public String cleanPend(@RequestParam("id") String roomId) {
        Room room = roomRepo.findById(Long.valueOf(roomId)).get();
        room.setClean_pend(true);
        roomRepo.save(room);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('CLEANER')")
    @GetMapping("/cleaned")
    public String cleaned(@RequestParam("id") String roomId) {
        Room room = roomRepo.findById(Long.valueOf(roomId)).get();
        room.setClean_pend(false);
        roomRepo.save(room);
        return "redirect:/";
    }
}
