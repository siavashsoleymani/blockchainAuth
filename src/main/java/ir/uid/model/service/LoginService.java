package ir.uid.model.service;

import ir.uid.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface LoginService {
    User loginUser(MultipartFile file, String lid) throws Exception;
}
