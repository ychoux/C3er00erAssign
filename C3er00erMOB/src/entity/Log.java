package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Log class represents a log entity, the log entity is used to store log
 * files of the admin app
 * 
 * @author JIAYING
 *
 */
public class Log {
	/**
	 * This string is used for the log object to know who created the log entry
	 */
	public String username;
	/**
	 * This string is used for the log object to store the description of what
	 * happen
	 */
	public String description;
	/**
	 * This int is used for the log object to store the loglvl, the loglvl is used
	 * to store the level severity
	 */
	public int loglvl;
	/**
	 * This string is used for the log object to store the date and time of log
	 * occurrence
	 */
	public String datetime;

	/**
	 * This is the default constructor
	 */
	public Log() {

	}

	/**
	 * This constructor is used to create new log entries into the log.csv file
	 * 
	 * @param username    This is parsed in to store the user name for the log
	 *                    object
	 * @param description This is parsed in to store the description for the log
	 *                    object
	 * @param loglvl      This is parsed in to store the log level for the log
	 *                    object
	 */
	public Log(String username, String description, int loglvl) {
		this.username = username;
		this.description = description;
		this.loglvl = loglvl;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		this.datetime = LocalDateTime.now().format(formatter);
	}

	/**
	 * This constructor is used to get the log objects from the log.csv file
	 * 
	 * @param username    This is parsed in to store the user name for the log
	 *                    object
	 * @param description This is parsed in to store the description for the log
	 *                    object
	 * @param loglvl      This is parsed in to store the log level for the log
	 *                    object
	 * @param dt
	 */
	public Log(String username, String description, int loglvl, String dt) {
		this.username = username;
		this.description = description;
		this.loglvl = loglvl;
		this.datetime = dt;
	}
}
