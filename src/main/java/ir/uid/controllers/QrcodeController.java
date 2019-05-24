package ir.uid.controllers;

import com.google.zxing.WriterException;
import ir.uid.model.DTO.LidAndLinkDTO;
import ir.uid.model.service.OTQService;
import ir.uid.util.StreamFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class QrcodeController {

    private final OTQService otqService;

    @Autowired
    public QrcodeController(OTQService otqService) {
        this.otqService = otqService;
    }

    @RequestMapping(method = GET, value = "/{businessId}/generateQrCode", params = {"size"})
    public ResponseEntity<LidAndLinkDTO> getQRCode(@PathVariable("businessId") String businessId
            , @RequestParam("size") int size
            , @RequestHeader("Authorization") String token) throws IOException, WriterException {
        return new ResponseEntity<>(otqService.generateQrAndInsertLid(businessId, token, size), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "/downloadQrCode/{lid}")
    public void downloadQrCode(@PathVariable("lid") String lid, HttpServletResponse response) throws IOException {
        File file = otqService.getQrCodeFileIfExists(lid);
        StreamFileUtil.streamFileIntoResponse(file, response);
    }

}
