package ir.uid.model.service.impl;

import com.google.zxing.WriterException;
import ir.uid.exception.NotFoundException;
import ir.uid.model.DTO.LidAndLinkDTO;
import ir.uid.model.entity.OTQ;
import ir.uid.model.repository.OTQRepository;
import ir.uid.model.service.OTQService;
import ir.uid.util.QRGenerator;
import ir.uid.util.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static ir.uid.util.Constants.QR_CODE_IMAGE_PATH;

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
    public LidAndLinkDTO generateQrAndInsertLid(String id, String token, int size, String callBackUrl) throws IOException, WriterException {
        String lid        = RandomGenerator.getRandomString();
        String qrCodePath = QRGenerator.generateQrCode(lid, size);
        OTQ    otq        = new OTQ();
        otq.setBusinessId(id);
        otq.setLid(lid);
        otq.setCallBackUrl(callBackUrl);
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
