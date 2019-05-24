package ir.uid.controllers;

import ir.uid.model.entity.User;
import ir.uid.model.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
public class    UserController {
    @Autowired
    private UsersServiceImpl usersService;

    @PostMapping("/users")
    public User addUser(String name, String dob, String sex,
                           @RequestParam("file") MultipartFile file) throws Exception {
        return usersService.addUser(new User(name, dob, sex), file);
    }

    @PostMapping(value = "/users", params = {"lid"})
    public User getUser(@RequestParam("file") MultipartFile file, @RequestParam("lid") String lid) throws Exception {
        User user = usersService.getUserWithLid(file, lid);
        return user;
    }
}
