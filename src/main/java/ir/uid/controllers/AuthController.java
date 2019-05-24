package ir.uid.controllers;

import ir.uid.model.entity.User;
import ir.uid.model.service.impl.UsersServiceImpl;
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
    public User verifyUser(@RequestParam("key") String file) throws Exception {
        User user = userSvc.probeUser(file);
        return user;
    }
}
