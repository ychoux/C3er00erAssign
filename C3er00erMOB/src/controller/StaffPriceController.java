package controller;
import java.util.Scanner;

import entity.Admin;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class StaffPriceController {

	/**
	 * The path to the CSV file that stores all the ticket price rate
	 */
	private static final String RATEPATH = "src/data/rates.csv";
	
	
	
	public static void staffShowRates() {
		Hashtable<String, Double> rates = (Hashtable<String, Double>) controller.PriceManager.getInstance().getRates();
		int index=0;
		for(String key : rates.keySet()) {
			System.out.println(index+1+". Type :"+key+", Rate: "+rates.get(key));
			index++;
		}
	}
	
	/**
	 * 
	 */
	public static boolean staffUpdateRates() {
		Scanner sc = new Scanner(System.in);
		System.out.println("====================");
		Hashtable<String, Double> rates = (Hashtable<String, Double>) PriceManager.getInstance().getRates();
		int index=0;
		for(String key : rates.keySet()) {
			System.out.println(index+1+". Type :"+key+", Rate: "+rates.get(key));
			index++;
		}
		System.out.println("====================");
		String choice="";
		for(;;) {
			System.out.println("Enter Type: ");
			choice = sc.next().toUpperCase();
			if(rates.containsKey(choice))
				break;
			else {
				System.out.println("Invalid option! Try again.");
			}
		}
		System.out.println("Enter rate to change: ");
		double newRate=sc.nextDouble();
		
		// updates rates dictionary
		rates.replace(choice, rates.get(choice),newRate);
		
		// updates rates.csv
		if(saveRatesCSV(rates))
			return true;
		return false;
	}
	
	private static boolean saveRatesCSV(Hashtable<String, Double> rates) {
		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter(RATEPATH);
			for(String key : rates.keySet()) {
				StringBuilder sb = new StringBuilder();
				sb.append(key);
				sb.append(',');
				sb.append(rates.get(key));
				sb.append(',');
				sb.append('\n');
				csvWriter.append(sb.toString());
			}
			csvWriter.flush();
			csvWriter.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
}
