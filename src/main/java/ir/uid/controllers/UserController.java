package ir.uid.controllers;

import ir.uid.model.entity.User;
import ir.uid.model.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
public class    UserController {
    @Autowired
    private UsersServiceImpl usersService;

    @PostMapping("/users")
    public User addUser(@RequestBody User user) throws Exception {
        return usersService.addUser(user);
    }

    @PostMapping(value = "/users", params = {"lid"})
    public User getUser(@RequestBody String key, @RequestParam("lid") String lid) throws Exception {
        User user = usersService.getUserWithLid(key, lid);
        return user;
    }
}
