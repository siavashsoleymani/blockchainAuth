package ir.uid.model.service;

import com.google.zxing.WriterException;
import ir.uid.model.DTO.LidAndLinkDTO;

import java.io.File;
import java.io.IOException;

public interface OTQService {
    LidAndLinkDTO generateQrAndInsertLid(String id, String token, int size, String callBackUrl) throws IOException, WriterException;

    File getQrCodeFileIfExists(String lid);
}
