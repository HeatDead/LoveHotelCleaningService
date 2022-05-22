package com.example.lovehotelcleaningservice.controller;

import com.example.lovehotelcleaningservice.domain.Message;
import com.example.lovehotelcleaningservice.domain.User;
import com.example.lovehotelcleaningservice.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    /*
    @GetMapping("/")
    public String main(Map<String, Object> model)
    {
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "home";
    }

    @PostMapping("/")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String text,
                      @RequestParam String tag,
                      Map<String, Object> model)
    {
        Message message = new Message(text, tag, user);

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);

        return "home";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model)
    {
        Iterable<Message> messages;

        if(filter != null && !filter.isEmpty())
            messages = messageRepo.findByTag(filter);
        else
            messages = messageRepo.findAll();

        model.put("messages", messages);
        return "home";
    }*/
}
