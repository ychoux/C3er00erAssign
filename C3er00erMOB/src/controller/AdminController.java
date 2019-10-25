package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import entity.Admin;

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
				// use comma as separator
				String[] user = line.split(",");
				System.out.println(user[0]+user[1]+user[2]);
				a=new Admin(user[0],user[1],Integer.parseInt(user[2]));
				adminList.add(a);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return adminList;
	}
}
