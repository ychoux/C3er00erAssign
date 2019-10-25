package login;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import controller.AdminController;
import entity.Admin;

public class AdminSession {
	
	public static AdminSession createSession(String username,String password) {
		AdminSession session = null;
		try {
			session = new AdminSession(username, password);
		} catch (NoSuchUserException e) {
			System.out.println("No such user found!");
			return null;
		} catch (WrongPasswordException e) {
			System.out.println("Wrong password entered!");
			return null;
		}
		System.out.println("No errors logging in, successful!");
		return session;
	}
	
	
	AdminSession(String username,String password) throws NoSuchUserException,WrongPasswordException{
		AdminController acon=new AdminController();
		List<Admin> aList = acon.getAdminUsers();
		Admin adminuser=new Admin();
		boolean userexist=false;
		for(Admin a : aList) {
			if(a.username.equalsIgnoreCase(username)) {
				userexist=true;
				adminuser=a;
			}
		}
		if(userexist == false) {
			NoSuchUserException up = new NoSuchUserException();
			throw up;
		}
		System.out.println("Hash: "+(SecurityFunc.hash(password.toCharArray(), adminuser.salt.getBytes())).toString());
		if((SecurityFunc.hash(password.toCharArray(), adminuser.salt.getBytes())).toString().equals(adminuser.password)) {
			System.out.println("Correct Password lah!");
		}else {
			WrongPasswordException away = new WrongPasswordException();
			throw away;
		}
		
	}
	
	public static byte[] hash(char[] password, byte[] salt) {
	    PBEKeySpec spec = new PBEKeySpec(password, salt, 10000, 256);
	    Arrays.fill(password, Character.MIN_VALUE);
	    try {
	      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	      return skf.generateSecret(spec).getEncoded();
	    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
	      throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
	    } finally {
	      spec.clearPassword();
	    }
	  }
	
}
