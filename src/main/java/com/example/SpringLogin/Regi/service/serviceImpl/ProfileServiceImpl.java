package com.example.SpringLogin.Regi.service.serviceImpl;

import com.example.SpringLogin.Regi.service.ProfileService;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Override
    public String getProfileInfo(String username) {
        return "Profile for: " + username;
    }
}
