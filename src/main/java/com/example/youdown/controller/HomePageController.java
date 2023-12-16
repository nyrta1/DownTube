package com.example.youdown.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomePageController {
    @GetMapping("home")
    public String homePage() {
        return "home";
    }

    @GetMapping("playlist")
    public String playlist() {
        return "home-playlist";
    }

    @GetMapping("channel")
    public String channelHomePage(){
        return "home-channel";
    }
}
