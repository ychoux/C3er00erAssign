package login;

import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import controller.AdminController;
import entity.Admin;

public class AdminSession {

	private String username="";
	private AccessLevel accesslevel=AccessLevel.NOACCESS;

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
		System.out.println("Hash: "+(SecurityFunc.hash(password.toCharArray(), Base64.getDecoder().decode(adminuser.salt))));
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

	private boolean authenticatePassword(String password,Admin adtmp) {
		if((SecurityFunc.hash(password.toCharArray(), Base64.getDecoder().decode(adtmp.salt))).equals(adtmp.password)) {
			return true;
		}else {
			return false;
		}
	}

	// view for User Settings
	public void userSettings(AdminController aCon) {
		Scanner sc=new Scanner(System.in);
		System.out.println("1. Change Password");
		System.out.println("2. Create user");
		System.out.println("3. Delete user");
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			for(;;) {
				System.out.println("Enter current password: ");
				String currpassword = sc.next();
				Admin adtmp=aCon.getAdminUser(this.getUsername());
				if(authenticatePassword(currpassword,adtmp)) {
					for(;;) {
						System.out.println("Enter new password: ");
						String newpassword = sc.next();
						System.out.println("Confirm new password: ");
						String newcfmpassword = sc.next();
						if(newpassword.equals(newcfmpassword)) {
							// Generate new salt
							String newSalt = Base64.getEncoder().encodeToString(SecurityFunc.getNextSalt());

							// Newly hashed password
							String newPassword= SecurityFunc.hash(newpassword.toCharArray(),Base64.getDecoder().decode(newSalt));

							// update admin user password & salt
							adtmp.password = newPassword;
							adtmp.salt = newSalt;
							
							List<Admin> adminList=aCon.getAdminUsers();
							for(Admin a : adminList) {
								if(a.username.equals(this.getUsername())) {
									a.password = newPassword;
									a.salt = newSalt;
								}
							}

							// Update csv file
							aCon.updateAdminCSV(adminList);
							System.out.println("Password updated!");
							break;
						}else {
							System.out.println("Passwords did not match!");
							System.out.println("Please try again.");
						}
					}
					break;
				}else {
					System.out.println("Passwords did not match!");
					System.out.println("Please try again.");
				}
			}
		case 2:
			break;
		case 3:
			break;
		}

		sc.close();
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
