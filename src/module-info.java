module RailwayBandit {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.desktop;
	requires javafx.media;
	
	opens tools to javafx.graphics, javafx.fxml;
}
