package com.example.SpringLogin.Regi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public ResponseEntity<String> getProfile() {
        return ResponseEntity.ok("Here is your secured profile information (JWT validated)");
    }
}
