package entity;
public class Log {
	public String username;
	public String description;
	public int loglvl;
	
	public Log() {
		
	}
	public Log(String username, String description, int loglvl) {
		this.username=username;
		this.description=description;
		this.loglvl=loglvl;
	}
}
