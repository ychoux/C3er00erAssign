package login;


/**
 * This is a enumeration class to house different types of log severity
 * @author JIAYING
 *
 */
public enum Logger {
	NORMAL(0),
	SECURITY(1),
	CHANGE(2),
	ERROR(3);

	/**
	 * An integer variable for log level of the log entry
	 */
	private final int logLvl;

	/**
	 * This function is used to set the log level for the log entry
	 * @param level	Used to set the access level
	 */
	Logger(int logLvl){
		this.logLvl = logLvl;
	}

	/**
	 * @return	This returns the integer equivalent of the log level
	 */
	int getValue() {
		return logLvl;
	}
}
