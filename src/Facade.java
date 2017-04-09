import javafx.application.Application;
import model.DBConnection;
import view.MainAppWindowView;

public class Facade {
	
	public Facade () {
		dbc = new DBConnection();
	}
	
	public void start() {
		dbc.getConnection();
	    Application.launch(MainAppWindowView.class);
	}
	
	private DBConnection dbc;
}