package ir.uid.model.service;

import ir.uid.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface LoginService {
    User loginUser(String key, String lid) throws Exception;
}
