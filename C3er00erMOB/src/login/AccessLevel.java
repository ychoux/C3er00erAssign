package login;

public enum AccessLevel {
			NOACCESS(0),
			ADMIN(1),
			SUPERADMIN(2);
	
	private final int level;
	
	AccessLevel(int level){
		this.level = level;
	}
	
	int getValue() {
		return level;
	}
}
