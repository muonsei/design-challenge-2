package model;

import java.util.Calendar;

public class Task extends CalendarNode {
	
	public Task(String name, String fromTime, String toTime, Calendar date) {
		super(name, fromTime, toTime, date);
		done = false;
	}
	
	public Task(String name, String fromTime, String toTime, String date) {
		super(name, fromTime, toTime, date);
		done = false;
	}
	
	public Task(String name, String fromTime, String toTime, Calendar date, boolean done) {
		super(name, fromTime, toTime, date);
		setDone(done);
	}
	
	public Task(String name, String fromTime, String toTime, String date, boolean done) {
		super(name, fromTime, toTime, date);
		setDone(done);
	}
	
	public Task(String name, String fromTime, String toTime, String date, String done) {
		super(name, fromTime, toTime, date);
		if (Integer.parseInt(done) == 0) 
			setDone(false);
		else
			setDone(true);
	}
	
	public Task(String name, String fromTime, String toTime, Calendar date, String done) {
		super(name, fromTime, toTime, date);
		if (Integer.parseInt(done) == 0)
			setDone(false);
		else
			setDone(true);
	}
	
	public void setDone(boolean value) {
		done = value;
	}
	
	public boolean getDone() {
		return done;
	}
	
	private boolean done;
}
