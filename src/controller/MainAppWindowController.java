package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import com.sun.prism.paint.Color;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import javafx.util.converter.DateTimeStringConverter;
import model.CalendarNode;
import model.Event;
import model.NodeManager;
import model.Task;
import view.AllNodesView;
import model.AlertFactory;

public class MainAppWindowController {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane topAnchor;

    @FXML
    private Label appnameLabel;

    @FXML
    private Button todayButton;

    @FXML
    private Label dateLabel;

    @FXML
    private ToggleButton dayToggle;

    @FXML
    private ToggleGroup viewType;

    @FXML
    private ToggleButton agendaToggle;

    @FXML
    private AnchorPane leftAnchor;

    @FXML
    private Button createButton;

    @FXML
    private Label viewLabel;

    @FXML
    private CheckBox eventCheckbox;

    @FXML
    private CheckBox taskCheckbox;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Button prevMonthButton;

    @FXML
    private Button nextMonthButton;

    @FXML
    private Label calendarDateLabel;

    @FXML
    private StackPane anchorStack;

    @FXML
    private AnchorPane createEventAnchor;

    @FXML
    private TextField eventnameField;

    @FXML
    private RadioButton eventRadioButton;

    @FXML
    private ToggleGroup typeRadioGroup;

    @FXML
    private RadioButton taskRadioButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button saveButton;

    @FXML
    private Button discardButton;

    @FXML
    private AnchorPane dayAnchor;

    @FXML
    private TextField toTimeField;

    @FXML
    private TextField fromTimeField;

    @FXML
    private AnchorPane agendaAnchor;
    
    @FXML
    private ScrollPane dayScrollPane;

    @FXML
    private GridPane dayViewTimeGrid;

    @FXML
    private AnchorPane gridAnchor;
    
    @FXML
    private Button viewAllButton;
    
    private ToggleButton[][] days;
	private ToggleGroup group;
	private int yearBound, monthBound, dayBound, yearToday, monthToday;
	private Calendar selectedDate;
	private String[] months =  {"January", "February", "March", "April", 
			"May", "June", "July", "August", "September", 
			"October", "November", "December"};
	private NodeManager nm = new NodeManager();
	private ArrayList<CalendarNode> nodeList = new ArrayList<CalendarNode>();
	private AlertFactory af;
	private AllNodesView anv;
    
