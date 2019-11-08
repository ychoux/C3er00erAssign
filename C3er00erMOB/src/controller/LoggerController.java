package controller;
import java.io.FileWriter;
import java.io.IOException;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


import entity.Admin;
import entity.Log;
import login.Logger;

public class LoggerController {

	private static LoggerController INSTANCE = new LoggerController();

	private static String LOGFILE="src/data/log.csv";


	private LoggerController() {

	}

	void LogNormalEntry(String username,String description, int logLvl) {
		Log normLog = new Log(username,description,0);
		this.UpdateLogCSV(normLog);
	}

	void LogSecurityEntry(String username,String description, Logger logLvl) {
		Log secLog = new Log(username,description,0);
		this.UpdateLogCSV(secLog);
	}

	void LogChangeEntry(String username,String description, Logger logLvl) {
		Log changeLog = new Log(username,description,0);
		this.UpdateLogCSV(changeLog);
	}

	void LogErrorEntry(String username,String description, Logger logLvl) {
		Log errLog = new Log(username,description,0);
		this.UpdateLogCSV(errLog);
	}

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
					tmpLog=new Log(log[0],log[1],Integer.parseInt(log[2]));
					logList.add(tmpLog);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return logList;
	}

	public List<Log> getLogList(int logLvl){
		return null;
	}
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
			csvWriter.append("\n");

			for (Log l : logList) {
				StringBuilder sb = new StringBuilder();
				sb.append(l.username);
				sb.append(',');
				sb.append(l.description);
				sb.append(',');
				sb.append(Integer.toString(l.loglvl));
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


	protected static String GetServName(int logLvl) {
		switch (logLvl) {
		case 0:
			return "Normal";
		case 1:
			return "Access";
		case 2:
			return "Change";
		case 3:
			return "Error";
		default:
			return "-";
		}
	}
}
