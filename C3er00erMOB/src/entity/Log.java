package entity;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Log {
	public String username;
	public String description;
	public int loglvl;
	public String datetime;
	
	public Log() {
		
	}
	public Log(String username, String description, int loglvl ){
		this.username=username;
		this.description=description;
		this.loglvl=loglvl;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		this.datetime = LocalDateTime.now().format(formatter);
	}
	public Log(String username, String description, int loglvl, String dt) {
		this.username=username;
		this.description=description;
		this.loglvl=loglvl;
		this.datetime = dt;
	}
}
