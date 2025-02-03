/***********************************************************
* Railway Bandit - Main Class; CMSC 22 WX-4L (Final Project)
*
* @author Adrianne D. Solis
* @created_date 2024-05-02 6:40 PM
*
***********************************************************/

package tools;

import javafx.stage.Stage;
import javafx.application.Application;

public class Main extends Application {

	public static void main(String[] args)
		launch(args);
	}

	public void start(Stage stage) {
	//	 instantiate a menu
		Menu menu = new Menu();
		menu.setStage(stage);
	}
	
}
