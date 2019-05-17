package com.mira.controllers;

import com.mira.model.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users/login")
    public ResponseEntity<String> loginUserToBusiness(@RequestBody MultipartFile file,
                                                      String lid) throws Exception {
        loginService.loginUser(file, lid);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
