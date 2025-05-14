package com.example.SpringLogin.Regi.service.serviceImpl;

import com.example.SpringLogin.Regi.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Override
    public List<String> getCoursesForUser(String username) {
        return Arrays.asList("Spring Boot", "Spring Security", "JWT");
    }
}