    @FXML
    void initialize() {
    	assert topAnchor != null : "fx:id=\"topAnchor\" was not injected: check your FXML file 'appView.fxml'.";
        assert appnameLabel != null : "fx:id=\"appnameLabel\" was not injected: check your FXML file 'appView.fxml'.";
        assert todayButton != null : "fx:id=\"todayButton\" was not injected: check your FXML file 'appView.fxml'.";
        assert dateLabel != null : "fx:id=\"dateLabel\" was not injected: check your FXML file 'appView.fxml'.";
        assert dayToggle != null : "fx:id=\"dayToggle\" was not injected: check your FXML file 'appView.fxml'.";
        assert viewType != null : "fx:id=\"viewType\" was not injected: check your FXML file 'appView.fxml'.";
        assert agendaToggle != null : "fx:id=\"agendaToggle\" was not injected: check your FXML file 'appView.fxml'.";
        assert leftAnchor != null : "fx:id=\"leftAnchor\" was not injected: check your FXML file 'appView.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'appView.fxml'.";
        assert viewLabel != null : "fx:id=\"viewLabel\" was not injected: check your FXML file 'appView.fxml'.";
        assert eventCheckbox != null : "fx:id=\"eventCheckbox\" was not injected: check your FXML file 'appView.fxml'.";
        assert taskCheckbox != null : "fx:id=\"taskCheckbox\" was not injected: check your FXML file 'appView.fxml'.";
        assert calendarGrid != null : "fx:id=\"calendarGrid\" was not injected: check your FXML file 'appView.fxml'.";
        assert prevMonthButton != null : "fx:id=\"prevMonthButton\" was not injected: check your FXML file 'appView.fxml'.";
        assert nextMonthButton != null : "fx:id=\"nextMonthButton\" was not injected: check your FXML file 'appView.fxml'.";
        assert calendarDateLabel != null : "fx:id=\"calendarDateLabel\" was not injected: check your FXML file 'appView.fxml'.";
        assert anchorStack != null : "fx:id=\"anchorStack\" was not injected: check your FXML file 'appView.fxml'.";
        assert createEventAnchor != null : "fx:id=\"createEventAnchor\" was not injected: check your FXML file 'appView.fxml'.";
        assert eventnameField != null : "fx:id=\"eventnameField\" was not injected: check your FXML file 'appView.fxml'.";
        assert eventRadioButton != null : "fx:id=\"eventRadioButton\" was not injected: check your FXML file 'appView.fxml'.";
        assert typeRadioGroup != null : "fx:id=\"typeRadioGroup\" was not injected: check your FXML file 'appView.fxml'.";
        assert taskRadioButton != null : "fx:id=\"taskRadioButton\" was not injected: check your FXML file 'appView.fxml'.";
        assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'appView.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'appView.fxml'.";
        assert discardButton != null : "fx:id=\"discardButton\" was not injected: check your FXML file 'appView.fxml'.";
        assert toTimeField != null : "fx:id=\"toTimeField\" was not injected: check your FXML file 'appView.fxml'.";
        assert fromTimeField != null : "fx:id=\"fromTimeField\" was not injected: check your FXML file 'appView.fxml'.";
        assert dayAnchor != null : "fx:id=\"dayAnchor\" was not injected: check your FXML file 'appView.fxml'.";
        assert dayScrollPane != null : "fx:id=\"dayScrollPane\" was not injected: check your FXML file 'appView.fxml'.";
        assert dayViewTimeGrid != null : "fx:id=\"dayViewTimeGrid\" was not injected: check your FXML file 'appView.fxml'.";
        assert agendaAnchor != null : "fx:id=\"agendaAnchor\" was not injected: check your FXML file 'appView.fxml'.";
        assert gridAnchor != null : "fx:id=\"gridAnchor\" was not injected: check your FXML file 'appView.fxml'.";
        assert viewAllButton != null : "fx:id=\"viewAllButton\" was not injected: check your FXML file 'appView.fxml'.";
     
        days = new ToggleButton [6][7];
        group = new ToggleGroup();
       
        for (int i = 0; i < days.length; i++) {
        	for (int j = 0; j < days[i].length; j++) {
        		Cell cell = new Cell ();
        		cell.setX(i);
        		cell.setY(j);
        		cell.setData(0);
        		
        		days[i][j] = new ToggleButton ();
        		days[i][j].setUserData(cell);
        		days[i][j].setText(" ");
        		days[i][j].setToggleGroup(group);
        		days[i][j].setTextAlignment(null);
        		
    			calendarGrid.add(days[i][j], j, i + 1);
    		}
        }
        
        monthToday = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH);
        yearToday = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);
        initializeCalendar(monthToday, yearToday);
        group.selectedToggleProperty().addListener((ov, oldVal, newVal) -> {
    		getSelectedDate();
        	updateDateLabel();
    		if (dayToggle.isSelected()) {
    			dayButtonAction();
    			checkBoxChecker();
    		} else if (agendaToggle.isSelected()) {
    			agendaButtonAction();
    			checkBoxChecker();
    		}
    	});
        
        todayButtonAction();
        
        try {
        	SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
            toTimeField.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(timeformat), timeformat.parse("00:00")));
            fromTimeField.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(timeformat), timeformat.parse("00:00")));
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        toTimeField.textProperty().addListener((ov, oldVal, newVal) -> {
    		timeFieldAction(toTimeField, newVal);
    	});
        
        fromTimeField.textProperty().addListener((ov, oldVal, newVal) -> {
    		timeFieldAction(fromTimeField, newVal);
    	});
        
        datePicker.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate == null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString == null || dateString.trim().isEmpty())
                    return null;
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
        
        af = new AlertFactory();
        anv = new AllNodesView();
    }

