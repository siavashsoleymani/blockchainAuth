package ir.uid.controllers;

import ir.uid.model.DTO.LidDTO;
import ir.uid.model.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users/login")
    public ResponseEntity<String> loginUserToBusiness(@RequestBody LidDTO lidDTO) throws Exception {
        loginService.loginUser(lidDTO.getKey(), lidDTO.getLid());
        return new ResponseEntity<>("ok", HttpStatus.OK);

    }
}
