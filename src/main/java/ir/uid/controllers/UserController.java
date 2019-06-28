package ir.uid.controllers;

import ir.uid.model.entity.User;
import ir.uid.model.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
public class    UserController {
    @Autowired
    private UsersServiceImpl usersService;

    @PostMapping("/users")
    public User addUser(@RequestBody User user) throws Exception {
        return usersService.addUser(user);
    }

    @PatchMapping("/users")
    public User editUser(@RequestBody User user) throws Exception {
        return usersService.editUser(user);
    }
}
