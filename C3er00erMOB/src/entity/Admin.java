package entity;

public class Admin {
	public String username;
	public String password;
	public String salt;
	public int AccessLevel;
	
	public Admin() {
		
	}
	public Admin(String username, String password,String salt, int accesslevel) {
		this.username=username;
		this.password=password;
		this.salt=salt;
		this.AccessLevel=accesslevel;
	}
	
}
