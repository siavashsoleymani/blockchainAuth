package com.mira.model.service;

import com.google.zxing.WriterException;
import com.mira.model.DTO.LidAndLinkDTO;

import java.io.File;
import java.io.IOException;

public interface OTQService {
    LidAndLinkDTO generateQrAndInsertLid(String id, String token, int size) throws IOException, WriterException;

    File getQrCodeFileIfExists(String lid);
}
