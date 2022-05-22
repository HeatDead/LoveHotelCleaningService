package com.example.lovehotelcleaningservice.controller;

import com.example.lovehotelcleaningservice.domain.Role;
import com.example.lovehotelcleaningservice.domain.User;
import com.example.lovehotelcleaningservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class RegistrationController {


}
