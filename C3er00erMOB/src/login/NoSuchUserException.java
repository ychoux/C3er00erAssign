package login;

/**
 * This is an exception class that is made to check if a admin user exist
 * 
 * @author JIAYING
 *
 */
public class NoSuchUserException extends Exception {

	/**
	 * Generated ID for exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the class
	 */
	public NoSuchUserException() {
		this("No such user was found.");
	}

	/**
	 * @param message This is the custom message that could be set
	 */
	public NoSuchUserException(String message) {
		super(message);
	}
}
