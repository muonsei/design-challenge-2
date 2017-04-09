package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class NodeManager implements I_DAO {
	private DBConnection connection;
	
	public NodeManager() {
		connection = new DBConnection();
		connection.getConnection();
		
		setPastEventsDone(Calendar.getInstance());
		
		Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (Calendar.getInstance().get(Calendar.MINUTE) == 30 ||
                		Calendar.getInstance().get(Calendar.MINUTE) == 00) {
                	realTimeCheck();
                	timer.cancel();
                	timer.purge();
                }
            }
        }, 0, 60 * 1000); /* # of seconds * 1000 */
    }
	
	public ArrayList<CalendarNode> getAllNodes() {
		ArrayList<CalendarNode> nodeList = new ArrayList<CalendarNode>();
		
		try {
			ResultSet rs;
			String query = "SELECT * FROM node ORDER BY date, fromtime, totime"; //NOTE CHECK DATABASE NAME
			
			Statement stment = connection.getConnection().createStatement();
			rs = stment.executeQuery(query);
			
			while(rs.next()) {
				// getString params == column names
				CalendarNode node = toNode(rs);
				
				if (node instanceof Task) {
					switch (rs.getString("done")) {
						case "0": ((Task)node).setDone(false); break;
						case "1": ((Task)node).setDone(true); break;
					}
				}
					
				nodeList.add(node); // add node to list
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}
		return nodeList;
	}
	
	/* Gets all NODES happening on specific date; SQL date format: YYYY-MM-DD */
	public ArrayList<CalendarNode> getAllNodesToday(Calendar date) {
		ArrayList<CalendarNode> nodeList = new ArrayList<CalendarNode>();
		
		String dateToSearch = convertDateToString(date);
		try {
			ResultSet rs;
			String query = "SELECT * FROM node WHERE date = '" + dateToSearch + "' " + 
				"ORDER BY date, fromtime, totime"; //NOTE CHECK DATABASE NAME
			
			Statement stment = connection.getConnection().createStatement();
			rs = stment.executeQuery(query);
			
			while(rs.next()) {
				// getString params == column names
				CalendarNode node = toNode(rs);
				if (node instanceof Task) {
					switch (rs.getString("done")) {
						case "0": ((Task)node).setDone(false); break;
						case "1": ((Task)node).setDone(true); break;
					}
				}
				
				nodeList.add(node); // add node to list
			}
 
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return nodeList;
	}
	
	public boolean setPastEventsDone(Calendar date) {
		Statement statement;
		String dateToSearch = convertDateToString(date);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String timeToSearch = sdf.format(date.getTime());
		
		try {
			String query = "UPDATE node SET done = 1"
					+ " WHERE type LIKE 'event' AND (date < '"
					+ dateToSearch + "' OR (date = '"
					+ dateToSearch + "' AND totime <= '"
					+ timeToSearch + "'))";
			
			statement = connection.getConnection().createStatement();
			statement.executeUpdate(query);
			return true;
 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean setCurrentEventsDone(Calendar date) {
		Statement statement;
		String dateToSearch = convertDateToString(date);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String timeToSearch = sdf.format(date.getTime());
		
		try {
			String query = "UPDATE node SET done = 1"
					+ " WHERE type LIKE 'event' AND date = '"
					+ dateToSearch + "' AND totime = '"
					+ timeToSearch + ":00'";
			
			statement = connection.getConnection().createStatement();
			statement.executeUpdate(query);
			return true;
 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/* Gets all EVENTS happening on specific date; SQL date format: YYYY-MM-DD */
	public ArrayList<CalendarNode> getAllEvents(Calendar date) {
		ArrayList<CalendarNode> nodeList = new ArrayList<CalendarNode>();
		Calendar date2 = date;

		String dateToSearch = convertDateToString(date2);
		try {
			ResultSet rs;
			String query = "SELECT * FROM node "
					+ "WHERE date LIKE '" + dateToSearch + "' AND " 
					+ "type LIKE 'event' ORDER BY date, fromtime, totime"; //NOTE CHECK DATABASE NAME
			
			Statement stment = connection.getConnection().createStatement();
			rs = stment.executeQuery(query);
			while(rs.next()) {
				// getString params == column names
				CalendarNode node = toNode(rs);
				nodeList.add(node); // add node to list
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}
		return nodeList;
	}
	
	/* Gets all TASKS happening on specific date, both DONE and NOT DONE; SQL date format: YYYY-MM-DD */
	public ArrayList<CalendarNode> getAllTasks(Calendar date) {
		ArrayList<CalendarNode> nodeList = new ArrayList<CalendarNode>();
		String dateToSearch = convertDateToString(date);
		
		try {
			ResultSet rs;
			String query = "SELECT * FROM node "
					+ "WHERE date = '" + dateToSearch + "' AND " 
					+ "type LIKE 'task' ORDER BY date, fromtime, totime"; //NOTE CHECK DATABASE NAME
			
			Statement stment = connection.getConnection().createStatement();
			rs = stment.executeQuery(query);
			
			while(rs.next()) {
				// getString params == column names
				CalendarNode node = toNode(rs);
				nodeList.add(node); // add node to list
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}
		return nodeList;
	}
	
	/* Gets all TASKS happening on specific date ONLY NOT DONE; SQL date format: YYYY-MM-DD */
	public ArrayList<CalendarNode> getAllUnfinishedTasks(Calendar date) {
		ArrayList<CalendarNode> nodeList = new ArrayList<CalendarNode>();
		date.add(Calendar.MONTH, 1);
		String dateToSearch = convertDateToString(date);
		
		try {
			ResultSet rs;
			String query = "SELECT * FROM node "
					+ "WHERE date = '" + dateToSearch + "' AND " 
					+ "type LIKE 'task' AND done = 0 ORDER BY date, fromtime, totime"; //NOTE CHECK DATABASE NAME
			
			Statement stment = connection.getConnection().createStatement();
			rs = stment.executeQuery(query);
			
			while(rs.next()) {
				// getString params == column names
				CalendarNode node = toNode(rs);
				nodeList.add(node); // add node to list
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}
		return nodeList;
	}
	
	public ArrayList<CalendarNode> getOverlappingEvent(String date, String toTime, String fromTime){
		ArrayList<CalendarNode> nodeList = new ArrayList<CalendarNode>();
		
		//DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		//Date date = sdf.parse(toTime);
		
		try{
			ResultSet rs;
			/*String query = "SELECT * FROM node "
					+ "WHERE date = '" + date + "' AND "
					+ " (totime = '" + toTime + "' OR fromtime = '" + fromTime +"') "
					+ "ORDER BY date, fromtime, totime";*/
			String query = "SELECT * FROM node "
					+ "WHERE date = '" + date + "' AND ('" + fromTime + "' <= toTime) AND ('" + toTime + "' >= fromTime)";
			/*WHERE   (m2.meetingStart BETWEEN m1.meetingStart AND m1.meetingEnd
        	OR m2.meetingEnd BETWEEN m1.meetingStart AND m1.meetingEnd)
        	AND m2.meetingID > m1.meetingID*/
			
			Statement stment = connection.getConnection().createStatement();
			rs = stment.executeQuery(query);
			
			while(rs.next()){
				CalendarNode node = toNode(rs);
				nodeList.add(node);
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}
		return nodeList;
	}
	public boolean addNode(CalendarNode node) {
		Statement statement;
		String type = "";
		int doneValue = 0;
		
		if (node instanceof Event) {
			type = "event";
		}
			
		else if (node instanceof Task) {
			type = "task";
			if (((Task)node).getDone())
				doneValue = 1; 
		}
		
		String dateToSearch = convertDateToString(node.getDate());
		
		String query = "INSERT INTO node(type, name, date, fromtime, totime, done) VALUES ('"
				+ type + "', '"
				+ node.getName() + "', '"
				+ dateToSearch + "', '"
				+ node.getFromTime() + "', '"
				+ node.getToTime() + "', '"
				+ doneValue + "')";
		
		try {
			statement = connection.getConnection().createStatement();
			statement.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean setAsDone(CalendarNode node, boolean done) {
		Statement statement;
		String type = "";
		int doneValue = 0, setDone = 0;
		
		if (done)
			setDone = 1;
		
		if (node instanceof Event) {
			type = "event";
		}
			
		else if (node instanceof Task) {
			type = "task";
			if (((Task)node).getDone())
				doneValue = 1; 
		}
		
		String dateToSearch = convertDateToString(node.getDate());
		
		String query = "UPDATE node SET done = " + setDone 
				+ " WHERE type LIKE '"
				+ type + "' AND name LIKE '"
				+ node.getName() + "' AND date = '"
				+ dateToSearch + "'AND fromtime = '"
				+ node.getFromTime() + "'AND totime = '"
				+ node.getToTime() + "'AND done = "
				+ doneValue;
		
		try {
			statement = connection.getConnection().createStatement();
			statement.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean removeNode(CalendarNode node) {
		Statement statement;
		String type = "";
		int doneValue = 0;
		
		if (node instanceof Event) {
			type = "event";
		}
			
		else if (node instanceof Task) {
			type = "task";
			if (((Task)node).getDone())
				doneValue = 1; 
		}
		
		String query = "DELETE from node WHERE " +
			"type LIKE '" + type                             + "' AND " +
			"name LIKE '" + node.getName()                   + "' AND " +
			"date = '" + convertDateToString(node.getDate()) + "' AND " +
			"fromtime = '" + node.getFromTime()              + "' AND " +
			"totime = '" + node.getToTime()                  + "' AND " +
			"done = " + doneValue;
		
		try {
			statement = connection.getConnection().createStatement();
			statement.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public CalendarNode toNode(ResultSet rs) throws SQLException{
		CalendarNode node = null;
		switch (rs.getString("type")) {
			case "event": node = new Event(rs.getString("name"),
					rs.getString("fromtime").substring(0, 5),
					rs.getString("totime").substring(0, 5),
					rs.getString("date")); //insert constructor
				break;
			case "task": node = new Task(rs.getString("name"),
					rs.getString("fromtime").substring(0, 5),
					rs.getString("totime").substring(0, 5),
					rs.getString("date"),
					rs.getString("done")); //insert constructor
				break;
		}
		
		return node;
	}
	
	public String convertDateToString(Calendar date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date.getTime());
	}
	
	public void realTimeCheck() {
		Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	setCurrentEventsDone(Calendar.getInstance());
            }
        }, 0, 30 * 60 * 1000);
	}
}