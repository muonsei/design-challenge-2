package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class CalendarNode {
	private String name, fromTime, toTime;
	private Calendar date;
	
	public CalendarNode(String name, String fromTime, String toTime, Calendar date){
		setName(name);
		setFromTime(fromTime);
		setToTime(toTime);
		setDate(date);
	}
	
	public CalendarNode(String name, String fromTime, String toTime, String date){
		String[] dateArray = date.split("-");
		/* YYYY-MM-DD */
		setName(name); 
		setFromTime(fromTime);
		setToTime(toTime); 
		setDate(new GregorianCalendar(
				Integer.parseInt(dateArray[0]),
				Integer.parseInt(dateArray[1])-1,
				Integer.parseInt(dateArray[2])));
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setFromTime(String time) {
		fromTime = time;
	}
	
	public void setToTime(String time) {
		toTime = time;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFromTime() {
		return fromTime;
	}
	
	public String getToTime() {
		return toTime;
	}
	
	public Calendar getDate() {
		return date;
	}
	
	public String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date.getTime());
	}
	
	/* Returns time difference in minutes */
	public String getTimeDifference() {
		//String hour = "" + (Integer.parseInt(toTime.substring(0, 2)) - Integer.parseInt(fromTime.substring(0, 2)) + ":00");
		//String min = "" + (Integer.parseInt(toTime.substring(3, 5)) - Integer.parseInt(fromTime.substring(3, 5)) + ":00");
		
		String time1 = toTime + ":00";
		String time2 = fromTime + ":00";
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		
		try {
			date1 = format.parse(time1);
			date2 = format.parse(time2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long difference = date1.getTime() - date2.getTime();
		long hours = TimeUnit.MILLISECONDS.toHours(difference);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(difference);
		
		String hour = Long.toString(hours);
		String min = Long.toString(minutes);
		
		String dif = Long.toString(TimeUnit.MILLISECONDS.toMinutes(difference)/30);
		if (Integer.parseInt(hour) < 10)
			hour = "0" + hour;
		if (Integer.parseInt(min) < 10)
			min = "0" + min;
		return dif;
	}
}
