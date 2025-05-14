package com.example.SpringLogin.Regi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @GetMapping
    public ResponseEntity<String> getCourses() {
        return ResponseEntity.ok("Here are your secured course details (JWT validated)");
    }
}
