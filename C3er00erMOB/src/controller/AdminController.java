package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import entity.Admin;
import login.AdminSession;
import login.SecurityFunc;

/**
 * The class for all actions related to Super-admin settings
 * 
 * @author JIAYING
 *
 */
public class AdminController {

	/**
	 * The path to the CSV file that stores all the admin users
	 */
	// private static String ADMINFILE="src/data/admin.csv";
	private static final String ADMINFILE = "data/admin.csv";

	public AdminController() {

	}

	/**
	 * The function is fetch all the users inside the admin.csv file and compile
	 * them into a list
	 * 
	 * @return Return a list of admin users
	 */
	public List<Admin> getAdminUsers() {
		List<Admin> adminList = new ArrayList<>();
		BufferedReader br = null;
		String line = "";
		Admin adminTmp;
		try {
			br = new BufferedReader(new FileReader(ADMINFILE));
			while ((line = br.readLine()) != null) {
				String[] user = line.split(",");
				if (!user[0].equals("Name")) {
					adminTmp = new Admin(user[0], user[1], user[2], Integer.parseInt(user[3]));
					adminList.add(adminTmp);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return adminList;
	}

	/**
	 * The function is to update the admin.csv file
	 * 
	 * @param adminList A list of updated admin
	 * @return A boolean variable that indicates whether the operation is successful
	 *         or not
	 */
	public boolean updateAdminCSV(List<Admin> adminList) {
		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter(ADMINFILE);
			csvWriter.append("Name");
			csvWriter.append(",");
			csvWriter.append("Password");
			csvWriter.append(",");
			csvWriter.append("Salt");
			csvWriter.append(",");
			csvWriter.append("AccessLevel");
			csvWriter.append("\n");

			for (Admin admtmp : adminList) {
				StringBuilder sb = new StringBuilder();
				sb.append(admtmp.username);
				sb.append(',');
				sb.append(admtmp.password);
				sb.append(',');
				sb.append(admtmp.salt);
				sb.append(',');
				sb.append(Integer.toString(admtmp.accesslevel));
				sb.append(',');
				sb.append('\n');
				csvWriter.append(sb.toString());
			}

			csvWriter.flush();
			csvWriter.close();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			LoggerController.getInstance().LogErrorEntry("admin", "Error in saving/opening Admin.csv");
			return false;
		}

	}

	/**
	 * The function is to update user password
	 * 
	 * @param admSess An admin session of the identity of user
	 * @return A boolean variable that indicates whether the operation is successful
	 *         or not
	 */
	public boolean updateAdminUserPassword(AdminSession admSess) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter current password: ");
		String currPassword = sc.next();
		Admin adtmp = this.getAdminUser(admSess.getUsername());
		if (admSess.authenticatePassword(currPassword, adtmp)) {
			for (;;) {
				System.out.println("Enter new password: ");
				String newPassword = sc.next();
				System.out.println("Confirm new password: ");
				String newCfmPassword = sc.next();
				if (newPassword.equals(newCfmPassword)) {
					// Generate new salt
					String newSalt = Base64.getEncoder().encodeToString(SecurityFunc.getNextSalt());

					// Newly hashed password
					String newPasswordHash = SecurityFunc.hash(newPassword.toCharArray(),
							Base64.getDecoder().decode(newSalt));

					// update admin user password & salt
					adtmp.password = newPasswordHash;
					adtmp.salt = newSalt;

					List<Admin> adminList = this.getAdminUsers();
					for (Admin a : adminList) {
						if (a.username.equals(admSess.getUsername())) {
							a.password = newPasswordHash;
							a.salt = newSalt;
							break;
						}
					}

					// Update csv file
					this.updateAdminCSV(adminList);
					System.out.println("Password updated!");
					return true;
				} else {
					System.out.println("Passwords did not match!");
					System.out.println("Please try again.");
				}
			}
		}
		LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "Wrong password to change password");
		return false;
	}

	/**
	 * The function is to lock users that failed to login multiple times
	 * 
	 * @param username The name of the user is parsed in to be locked
	 */
	public void lockAdminUser(String username) {
		List<Admin> adminList = getAdminUsers();
		for (Admin adminObj : adminList) {
			if (adminObj.username.equals(username)) {
				// Set to NOACCESS
				adminObj.accesslevel = 0;
			}
		}
		// update CSV file
		updateAdminCSV(adminList);
	}

