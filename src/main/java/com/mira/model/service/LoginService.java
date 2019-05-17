package com.mira.model.service;

import com.mira.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface LoginService {
    User loginUser(MultipartFile file, String lid) throws Exception;
}
