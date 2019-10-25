package entity;

public class Admin {
	public String username;
	public String password;
	public int AccessLevel;
	
	public Admin(String username) {
		this.username=username;
	}
	public Admin(String username, String password, int accesslevel) {
		this.username=username;
		this.password=password;
		this.AccessLevel=accesslevel;
	}
	
}
