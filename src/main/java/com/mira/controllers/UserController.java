package com.mira.controllers;

import com.mira.model.entity.User;
import com.mira.model.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
public class    UserController {
    @Autowired
    private UsersServiceImpl userSvc;

    @PostMapping("users")
    public User addUser(String name, String dob, String sex,
                           @RequestParam("file") MultipartFile file) throws Exception {
        return userSvc.addUser(new User(name, dob, sex), file);
    }
}
