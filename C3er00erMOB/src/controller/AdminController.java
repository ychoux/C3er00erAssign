package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import entity.Admin;
import login.SecurityFunc;

public class AdminController {

	private static String ADMINFILE="src/data/admin.csv";

	public List<Admin> getAdminUsers(){
		List<Admin> adminList = new ArrayList<>();
		BufferedReader br = null;
		String line = "";
		Admin a;
		try {
			br = new BufferedReader(new FileReader(ADMINFILE));
			while ((line = br.readLine()) != null) {
				String[] user = line.split(",");
				// Debug line
				System.out.println("Username: "+user[0]+" Password: "+user[1]+" Salt: "+user[2]);
				a=new Admin(user[0],user[1],user[2],Integer.parseInt(user[3]));
				adminList.add(a);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return adminList;
	}

	public void makeAdminUser(String username, String password, int AccessLevel) {
		Admin a=new Admin();
		a.username=username;
		String salt=SecurityFunc.getNextSalt().toString();
		a.password=SecurityFunc.hash(password.toCharArray(),salt.getBytes()).toString();
		a.salt=salt;
		a.AccessLevel=AccessLevel;
		printUserDetails(a);
	}
	
	
	// Debug
	public void printUserDetails(Admin a) {
		System.out.println("Username: "+a.username);
		System.out.println("Password: "+a.password);
		System.out.println("Salt: "+a.salt);
		System.out.println("AccessLevel: "+a.AccessLevel);
	}
}