/* Main App Window Buttons */
    
    @FXML
    private void createButtonAction () {
        dayAnchor.setVisible(false);
        dayAnchor.setDisable(true);
        agendaAnchor.setVisible(false);
        agendaAnchor.setDisable(true);
        createEventAnchor.toFront();
        createEventAnchor.setVisible(true);
        createEventAnchor.setDisable(false);
   }
    
    @FXML
    private void viewAllButtonAction () {
    	anv.show();
    }
    
    @FXML
    private void todayButtonAction () {
    	int i, j;
    	monthToday = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH);
        yearToday = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);
        initializeCalendar(monthToday, yearToday);
        for (i = 0; i < days.length; i++)
        	for (j = 0; j < days[i].length; j++)
        		if (days[i][j].getText().equalsIgnoreCase(GregorianCalendar.getInstance().get(GregorianCalendar.DAY_OF_MONTH) + ""))
        			days[i][j].setSelected(true);
        
        if (agendaToggle.isSelected()) {
        	agendaButtonAction();
        } else if (dayToggle.isSelected()) {
        	dayButtonAction();
        }
    }
    
    @FXML
    private void agendaButtonAction () {
    	if (agendaToggle.isSelected()) {
        	createEventAnchor.setVisible(false);
            createEventAnchor.setDisable(true);
            dayAnchor.setVisible(false);
            dayAnchor.setDisable(true);
            agendaAnchor.toFront();
            agendaAnchor.setVisible(true);
            agendaAnchor.setDisable(false);
    	} else {
    		agendaAnchor.setVisible(false);
            agendaAnchor.setDisable(true);
    	}
    }
    
    @FXML
    private void dayButtonAction () {
    	if (dayToggle.isSelected()) {
    		agendaToggle.setSelected(false);
        	createEventAnchor.setVisible(false);
            createEventAnchor.setDisable(true);
            agendaAnchor.setVisible(false);
            agendaAnchor.setDisable(true);
            dayAnchor.toFront();
            dayAnchor.setVisible(true);
            dayAnchor.setDisable(false);
    	} else {
    		dayAnchor.setVisible(false);
    		dayAnchor.setDisable(true);
    	}
    }
    
    @FXML
    private void eventCheckboxAction () {
    	checkBoxChecker();
    }
    
    @FXML
    private void taskCheckboxAction () {
    	checkBoxChecker();
    }
    
    private void checkBoxChecker() {
    	agendaAnchor.getChildren().clear();
    	gridAnchor.getChildren().clear();
    	gridAnchor.getChildren().add(dayViewTimeGrid);
    	//this.selectedDate.add(this.selectedDate.MONTH, 1);
    	if (taskCheckbox.isSelected() && eventCheckbox.isSelected()) {
    		nodeList = nm.getAllNodesToday(this.selectedDate);
    	} else if (taskCheckbox.isSelected()) {
    		nodeList = nm.getAllTasks(this.selectedDate);
    	} else if (eventCheckbox.isSelected()) {
    		nodeList = nm.getAllEvents(this.selectedDate);
    	}
    	
    	if (eventCheckbox.isSelected() || taskCheckbox.isSelected()){
    		ArrayList<Label> labels = new ArrayList<Label>();
    		ArrayList<Button> buttons = new ArrayList<Button>();
    		int taskCount = 0;
    		
        	for(int i=0; i < nodeList.size();i++){
        		/* Agenda View */
        		labels.add(new Label());
        		labels.get(i).setText(nodeList.get(i).getFromTime() + " - " + nodeList.get(i).getToTime() + "   " 
        				+ nodeList.get(i).getName());
        		
        		/* Day View */
        		buttons.add(new Button());
        		buttons.get(i).setText(nodeList.get(i).getName());
        		buttons.get(i).setAlignment(Pos.TOP_LEFT);
        		
        		double size = 30*(Integer.parseInt(nodeList.get(i).getTimeDifference())) - 3.5;
        		buttons.get(i).setPrefSize(258, size);
        		buttons.get(i).setStyle("-fx-background-color:#1155cc; -fx-font-weight:bold;" +
        				"-fx-text-fill:#ffffff;-fx-border-width:1.75px;-fx-font-family:'Century Gothic';");
        		
        		if (nodeList.get(i) instanceof Task) {
        			CalendarNode temp = nodeList.get(i);
        			ArrayList<ButtonType> alertButtonList = new ArrayList<ButtonType>();
        			if (((Task)nodeList.get(i)).getDone()) { // Task is done
        				labels.get(i).setStyle("-fx-text-fill:gray;-fx-font-family:'Century Gothic'");
        				buttons.get(i).setStyle("-fx-background-color:#b1ff8e;-fx-border-width:1.75px;"+
        					"-fx-font-family:'Century Gothic';");
        				//buttons.get(i).setStyle("-fx-background-image:url(\"hehe.jpg\");");
        				buttons.get(i).setOnAction((event) -> {
        					ButtonType yes = new ButtonType("Mark as Not Done");
        					ButtonType delete = new ButtonType("Delete from List");
        					ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        					alertButtonList.add(yes);
        					alertButtonList.add(delete);
        					alertButtonList.add(no);
        					Optional<ButtonType> result = af.confMarkedDone(temp, alertButtonList, true);
        					checkBoxChecker();
        					if (result.get().equals(yes)) {
        					    if (nm.setAsDone(temp, false)) {
        					    	checkBoxChecker();
            					    af.infMark(temp, "marked as not done.");
            					    checkBoxChecker();
        					    }
        					} else if (result.get().equals(delete)) {
        						if (nm.removeNode(temp)) {
        							checkBoxChecker();
            						af.infMark(temp, "removed from the list.");
            						checkBoxChecker();
        						}
        					}
                		});
        			}
        			else { // Task is not done
        				taskCount++; // Count unfinished tasks
        				labels.get(i).setStyle("-fx-text-fill:#38761d;-fx-font-family:'Century Gothic';");
        				buttons.get(i).setStyle("-fx-background-color:#38761d; -fx-font-weight:bold;" +
        						"-fx-text-fill:#ffffff;-fx-border-width:1.75px;"+
        						"-fx-font-family:'Century Gothic';");
        				buttons.get(i).setOnAction((event) -> {
        					ButtonType yes = new ButtonType("Yes");
        					ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        					alertButtonList.add(yes);
        					alertButtonList.add(no);
        					Optional<ButtonType> result = af.confMarkedDone(temp, alertButtonList, false);
        					checkBoxChecker();
        					if (result.get().equals(yes)) {
        					    nm.setAsDone(temp, true);
        					    checkBoxChecker();
        					    af.infMark(temp, "marked as done.");
        					    checkBoxChecker();
        					}
        				});
        			}
        			
        			alertButtonList.clear();
        		}
        			
        		else if (nodeList.get(i) instanceof Event) {
        			labels.get(i).setStyle("-fx-text-fill:#1155cc;-fx-font-family:'Century Gothic';");
        			if (nodeList.get(i).getDate().before(Calendar.getInstance())) {
        				buttons.get(i).setStyle("-fx-background-color:#b2e5ff;-fx-border-width:1.75px;" +
        					"-fx-font-family:'Century Gothic';");
        			}
        		}
        		
        		if (i == 0) {
        			labels.get(i).setLayoutY(20);
        		}
        			
        		else if (i > 0) {
        			labels.get(i).setLayoutY(labels.get(i).getLayoutY() + (i + 1) * 20);
        		}

    			labels.get(i).setLayoutX(20);
    			buttons.get(i).setLayoutX(118);
    			buttons.get(i).setLayoutY(((Integer.parseInt(nodeList.get(i).getFromTime().substring(0, 2)) * 1 +
    					Integer.parseInt(nodeList.get(i).getFromTime().substring(3, 5)) * 2) * 56));
            	//label.setText(label.getText() + nodeList.get(i).getName() + " " + nodeList.get(i).getFromTime() + " " + nodeList.get(i).getToTime() + "\n");
            }
        	
        	if(labels.isEmpty()){
        		Label label = new Label();
        		Button button = new Button();
        		if (eventCheckbox.isSelected() && taskCheckbox.isSelected()) {
        			label.setText("No events or tasks for this day");
        			label.setStyle("-fx-font-family:'Century Gothic';");
        			button.setText("No events or tasks for this day");
        		} else if (eventCheckbox.isSelected()) {
        			label.setText("No events for this day");
        			label.setStyle("-fx-font-family:'Century Gothic';");
        			button.setText("No events for this day");
        		} else if (taskCheckbox.isSelected()) {
        			label.setText("No tasks for this day");
        			label.setStyle("-fx-font-family:'Century Gothic';");
        			button.setText("No tasks for this day");
        		}
        		label.setLayoutX(20);
        		label.setLayoutY(20);
        		labels.add(label);
        		buttons.add(button);
        		buttons.get(0).setLayoutX(118);
        		buttons.get(0).setLayoutY(0);
        		buttons.get(0).setPrefSize(258, 22 * 60 + 30);
        		buttons.get(0).setWrapText(true);
        		buttons.get(0).setStyle("-fx-background-color:transparent;-fx-font-size:50px;" +
        				"-fx-text-alignment:center;-fx-text-fill:gray;-fx-border-width:0px;" +
        				"-fx-font-family:'Century Gothic'");
        	} else {
        		Label taskCountLabel = new Label(taskCount + " unfinished task(s).\n");
            	taskCountLabel.setLayoutX(240);
            	taskCountLabel.setLayoutY((labels.size() + 1) * 20 + 20);
            	taskCountLabel.setStyle("-fx-font-family:'Century Gothic';");
            	labels.add(taskCountLabel);
        	}
        	
        	agendaAnchor.getChildren().addAll(labels);
        	gridAnchor.getChildren().addAll(buttons);
    	}
    }
    
    /* Create Pane Actions */
    
    @FXML
    private void saveButtonAction () {
    	String eventName = eventnameField.getText();
    	eventName = eventName.replaceAll("'", "''");
    	String fromTime = fromTimeField.getText();
    	String toTime = toTimeField.getText();
    	String date = datePicker.getEditor().getText();
    	CalendarNode node = null;
    	
    	try {
    		if(eventName.isEmpty() || date.isEmpty()) {
    			String type = "", element = "";
    			if (eventRadioButton.isSelected())
    				type = "event";
    			else if (taskRadioButton.isSelected())
    				type = "task";
    			if (eventName.isEmpty() && date.isEmpty())
    				element = "name and date";
    			else if (eventName.isEmpty())
    				element = "name";
    			else if (date.isEmpty())
    				element = "date";
    			af.warnIncElements(type, element);
    		}
    		else if(Integer.parseInt(toTime.substring(0,2) + toTime.substring(3, 5))
    				< Integer.parseInt(fromTime.substring(0,2) + fromTime.substring(3, 5))){
    			af.customWarning("Invalid time entered.");			
    		}
    		else if(!nm.getOverlappingEvent(date, toTime, fromTime).isEmpty()){
    			af.warnOverlap(eventName);
    		}
    		else if (eventRadioButton.isSelected()) {
        		nm.addNode(node = new Event(eventName, fromTime, toTime, date));
    			af.infCreateNode(node);
    			discardButtonAction();
    			checkBoxChecker();
        	} else {
        		nm.addNode(node = new Task(eventName, fromTime, toTime, date));
        		af.infCreateNode(node);
    			discardButtonAction();
    			checkBoxChecker();
        	}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	nm.setPastEventsDone(Calendar.getInstance());
    }
    
    @FXML
    private void discardButtonAction () {
    	if (dayToggle.isSelected()) {
    		dayButtonAction();
    	} else if (agendaToggle.isSelected()) {
    		agendaButtonAction();
    	} else {
    		eventnameField.clear();
        	eventRadioButton.setSelected(true);
        	datePicker.getEditor().clear();
        	fromTimeField.setText("00:00");
        	toTimeField.setText("00:00");
        	todayButtonAction();
        	createEventAnchor.setDisable(true);
        	createEventAnchor.setVisible(false);
        	dayAnchor.setDisable(true);
        	dayAnchor.setVisible(false);
        	agendaAnchor.setDisable(true);
        	agendaAnchor.setVisible(false);
    	}
    }
    
    private void initializeCalendar(int month, int year) {
    	activateAllToggleButtons();
    	int nod, som, i, j;
    	
    	for (i = 0; i < days.length; i++) {
        	for (j = 0; j < days[i].length; j++) {
        		days[i][j].setText(" ");
    		}
        }
    	    
    	if (month == 0 && year <= yearBound-10)
    	            prevMonthButton.setDisable(true);
    	if (month == 11 && year >= yearBound+100)
    	            nextMonthButton.setDisable(true);
    	        
    	calendarDateLabel.setText(months[month] + " " + yearToday);
    	
    	Calendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
		monthBound = cal.get(GregorianCalendar.MONTH);
		yearBound = cal.get(GregorianCalendar.YEAR);
		monthToday = monthBound; 
		yearToday = yearBound;
		
		for (i = 1; i <= nod; i++)
        {
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			days[row][column].setText(i + "");
		}
		
		deactivateBlankToggleButtons();
    }
    
    @FXML
    private void prevMonthButtonAction() {
    	deselectDates();
    	if (monthToday == 0){
			monthToday = 11;
			yearToday -= 1;
    	}
		else
			monthToday -= 1;
		
    	initializeCalendar(monthToday, yearToday);
    	selectSelected();
    }
    
    @FXML
    private void nextMonthButtonAction() {
    	deselectDates();
    	if (monthToday == 11){
			monthToday = 0;
			yearToday += 1;
    	}
		else
			monthToday += 1;
		
    	initializeCalendar(monthToday, yearToday);
    	selectSelected();
    }
    
    private Calendar getSelectedDate() {
    	int i, j, selectedDay = 1;
    	for (i = 0; i < days.length; i++)
    		for (j = 0; j < days[i].length; j++)
    			if (days[i][j].isSelected())
    				selectedDay = Integer.parseInt(days[i][j].getText());
    	return new GregorianCalendar(yearToday, monthToday, selectedDay);
    }
    
    private void updateDateLabel() {
    	if(group.getSelectedToggle() != null) {
    		String month = "";
			selectedDate = getSelectedDate();
			if (selectedDate.get(Calendar.MONTH) >= 0)
				month = months[selectedDate.get(Calendar.MONTH)];
			else
				month = months[months.length - 1];
		
			dateLabel.setText(month + " " + selectedDate.get(Calendar.DAY_OF_MONTH) + ", " + selectedDate.get(Calendar.YEAR));
			//updateViews
		}
    }
    
    private void deselectDates() {
    	int i, j;
    	if(group.getSelectedToggle() != null) {
    		for (i = 0; i < days.length; i++)
    			for (j = 0; j < days[i].length; j++)
    				if (days[i][j].isSelected())
    					days[i][j].setSelected(false);
    	}
    }
    
    private void selectSelected() {
    	int i, j;
    	if(group.getSelectedToggle() == null) {
    		if (selectedDate.get(Calendar.YEAR) == yearToday &&
    				selectedDate.get(Calendar.MONTH) == monthToday)
    			for (i = 0; i < days.length; i++)
    				for (j = 0; j < days[i].length; j++)
    					if (days[i][j].getText().equalsIgnoreCase((selectedDate.get(Calendar.DAY_OF_MONTH)) + ""))
    						days[i][j].setSelected(true);
    	}
    }
    
    private void activateAllToggleButtons() {
    	int i, j;
    	for (i = 0; i < days.length; i++) 
    		for (j = 0; j < days[i].length; j++) {
    			days[i][j].setDisable(false);
    		    days[i][j].setVisible(true);
    		}
    }
    
    private void deactivateBlankToggleButtons() {
    	int i, j;
    	for (i = 0; i < days.length; i++) 
    		for (j = 0; j < days[i].length; j++)
    			if (days[i][j].getText().isEmpty() || days[i][j].getText().equalsIgnoreCase(" ")) {
    				days[i][j].setDisable(true);
    		    	days[i][j].setVisible(false);
    			}
    }
    
    private void timeFieldAction(TextField tf, String newVal) {
    	if (newVal.length() == 5) {
    		if (!newVal.contains(":00") || !newVal.contains(":30")) {
        		if (Integer.parseInt(newVal.substring(3)) > 0)
        			tf.setText(newVal.substring(0, 2) + ":30");
        		else
        			tf.setText(newVal.substring(0, 2) + ":00");
        	}
        }
    }
}

class Cell {
	private int x;
	private int y;
	private int data;
	
	public int getData() {
		return data;
	}
	
	public void setData(int data) {
		this.data = data;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
}
