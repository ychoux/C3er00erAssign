package login;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SecurityFunc {
	public static byte[] getNextSalt() {
		Random RANDOM = new SecureRandom();
		byte[] salt = new byte[32];
		RANDOM.nextBytes(salt);
		return salt;
	}
	public static String hash(char[] password, byte[] salt) {
		PBEKeySpec spec = new PBEKeySpec(password, salt, 10000, 512);
		Arrays.fill(password, Character.MIN_VALUE);
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return Base64.getEncoder().encodeToString(skf.generateSecret(spec).getEncoded());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
		} finally {
			spec.clearPassword();
		}
	}
}
