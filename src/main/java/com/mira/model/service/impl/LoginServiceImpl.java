package com.mira.model.service.impl;

import com.mira.exception.NotFoundException;
import com.mira.exception.UserNotFoundException;
import com.mira.model.entity.OTQ;
import com.mira.model.entity.User;
import com.mira.model.repository.OTQRepository;
import com.mira.model.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    private final UsersServiceImpl usersService;
    private final Environment      env;
    private final OTQRepository otqRepository;

    @Autowired
    public LoginServiceImpl(UsersServiceImpl usersService, Environment env, OTQRepository otqRepository) {
        this.usersService = usersService;
        this.env = env;
        this.otqRepository = otqRepository;
    }

    @Override
    public User loginUser(MultipartFile file, String lid) throws Exception {
        OTQ    byLid             = otqRepository.findByLid(lid);
        if (Objects.isNull(byLid)) throw new NotFoundException("lid not found");
        User user = usersService.probeUser(file);
        if (Objects.isNull(user)) throw new UserNotFoundException(env.getProperty("usernotfound"));
        String templateFromImage = usersService.getTemplateFromImage(file).serialize();
        byLid.setUserId(templateFromImage);
        otqRepository.save(byLid);
        return user;
    }
}
