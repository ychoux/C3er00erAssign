package login;

/**
 * This is a enum class to house different types of access level
 * @author JIAYING
 *
 */
public enum AccessLevel {
			NOACCESS(0),
			ADMIN(1),
			SUPERADMIN(2);
	
	/**
	 * An integer variable for access level of the user
	 */
	private final int level;
	
	/**
	 * This function is used to set the access level of the user when called
	 * @param level	Used to set the access level
	 */
	AccessLevel(int level){
		this.level = level;
	}
	
	/**
	 * This function returns the access level of the user when called
	 * @return	The integer equivalent of the access level for the user
	 */
	int getValue() {
		return level;
	}
}
