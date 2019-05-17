package com.mira.model.service.impl;

import com.mira.model.service.CredentialService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;

@Service
public class CredentialServiceImpl implements CredentialService {
    private Credentials credentials;

    @Value("${privatekey}")
    private String privateKey;

    @Override
    public Credentials getCredentials() {
        credentials = credentials != null ? credentials : Credentials.create(privateKey);
        return credentials;
    }
}
