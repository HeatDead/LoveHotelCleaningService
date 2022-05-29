package com.example.lovehotelcleaningservice.controller;

import com.example.lovehotelcleaningservice.domain.*;
import com.example.lovehotelcleaningservice.repos.CleanupRepo;
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

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class RoomController {
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CleanupRepo cleanupRepo;

    @GetMapping("/")
    public String roomList(Model model) {
        List<Room> rooms = roomsSort(roomRepo.findAll());
        model.addAttribute("rooms", rooms);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userRepo.findByUsername(auth.getName()));
        return "roomsList";
    }

    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('MODERATOR')")
    @GetMapping("/rooms")
    public String adminRoomList(Model model) {
        List<Room> rooms = roomsSort(roomRepo.findAll());
        model.addAttribute("rooms", rooms);
        return "control_panel/roomsList";
    }

    public static List<Room> roomsSort(List<Room> rooms) {
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

    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('MODERATOR')")
    @GetMapping("/addRoom")
    public String addRoom(Model model) {
        model.addAttribute("types", RoomType.values());
        return "control_panel/addRoom";
    }

    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('MODERATOR')")
    @PostMapping("/addRoom")
    public String addRoom(Room room,
                          @RequestParam String ty,
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
        room.setType(RoomType.valueOf(ty));
        roomRepo.save(room);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('ADMIN')  OR hasAuthority('MODERATOR')")
    @GetMapping("/roomEdit")
    public String roomEditForm(@RequestParam("id") String userId, Model model) {
        model.addAttribute("room", roomRepo.findById(Long.valueOf(userId)).get());
        model.addAttribute("types", RoomType.values());
        return "control_panel/roomEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')  OR hasAuthority('MODERATOR')")
    @PostMapping("/roomSave")
    public String roomSave(
            Room room,
            @RequestParam String roomId,
            @RequestParam String ty,
            Model model)
    {
        room.setId(Long.valueOf(roomId));
        Room roomFromDb = roomRepo.findByNumber(room.getNumber());
        if(roomFromDb != null)
        {
            if(roomFromDb.getId() != room.getId()) {
                model.addAttribute("message", "Room already exists!");
                model.addAttribute("types", RoomType.values());
                return "control_panel/roomEdit";
            }
        }
        if(room.getName().isEmpty())
        {
            model.addAttribute("message", "Enter a name!");
            model.addAttribute("types", RoomType.values());
            return "control_panel/roomEdit";
        }
        room.setClean_pend(false);
        room.setType(RoomType.valueOf(ty));
        roomRepo.save(room);
        return "redirect:/rooms";
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/cleanPend")
    public String cleanPend(@RequestParam("id") String roomId) {
        Room room = roomRepo.findById(Long.valueOf(roomId)).get();
        room.setPend_date(LocalDateTime.now());
        room.setClean_pend(true);
        roomRepo.save(room);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('CLEANER')")
    @GetMapping("/cleaned")
    public String cleaned(@RequestParam("id") String roomId) {
        Room room = roomRepo.findById(Long.valueOf(roomId)).get();

        if(!room.isClean_pend())
        {
            return "redirect:/cleaningError";
        }

        Cleanup cleanup = new Cleanup();
        cleanup.setPend_date(room.getPend_date());
        cleanup.setClean_date(LocalDateTime.now());
        cleanup.setRoom_id(room.getId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        cleanup.setUser_id(userRepo.findByUsername(auth.getName()).getId());

        room.setPend_date(null);
        room.setClean_pend(false);
        roomRepo.save(room);
        cleanupRepo.save(cleanup);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('CLEANER')")
    @GetMapping("/cleaningError")
    public String cleanError() {
        return "cleaningError";
    }
}