	/**
	 * The function is to delete users
	 * 
	 * @return A boolean variable to see if user was deleted successfully
	 */
	public boolean deleteAdminUser() {
		Scanner sc = new Scanner(System.in);
		List<Admin> adminList = getAdminUsers();
		for (Admin adminObj : adminList) {
			System.out.println("===========");
			printUserDetails(adminObj);
			System.out.println("===========");
		}
		System.out.println("Enter username to remove: ");
		String removeUsername = sc.next();
		System.out.println("Are you sure? (Y/N)");
		char cfm = sc.next().charAt(0);
		if (cfm == 'Y' || cfm == 'y') {
			int indexRemove = -1;
			for (int i = 0; i < adminList.size(); i++) {
				if (adminList.get(i).username.equals(removeUsername))
					indexRemove = i;
			}
			if (indexRemove != -1) {
				adminList.remove(indexRemove);
			} else {
				System.out.println("No such user!");
				return false;
			}
			if (updateAdminCSV(adminList)) {
				return true;
			} else {
				return false;
			}
		} else {
			System.out.println("Task abort..........");
			return false;
		}
	}

	/**
	 * The function is to unlock users
	 * 
	 * @return A boolean variable to see if user was unlocked successfully
	 */
	public boolean unlockAdminUser() {
		Scanner sc = new Scanner(System.in);
		List<Admin> adminList = getAdminUsers();
		boolean check = true;
		for (Admin adminObj : adminList) {
			if (adminObj.accesslevel == 0) {
				printUserDetails(adminObj);
			} else {
				check = false;
			}
		}

		if (check) {
			System.out.println("Choose user to unlock: ");
			String unlockUserName = sc.next();
			System.out.println("Are you sure? (Y/N)");
			char cfm = sc.next().charAt(0);
			if (cfm == 'Y' || cfm == 'y') {
				for (int i = 0; i < adminList.size(); i++) {
					if (adminList.get(i).username.equals(unlockUserName)) {
						adminList.get(i).accesslevel = 1;
						if (updateAdminCSV(adminList)) {
							return true;
						}
					}
				}
			}
			return false;
		} else {
			System.out.println("No user to unlock!");
			System.out.println("Task abort..........");
			return false;
		}
	}

	/**
	 * The function is get a particular user from the admin.csv file
	 * 
	 * @param username The name of the user to get admin object from the list of
	 *                 admin users
	 * @return An admin object of the user
	 */
	public Admin getAdminUser(String userName) {
		List<Admin> adminList = getAdminUsers();
		for (Admin adminObj : adminList) {
			if (adminObj.username.equals(userName)) {
				return adminObj;
			}
		}
		return null;
	}

	/**
	 * The function is to make a new admin user
	 * 
	 * @return A boolean variable to see if the creation of user was successful
	 */
	public boolean makeAdminUser() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter username: ");
		String uname = sc.next();
		System.out.println("Enter accesslevel(0-2)");
		int accesslevel = sc.nextInt();
		System.out.println("Enter password");
		String password = sc.next();

		Admin adtmp = new Admin();
		adtmp.username = uname;
		String salt = Base64.getEncoder().encodeToString(SecurityFunc.getNextSalt());
		adtmp.password = SecurityFunc.hash(password.toCharArray(), Base64.getDecoder().decode(salt));
		adtmp.salt = salt;
		adtmp.accesslevel = accesslevel;

		List<Admin> adminList = this.getAdminUsers();
		adminList.add(adtmp);
		if (updateAdminCSV(adminList))
			return true;
		return false;
	}

	/**
	 * The function is solely for debugging only
	 * 
	 * @param adminObj An admin object for printing of details
	 */
	public void printUserDetails(Admin adminObj) {
		System.out.println("Username: " + adminObj.username);
		System.out.println("Password: " + adminObj.password);
		System.out.println("Salt: " + adminObj.salt);
		System.out.println("AccessLevel: " + adminObj.accesslevel);
	}
}
