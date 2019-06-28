package ir.uid.util;

import ir.uid.model.entity.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

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
            aesKey = new SecretKeySpec(keyStr.getBytes(), "AES/ECB/PKCS5Padding");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
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

    public User decryptUser(User encryptedUser, String key) throws Exception {
        encryptedUser.setName(this.decrypt(encryptedUser.getName(), key));
        encryptedUser.setFamily(this.decrypt(encryptedUser.getFamily(), key));
        encryptedUser.setEmail(this.decrypt(encryptedUser.getEmail(), key));
        encryptedUser.setDob(this.decrypt(encryptedUser.getDob(), key));
        encryptedUser.setSex(this.decrypt(encryptedUser.getSex(), key));
        return encryptedUser;
    }


    public User encryptUserDatas(User user, String key) throws Exception {
        user.setName(this.encrypt(user.getName(), key));
        user.setFamily(this.encrypt(user.getFamily(), key));
        user.setEmail(this.encrypt(user.getEmail(), key));
        user.setDob(this.encrypt(user.getDob(), key));
        user.setSex(this.encrypt(user.getSex(), key));
        return user;
    }
}