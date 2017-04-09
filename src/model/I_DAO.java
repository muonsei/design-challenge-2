package model;

import java.util.ArrayList;
import java.util.Calendar;
import model.CalendarNode;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface I_DAO {
	/* Creation Methods */
	boolean addNode(CalendarNode node);

	/* Retrieval Methods */
	ArrayList<CalendarNode> getAllNodes();
	ArrayList<CalendarNode> getAllNodesToday(Calendar date);
	ArrayList<CalendarNode> getAllEvents(Calendar date);
	ArrayList<CalendarNode> getAllTasks(Calendar date);
	ArrayList<CalendarNode> getOverlappingEvent(String date, String toTime, String fromTime);

	/* Update Methods */
	boolean setAsDone(CalendarNode node, boolean done);
	boolean setPastEventsDone(Calendar date);
	boolean setCurrentEventsDone(Calendar date);

	/* Deletion Methods */
	boolean removeNode(CalendarNode node);

	/* Converters */
	CalendarNode toNode(ResultSet rs) throws SQLException;
}
