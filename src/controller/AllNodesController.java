package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import model.NodeManager;
import model.Task;
import model.CalendarNode;
import model.Event;

public class AllNodesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane taskAnchor;

    @FXML
    private AnchorPane eventAnchor;
    
    @FXML
    private ScrollPane taskScroll;
    
    @FXML
    private ScrollPane eventScroll;
    
    private NodeManager nm = new NodeManager();
    private ArrayList<CalendarNode> nodeList;
    private ArrayList<Event> eventList;
    private ArrayList<Task> taskList;

    @FXML
    void initialize() {
    	
    	eventAnchor.getChildren().clear();
        assert taskAnchor != null : "fx:id=\"taskAnchor\" was not injected: check your FXML file 'allNodesView.fxml'.";
        assert eventAnchor != null : "fx:id=\"eventAnchor\" was not injected: check your FXML file 'allNodesView.fxml'.";
        assert taskScroll != null : "fx:id=\"taskScroll\" was not injected: check your FXML file 'allNodesView.fxml'.";
        assert eventScroll != null : "fx:id=\"eventScroll\" was not injected: check your FXML file 'allNodesView.fxml'.";
        
        refreshNodes();
        getTaskCheckboxes();
        getEventLabels();
    }
    
    private void refreshNodes() {
    	nodeList = nm.getAllNodes();
    	eventList = new ArrayList<Event>();
    	taskList = new ArrayList<Task>();
        
        if (!nodeList.isEmpty()) {
        	for (int i = 0; i < nodeList.size(); i++) {
            	if (nodeList.get(i) instanceof Task) {
            		taskList.add((Task)nodeList.get(i));
            	} else if (nodeList.get(i) instanceof Event) {
            		eventList.add((Event)nodeList.get(i));
            	}
            }
        }
    }
    
    private void getTaskCheckboxes() {
    	ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
    	int taskCount = 0;
    	taskAnchor.getChildren().clear();
    	if (!taskList.isEmpty()) {
    		for (int i = 0; i < taskList.size(); i++) {
    			checkBoxes.add(new CheckBox(detailsSummary(taskList.get(i))));
    			checkBoxes.get(i).setStyle("-fx-text-fill:#fafafa;-fx-font-family:'Century Gothic';-fx-font-size:17px;");
    			if (taskList.get(i).getDone())
    				checkBoxes.get(i).setSelected(true);
    			else
    				taskCount++;	
    			addListener(checkBoxes.get(i), taskList.get(i));
    			checkBoxes.get(i).setLayoutX(20);
    			if (i == 0) {
    				checkBoxes.get(i).setLayoutY(25);
        		} else if (i > 0) {
        			checkBoxes.get(i).setLayoutY(checkBoxes.get(i).getLayoutY() + (i + 1) * 25);
        		}
    		}
    	} 
    	
    	if(!checkBoxes.isEmpty()) {
    		Label taskCountLabel = new Label(taskCount + " unfinished task(s).\n");
        	taskCountLabel.setLayoutX(20);
        	taskCountLabel.setLayoutY((checkBoxes.size() + 1) * 25 + 20);
        	taskCountLabel.setStyle("-fx-text-fill:#fafafa;-fx-font-family:'Century Gothic';-fx-font-size:17px;");
        	taskAnchor.getChildren().add(taskCountLabel);
    	} else {
    		Label emptyLabel = new Label("No tasks found in database.");
    		emptyLabel.setLayoutX(20);
    		emptyLabel.setLayoutY((checkBoxes.size() + 1) * 25 + 20);
    		emptyLabel.setStyle("-fx-text-fill:#fafafa;-fx-font-family:'Century Gothic';-fx-font-size:17px;");
        	taskAnchor.getChildren().add(emptyLabel);
    	}
    	taskAnchor.getChildren().addAll(checkBoxes);
    }
    
    private void getEventLabels() {
    	ArrayList<Label> labels = new ArrayList<Label>();
    	if (!eventList.isEmpty()) {
    		for (int i = 0; i < eventList.size(); i++) {
    			labels.add(new Label(detailsSummary(eventList.get(i))));
    			labels.get(i).setStyle("-fx-text-fill:#fafafa;-fx-font-family:'Century Gothic';-fx-font-size:17px;");
    			labels.get(i).setLayoutX(20);
    			if (i == 0) {
    				labels.get(i).setLayoutY(25);
        		} else if (i > 0) {
        			labels.get(i).setLayoutY(labels.get(i).getLayoutY() + (i + 1) * 25);
        		}
    		}
    	} else {
    		Label emptyLabel = new Label("No tasks found in database.");
    		emptyLabel.setLayoutX(20);
    		emptyLabel.setLayoutY(20);
    		emptyLabel.setStyle("-fx-text-fill:#fafafa;-fx-font-family:'Century Gothic';-fx-font-size:17px;");
        	labels.add(emptyLabel);
    	}
    	
    	eventAnchor.getChildren().addAll(labels);
    }
    
    private String detailsSummary(CalendarNode node) {
    	return node.getDateString() + "   " + node.getFromTime() + " - " +
    		node.getToTime() + "   " + node.getName();
    }
    
    private void addListener(CheckBox cb, Task t) {
    	cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    	
		    	if (newValue) {
		    		nm.setAsDone(t, true);
		    		refreshNodes();
		    		getTaskCheckboxes();
		    	} else {
		    		nm.setAsDone(t, false);
		    		refreshNodes();
		    		getTaskCheckboxes();
		    	}
		    }
		});
    }
}
