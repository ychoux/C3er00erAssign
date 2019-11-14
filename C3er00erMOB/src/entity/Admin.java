package entity;

/**
 * The Admin class represents a admin user entity
 * @author JIAYING
 *
 */
public class Admin {
	
	/**
	 * This string is used for the admin object to store the username
	 */
	public String username;
	/**
	 * This string is used for the admin object to store the password, the password is not stored as plain text but a hashed string value
	 */
	public String password;
	/**
	 * This string is used for the admin object to store the salt, the salt is used to help with the hashing process of the password
	 */
	public String salt;
	/**
	 * This int is used for the admin object to store the access level
	 */
	public int AccessLevel;
	
	/**
	 * This is a default constructor for this file
	 */
	public Admin() {
		
	}
	/**
	 * This is used to create a new admin object
	 * @param username		This is parsed in to set the username
	 * @param password		This is parsed in to set the password
	 * @param salt			This is parsed in to set the salt
	 * @param accesslevel	This is parsed in to set the access level
	 */
	public Admin(String username, String password,String salt, int accesslevel) {
		this.username=username;
		this.password=password;
		this.salt=salt;
		this.AccessLevel=accesslevel;
	}
	
}
