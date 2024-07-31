package es.udc.tfg.app.util.encrypt;

import com.password4j.Hash;
import com.password4j.Password;
import org.springframework.beans.factory.annotation.Value;

public class PasswordEncrypter {

    @Value("${password4j.salt}")
    private static String salt;

    public static String crypt(String password) {

        Hash hash = Password.hash(password)
                .addSalt(salt)
                .withArgon2();
        return hash.getResult();
    }

    public static boolean isCorrectPassword(String clearPassword, String encryptedPassword) {

        return Password.check(clearPassword, encryptedPassword).addSalt(salt).withArgon2();
    }

}
