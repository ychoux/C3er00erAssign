package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


import entity.Admin;
import login.SecurityFunc;

public class AdminController {

	private static String ADMINFILE="src/data/admin.csv";

	public AdminController() {

	}

	public List<Admin> getAdminUsers(){
		List<Admin> adminList = new ArrayList<>();
		BufferedReader br = null;
		String line = "";
		Admin admintmp;
		try {
			br = new BufferedReader(new FileReader(ADMINFILE));
			while ((line = br.readLine()) != null) {
				String[] user = line.split(",");
				if(!user[0].equals("Name")) {
					// Debug line
					// System.out.println("Username: "+user[0]+" Password: "+user[1]+" Salt: "+user[2]);
					admintmp=new Admin(user[0],user[1],user[2],Integer.parseInt(user[3]));
					adminList.add(admintmp);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return adminList;
	}

	
	public void updateAdminCSV(List<Admin> adminList) {
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
				sb.append(Integer.toString(admtmp.AccessLevel));
				sb.append(',');
				sb.append('\n');
				csvWriter.append(sb.toString());
			}

			csvWriter.flush();
			csvWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void lockAdminUser(String username) {
		List<Admin> adminList=getAdminUsers();
		for(Admin a : adminList) {
			if(a.username.equals(username)) {
				// Set to NOACCESS 
				a.AccessLevel=0;
				
			}
		}
		// update csv file
		updateAdminCSV(adminList);
	}

	public Admin getAdminUser(String username) {
		List<Admin> adminList=getAdminUsers();
		for(Admin a : adminList) {
			if(a.username.equals(username)) {
				return a;
			}
		}

		return null;
	}

	public Admin makeAdminUser(String username, String password, int AccessLevel) {
		Admin a=new Admin();
		a.username=username;
		String salt=Base64.getEncoder().encodeToString(SecurityFunc.getNextSalt());
		a.password=SecurityFunc.hash(password.toCharArray(),Base64.getDecoder().decode(salt));
		a.salt=salt;
		a.AccessLevel=AccessLevel;
		printUserDetails(a);
		return a;
	}


	// Debug
	public void printUserDetails(Admin a) {
		System.out.println("Username: "+a.username);
		System.out.println("Password: "+a.password);
		System.out.println("Salt: "+a.salt);
		System.out.println("AccessLevel: "+a.AccessLevel);
	}
}
