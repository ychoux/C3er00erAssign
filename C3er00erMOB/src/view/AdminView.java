package view;

import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import controller.AdminController;
import entity.Admin;
import login.AccessLevel;
import login.AdminSession;
import login.SecurityFunc;

public class AdminView {

	public static void userSettings(AdminSession admSess,AdminController aCon) {
		Scanner sc=new Scanner(System.in);
		System.out.println("1. Change Password");
		System.out.println("2. Create user");
		System.out.println("3. Delete user");
		System.out.println("4. Unlock user");
		System.out.println("5. Back");
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			for(;;) {
				System.out.println("Enter current password: ");
				String currpassword = sc.next();
				Admin adtmp=aCon.getAdminUser(admSess.getUsername());
				if(admSess.authenticatePassword(currpassword,adtmp)) {
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
								if(a.username.equals(admSess.getUsername())) {
									a.password = newPassword;
									a.salt = newSalt;
									break;
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
			if(admSess.getAccesslevel() == AccessLevel.ADMIN || admSess.getAccesslevel()== AccessLevel.SUPERADMIN) {
				System.out.println("Enter username: ");
				String uname=sc.next();
				System.out.println("Enter accesslevel(0-2)");
				int accesslevel=sc.nextInt();
				System.out.println("Enter password");
				String password=sc.next();
				Admin adtmp=aCon.makeAdminUser(uname, password, accesslevel);

				List<Admin> adminList=aCon.getAdminUsers();
				adminList.add(adtmp);
				aCon.updateAdminCSV(adminList);
				System.out.println("User created!");
				break;
			}
			else {
				System.out.println("No permission");
				break;
			}
		case 3:
			if(admSess.getAccesslevel() == AccessLevel.SUPERADMIN) {
				List<Admin> adminList=aCon.getAdminUsers();
				for(Admin a : adminList) {
					aCon.printUserDetails(a);
				}
				System.out.println("Choose user to remove: ");
				String removeUsername = sc.next();
				System.out.println("Are you sure? (Y/N)");
				char cfm = sc.next().charAt(0);
				if(cfm == 'Y' || cfm == 'y'){
					aCon.deleteAdminUser(removeUsername);
					System.out.println("User has been removed.");
				}else {
					System.out.println("Task abort..........");
				}
			}else {
				System.out.println("No permission");
			}
			break;
		case 4:
			if(admSess.getAccesslevel() == AccessLevel.SUPERADMIN) {
				
			}else {
				System.out.println("No permission");
			}
			break;

		case 5: 
			int pick = -1;
			while(true) {
				System.out.println("====================");
				System.out.println("1. Create/Update/Remove Movie Listing ");
				System.out.println("2. Create/Update/Remove cinema showtimes");
				System.out.println("3. User Settings");
				System.out.println("====================");
				pick = sc.nextInt();
				switch(pick) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					userSettings(admSess,aCon);
					break;
				}
				if(pick == -1){
					break;
				}
			break;
			}
		}
	}
}
