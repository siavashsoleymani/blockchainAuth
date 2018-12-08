package com.mira.controllers;

import com.mira.exception.UserNotFoundException;
import com.mira.model.User;
import com.mira.services.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AuthController {
    @Autowired
    private UsersServiceImpl userSvc;

    @Autowired
    private Environment env;

    @PostMapping("verify")
    public User verifyUser(@RequestParam("file") MultipartFile file) throws Exception {
        User user = userSvc.probeUser(file);
        if (user == null) throw new UserNotFoundException(env.getProperty("usernotfound"));
        return user;
    }
}
