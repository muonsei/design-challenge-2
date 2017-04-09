package model;

import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;

public class AlertFactory implements I_AlertFactory {

	@Override
	public CustomAlert createConfirmationAlert() {
		return new CustomAlert(AlertType.CONFIRMATION);
	}

	@Override
	public CustomAlert createErrorAlert() {
		return new CustomAlert(AlertType.ERROR);
	}

	@Override
	public CustomAlert createInformationAlert() {
		return new CustomAlert(AlertType.INFORMATION);
	}

	@Override
	public CustomAlert createWarningAlert() {
		return new CustomAlert(AlertType.WARNING);
	}
	
	public void customWarning(String message) {
		CustomAlert alert = createWarningAlert();
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public Optional<ButtonType> confMarkedDone(CalendarNode node, 
			ArrayList<ButtonType> buttons, Boolean done) {
		CustomAlert alert = createConfirmationAlert();
		String message = "";
		alert.getButtonTypes().setAll(buttons);
		if (done)
			message += node.getName() + " is already marked done.";
		else
			message += "Mark " + node.getName() + " as done?";
		alert.setContentText(message);
		return alert.showAndWait();
	}
	
	public void infMark(CalendarNode node, String happened) {
		CustomAlert alert = createInformationAlert();
		String message = node.getName() + " has been successfully " + happened;
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void warnIncElements(String type, String element) {
		String message = "There was no " + element + " specified for this " + type + ".";
		customWarning(message);
	}
	
	public void warnOverlap(String name) {
		String message = name + " cannot be added, overlapping with an existing event.";
		customWarning(message);
	}
	
	public void infCreateNode(CalendarNode node) {
		String message = node.getName() + " on " 
			+ node.getDateString() + " from " 
			+ node.getFromTime() + " to "
			+ node.getToTime() 
			+ " has been added to the list.";
		if (node instanceof Event) {
			message = "Event named " + message;
		} else if (node instanceof Task) {
			message = "Task named " + message;
		}
		
		CustomAlert alert = createInformationAlert();
		alert.setContentText(message);
		alert.showAndWait();
	}
}
