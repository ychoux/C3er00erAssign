package controller;

import java.util.Scanner;

import java.io.FileWriter;
import java.io.IOException;

import java.util.Hashtable;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * This class is for CRU functions related to ticket pricings
 * 
 * @author JIAYING
 *
 */
public class StaffPriceController {

	/**
	 * The path to the CSV file that stores all the rates
	 */
	private static final String RATEPATH = "src/data/rates.csv";
	//private static final String RATEPATH = "data/rates.csv";
	/**
	 * The path to the CSV file that stores all the ph
	 */
	private static final String PHPATH = "src/data/PH.csv";
	//private static final String PHPATH = "data/PH.csv";

	/**
	 * The function is to display the current public holidays that are stored inside
	 * the ph.csv file
	 */
	public static void staffShowPh() {
		List<LocalDate> datesList = PriceManager.getInstance().getPH();
		int index = 0;
		for (LocalDate dateObj : datesList) {
			System.out.println(index + 1 + ". Date: " + dateObj);
			index++;
		}
	}

	/**
	 * The function is to display the current public holidays that are stored inside
	 * the ph.csv file
	 * 
	 * @return return a boolean to see if PH was added successfully.
	 */
	public static boolean staffAddPh() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("====================");
			System.out.println("Enter public holiday date [dd-MM-yyyy]: ");
			String date = sc.next();
			return PriceManager.getInstance().addPublicHoliday(date);
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	/**
	 * The function is to display the current rates that is stored inside the
	 * rates.csv file
	 */
	public static void staffShowRates() {
		Hashtable<String, Double> ratesHT = (Hashtable<String, Double>) controller.PriceManager.getInstance()
				.getRates();
		int index = 0;
		for (String key : ratesHT.keySet()) {
			System.out.println(index + 1 + ". Type :" + key + ", Rate: " + ratesHT.get(key));
			index++;
		}
	}

	/**
	 * The function is to add a new rate to the existing rates list
	 * 
	 * @return returns a boolean to the main view to see if the creation of rate was
	 *         successful
	 */
	public static boolean staffAddRates() {
		Scanner sc = new Scanner(System.in);
		System.out.println("====================");
		System.out.println("Enter name of rate: ");
		String rateType = sc.next();
		System.out.println("Enter rate: ");
		double rate = sc.nextDouble();

		Hashtable<String, Double> rates = (Hashtable<String, Double>) PriceManager.getInstance().getRates();
		rates.put(rateType, rate);

		return saveRatesCSV(rates);
	}

	/**
	 * The function is to update existing rates and update the CSV file
	 * 
	 * @return returns a boolean to the main view to see if the update was
	 *         successful
	 */
	public static boolean staffUpdateRates() {
		Scanner sc = new Scanner(System.in);
		System.out.println("====================");
		Hashtable<String, Double> ratesHT = (Hashtable<String, Double>) PriceManager.getInstance().getRates();
		int index = 0;
		for (String key : ratesHT.keySet()) {
			System.out.println(index + 1 + ". Type :" + key + ", Rate: " + ratesHT.get(key));
			index++;
		}
		System.out.println("====================");
		String choice = "";
		for (;;) {
			System.out.println("Enter Type: ");
			choice = sc.next().toUpperCase();
			if (ratesHT.containsKey(choice))
				break;
			else {
				System.out.println("Invalid option! Try again.");
			}
		}
		System.out.println("Enter rate to change: ");
		double newRate = sc.nextDouble();

		// updates rates dictionary
		ratesHT.replace(choice, ratesHT.get(choice), newRate);

		// updates rates.csv
		return saveRatesCSV(ratesHT);
	}

	/**
	 * The function is to update existing rates and update the CSV file
	 * 
	 * @param takes in a newly updated rates list
	 * @return returns a boolean to see if the CSV file was successfully saved
	 */
	private static boolean saveRatesCSV(Hashtable<String, Double> ratesHT) {
		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter(RATEPATH);
			for (String key : ratesHT.keySet()) {
				StringBuilder sb = new StringBuilder();
				sb.append(key);
				sb.append(',');
				sb.append(ratesHT.get(key));
				sb.append(',');
				sb.append('\n');
				csvWriter.append(sb.toString());
			}
			csvWriter.flush();
			csvWriter.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
