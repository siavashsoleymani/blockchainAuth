package ir.uid.model.service.impl;

import ir.uid.exception.NotFoundException;
import ir.uid.exception.UserNotFoundException;
import ir.uid.model.entity.OTQ;
import ir.uid.model.entity.User;
import ir.uid.model.repository.OTQRepository;
import ir.uid.model.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    private final UsersServiceImpl usersService;
    private final Environment      env;
    private final OTQRepository    otqRepository;
    private final RestTemplate     restTemplate;

    @Autowired
    public LoginServiceImpl(UsersServiceImpl usersService,
                            Environment env,
                            OTQRepository otqRepository,
                            RestTemplate restTemplate) {
        this.usersService = usersService;
        this.env = env;
        this.otqRepository = otqRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public User loginUser(String key, String lid) throws Exception {
        OTQ byLid = otqRepository.findByLid(lid);
        if (Objects.isNull(byLid)) throw new NotFoundException("lid not found");
        User user = usersService.probeUser(key);
        if (Objects.isNull(user)) throw new UserNotFoundException(env.getProperty("usernotfound"));
        byLid.setDeleted(true);
        user.setLid(lid);
        restTemplate.postForEntity(byLid.getCallBackUrl(), user, Object.class);
        otqRepository.save(byLid);
        return user;
    }
}
