package login;

import java.util.Base64;
import java.util.List;


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
		System.out.println("Hash: "+(SecurityFunc.hash(password.toCharArray(), Base64.getDecoder().decode(adminuser.salt))));
		if((SecurityFunc.hash(password.toCharArray(), Base64.getDecoder().decode(adminuser.salt))).equals(adminuser.password)) {
			System.out.println("Correct Password lah!");
		}else {
			WrongPasswordException away = new WrongPasswordException();
			throw away;
		}
		
	}
	
}
