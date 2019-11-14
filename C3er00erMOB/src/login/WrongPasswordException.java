package login;

/**
 * This exception is created to catch if the user enters the correct password
 * @author JIAYING
 *
 */
public class WrongPasswordException extends Exception {
	
	/**
	 * Generated ID for exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the class
	 */
	public WrongPasswordException() {
		this("Password was incorrect!");
	}
	
	/**
	 * @param message	This is the custom message that could be set
	 */
	public WrongPasswordException(String message) {
		super(message);
	}
}