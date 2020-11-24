package me.jko.pay.domain.pay.helper;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Base64;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

@Component
public class Decrypter {
    private static final String KEY = "1234567890123456";
    private static final SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");


    public String decrypt(String target) throws GeneralSecurityException {
        Cipher cipher = getCipher();
        cipher.init(DECRYPT_MODE, secretKeySpec);

        return new String(
            cipher.doFinal(Base64.getDecoder().decode(target)));
    }

    private Cipher getCipher() throws GeneralSecurityException {
        return Cipher.getInstance("AES");
    }
}
