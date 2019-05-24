package ir.uid.util;

import ir.uid.UidOnBlockChain;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Constants {
    static ApplicationHome home = new ApplicationHome(UidOnBlockChain.class);
    public static final String basePath = home.getDir().toString() + File.separator + "files"+ File.separator;

    public static final String QRCODE_IMAGE_FORMAT = "PNG";
    public static final String QR_CODE_IMAGE_PATH = home.getDir().toString() + File.separator + "QRs" + File.separator;

    public static final String ALGORITHM = "AES";
    public static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static final String SECRET_KEY = "thisisnotateapotornothingat";

    public static final String BASE_FOLDER_PROFILE_PIC = File.separator + "profilePictures" + File.separator;

}
