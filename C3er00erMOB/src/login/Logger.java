package login;


public enum Logger {
	NORMAL(0),
	SECURITY(1),
	CHANGE(2),
	ERROR(3);

	private final int logLvl;

	Logger(int logLvl){
		this.logLvl = logLvl;
	}

	int getValue() {
		return logLvl;
	}
}
