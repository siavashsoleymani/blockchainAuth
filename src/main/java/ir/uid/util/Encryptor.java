package ir.uid.util;

import ir.uid.model.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Component
@Scope("singleton")
public class Encryptor {


    private Key    aesKey = null;
    private Cipher cipher = null;

    synchronized private void init(String keyStr) throws Exception {
        if (keyStr == null || keyStr.length() != 16) {
            throw new Exception("bad aes key configured");
        }
        if (aesKey == null) {
            aesKey = new SecretKeySpec(keyStr.getBytes(), "AES");
            cipher = Cipher.getInstance("AES");
        }
    }

    synchronized public String encrypt(String text, String key) throws Exception {
        init(key);
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        return toHexString(cipher.doFinal(text.getBytes()));
    }

    synchronized public String decrypt(String text, String key) throws Exception {
        init(key);
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        return new String(cipher.doFinal(toByteArray(text)));
    }

    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

    public User decryptUser(User encryptedUser, String image) throws Exception {
        String key = generateKey(image);
        encryptedUser.setDob(this.decrypt(encryptedUser.getDob(), key));
        encryptedUser.setName(this.decrypt(encryptedUser.getName(), key));
        encryptedUser.setSex(this.decrypt(encryptedUser.getSex(), key));
        return encryptedUser;
    }


    public User encryptUserDatas(User user, String file) throws Exception {
        String key = generateKey(file);
        user.setDob(this.encrypt(user.getDob(), key));
        user.setName(this.encrypt(user.getName(), key));
        user.setSex(this.encrypt(user.getSex(), key));
        return user;
    }

    private String generateKey(String file) throws IOException, NoSuchAlgorithmException {
        String hash = hash(file);
        return hash.subSequence(0, 16).toString();
    }

    private String hash(String text) throws NoSuchAlgorithmException {
        return DigestUtils.sha256Hex(text);
    }
}