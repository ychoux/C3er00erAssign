package login;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * This class is made to house the security methods for password hashing
 * @author JIAYING
 *
 */
public class SecurityFunc {
	
	/**
	 * This function is used to generate a random salt byte to use for password hashing
	 * @return	
	 */
	public static byte[] getNextSalt() {
		Random RANDOM = new SecureRandom();
		byte[] salt = new byte[32];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	/**
	 * This function is to take in the password and generated hash to perform password hashing
	 * @param password	This is the char[] equivalent of the user's password
	 * @param salt		This is the randomly generated salt
	 * @return			The hashed password
	 */
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
