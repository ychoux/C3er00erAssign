package controller;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import entity.Log;

public class LoggerController {

	/**
	 * The LogController object instance, used as a singleton
	 */
	private static LoggerController INSTANCE = new LoggerController();

	/**
	 * The path to the CSV file that stores all the admin users
	 */
	private static String LOGFILE="src/data/log.csv";

	private LoggerController() {

	}
	
	/**
	 * The function to get the instance of LoggerController object
	 * @return 	a LoggerController object
	 */
	public static LoggerController getInstance() {
        return INSTANCE;
    }

	/**
	 * The function is used to generate a normal log entry
	 * @param username		User that triggered this log event
	 * @param description 	Description of what happen
	 */
	public void LogNormalEntry(String username,String description) {
		Log normLog = new Log(username,description,0);
		this.UpdateLogCSV(normLog);
	}

	/**
	 * The function is used to generate a security log entry
	 * @param username		User that triggered this log event
	 * @param description 	Description of what happen
	 */
	public void LogSecurityEntry(String username,String description) {
		Log secLog = new Log(username,description,1);
		this.UpdateLogCSV(secLog);
	}

	/**
	 * The function is used to generate a change log entry
	 * @param username		User that triggered this log event
	 * @param description 	Description of what happen
	 */
	public void LogChangeEntry(String username,String description) {
		Log changeLog = new Log(username,description,2);
		this.UpdateLogCSV(changeLog);
	}

	/**
	 * The function is used to generate a error log entry
	 * @param username		User that triggered this log event
	 * @param description 	Description of what happen
	 */
	public void LogErrorEntry(String username,String description) {
		Log errLog = new Log(username,description,3);
		this.UpdateLogCSV(errLog);
	}

	/**
	 * The function is get all the logs that are stored in log.csv
	 * @return A list of log entries from log.csv
	 */
	public List<Log> getLogList(){
		List<Log> logList = new ArrayList<>();
		BufferedReader br = null;
		String line = "";
		Log tmpLog;
		try {
			br = new BufferedReader(new FileReader(LOGFILE));
			while ((line = br.readLine()) != null) {
				String[] log = line.split(",");
				if(!log[0].equals("username")) {
					tmpLog=new Log(log[0],log[1],Integer.parseInt(log[2]),log[3]);
					logList.add(tmpLog);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return logList;
	}
	
	/**
	 * The function acts as a search function to help filter wanted log entries
	 * @param keyword 	A keyword that is used to filter the log entries
	 * @return returns 	A list of filtered log entries from log.csv
	 */
	public List<Log> getLogList(String keyword){
		List<Log> logList = this.getLogList();
		List<Log> fLogList = new ArrayList<>();
		for(Log l : logList) {
			if(Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(l.username).find()
					|| Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(l.description).find()
					|| l.datetime.contains(keyword) 
					|| Integer.toString(l.loglvl).contains(keyword)) {
				fLogList.add(l);
			}
		}
				
		return fLogList;
	}
	
	/**
	 * The function helps update the log.csv file with a new log entry
	 * @param newlog 	A new log entry
	 */
	public void UpdateLogCSV(Log newLog) {
		List<Log> logList = this.getLogList();
		logList.add(newLog);
		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter(LOGFILE);
			csvWriter.append("username");
			csvWriter.append(",");
			csvWriter.append("description");
			csvWriter.append(",");
			csvWriter.append("loglevel");
			csvWriter.append(",");
			csvWriter.append("datetime");
			csvWriter.append("\n");

			
			for (Log l : logList) {
				StringBuilder sb = new StringBuilder();
				sb.append(l.username);
				sb.append(',');
				sb.append(l.description);
				sb.append(',');
				sb.append(Integer.toString(l.loglvl));
				sb.append(',');
				sb.append(l.datetime);
				sb.append(',');
				sb.append('\n');
				csvWriter.append(sb.toString());
			}

			csvWriter.flush();
			csvWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
