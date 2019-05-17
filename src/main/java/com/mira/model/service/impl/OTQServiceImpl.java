package com.mira.model.service.impl;

import com.google.zxing.WriterException;
import com.mira.exception.NotFoundException;
import com.mira.model.DTO.LidAndLinkDTO;
import com.mira.model.entity.OTQ;
import com.mira.model.repository.OTQRepository;
import com.mira.model.service.OTQService;
import com.mira.util.QRGenerator;
import com.mira.util.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static com.mira.util.Constants.QR_CODE_IMAGE_PATH;

@Service
public class OTQServiceImpl implements OTQService {

    private final OTQRepository otqRepository;
    private final String        baseUrl;

    @Autowired
    public OTQServiceImpl(OTQRepository otqRepository,
                          String baseUrl) {
        this.otqRepository = otqRepository;
        this.baseUrl = baseUrl;
    }

    @Override
    public LidAndLinkDTO generateQrAndInsertLid(String id, String token, int size) throws IOException, WriterException {
        String lid        = RandomGenerator.getRandomString();
        String qrCodePath = QRGenerator.generateQrCode(lid, size);
        OTQ    otq        = new OTQ();
        otq.setBusinessId(id);
        otq.setLid(lid);
        otqRepository.save(otq);
        return new LidAndLinkDTO(baseUrl + "/downloadQrCode/" + lid, lid);
    }

    @Override
    public File getQrCodeFileIfExists(String lid) {
        OTQ byLid = otqRepository.findByLid(lid);
        if (Objects.isNull(byLid))
            throw new NotFoundException("lid not found");
        return new File(QR_CODE_IMAGE_PATH + lid + ".png");
    }
}
