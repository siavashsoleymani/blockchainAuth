package com.mira.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRGenerator {


    public static String generateQrCode(String text, int size) throws IOException, WriterException {
        File file = new File(Constants.QR_CODE_IMAGE_PATH);
        if (!file.exists()) file.mkdirs();
        return generateQRCodeImage(text, size, size, Constants.QR_CODE_IMAGE_PATH);
    }


    public static String generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        filePath = filePath + text + ".png";
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, Constants.QRCODE_IMAGE_FORMAT, path);
        return filePath;
    }

    public static byte[] getQRCodeByte(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, Constants.QRCODE_IMAGE_FORMAT, pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}