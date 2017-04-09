package model;

import java.util.Calendar;

public class Event extends CalendarNode {
	
	public Event(String name, String fromTime, String toTime, Calendar date) {
		super(name, fromTime, toTime, date);
	}
	
	public Event(String name, String fromTime, String toTime, String date) {
		super(name, fromTime, toTime, date);
	}
}
