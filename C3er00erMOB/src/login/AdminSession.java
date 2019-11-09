package login;

import java.util.Base64;
import java.util.List;

import controller.AdminController;
import entity.Admin;

public class AdminSession {

	private String username="";
	private AccessLevel accesslevel=AccessLevel.NOACCESS;

	/**
	 *	This function helps to create a session
	 *	@param username to be authenticated
	 *	@param password to be authenticated
	 * 	@return returns either a valid or not valid session depending if its authenticated
	 * 	@exception if the user does not exist
	 *  @exception if the password entered is wrong
	 */
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
		System.out.println("Welcome back! "+username);
		return session;
	}


	/**
	 *	This function helps to authenticate the session
	 *	@param username to be authenticated
	 *	@param password to be authenticated
	 * 	@return returns either a valid or not valid session depending if its authenticated
	 * 	@exception if the user does not exist
	 *  @exception if the password entered is wrong
	 */
	AdminSession(String username,String password) throws NoSuchUserException,WrongPasswordException{
		AdminController acon=new AdminController();
		List<Admin> aList = acon.getAdminUsers();
		Admin adminuser=new Admin();
		boolean userexist=false;
		for(Admin a : aList) {
			if(a.username.equals(username)) {
				userexist=true;
				adminuser=a;
			}
		}
		if(userexist == false) {
			NoSuchUserException up = new NoSuchUserException();
			throw up;
		}
		// Debugging Text
		// System.out.println("Hash: "+(SecurityFunc.hash(password.toCharArray(), Base64.getDecoder().decode(adminuser.salt))));
		//

		if((SecurityFunc.hash(password.toCharArray(), Base64.getDecoder().decode(adminuser.salt))).equals(adminuser.password)) {

			switch(adminuser.AccessLevel) {
			case 1:
				this.setAccesslevel(AccessLevel.ADMIN);
				break;
			case 2:
				this.setAccesslevel(AccessLevel.SUPERADMIN);
				break;
			default:
				this.setAccesslevel(AccessLevel.NOACCESS);
			}
			this.setUsername(adminuser.username);


		}else {
			WrongPasswordException away = new WrongPasswordException();
			throw away;
		}

	}
	
	/**
	 *	This function helps the password entered
	 *	@param password entered
	 *  @param user obj that was retrieved when user name was valid
	 *  @return	to see if the password is correct
	 */
	public boolean authenticatePassword(String password,Admin adtmp) {
		if((SecurityFunc.hash(password.toCharArray(), Base64.getDecoder().decode(adtmp.salt))).equals(adtmp.password)) {
			return true;
		}else {
			return false;
		}
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public AccessLevel getAccesslevel() {
		return accesslevel;
	}


	public void setAccesslevel(AccessLevel accesslevel) {
		this.accesslevel = accesslevel;
	}

}
