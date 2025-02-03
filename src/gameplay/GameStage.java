/***********************************************************
* Railway Bandit - GameStage Class; CMSC 22 WX-4L (Final Project)
*
* @author Adrianne D. Solis
* @created_date 2024-05-03 3:34 PM
*
***********************************************************/

package gameplay;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import tools.Main;
import tools.Menu;
import tools.SoundController;

public class GameStage {
	
	// game scene
	private Scene scene;
    private Stage stage;
    private GameTimer game;
    private Image bgImg;
    private Pane miscPane;
    private Pane characterPane;
    private Pane p1Pane;
    private Pane p2Pane;
    private Pane powerupPane;
    private StackPane root;
    private ImageView p1Icon;
    private ImageView p2Icon;
    private ImageView powerupTimerFrame;
    private Text powerupTimerTextName;
    private Text powerupTimerTextTime;
    private TextFlow powerupTimerText;
    
    // players
    private Character p1;
    private Character p2;
    
    // sound controller for BGM and SFX
    private SoundController soundController;
    
    // initial pane
    private Pane initialPane;
    private Text initialText;
    private ImageView initialFrame;
    
    // end scene
    private Menu menu;
    private Pane endPane;
    private Scene endScene;
    private ImageView endBoxFrame;
    private Text endText;
    private Text winnerText;
    private StackPane endPanes;
    private ImageView winnerImg;
    private ImageView menuButton;
    private ImageView quitButton;
    
    // fonts
    Font pixelbook80 = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("PixelBook-Regular.ttf"), 80);
    Font pixelbook100 = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("PixelBook-Regular.ttf"), 100);
    
    // window (stage) dimensions
    public static final int WINDOW_HEIGHT = 950;
    public static final int WINDOW_WIDTH = 600;
     
     // y coordinates of 1st and 2nd floor/platforms
     static final double storey1_y = 400;
     static final double storey2_y = 50;
     
     // initial x coordinate of players 1 and 2
     static final double init_x1 = 10;
     static final double init_x2 = 790;
     
     // x coordinates of left and right ladder
     static final double leftLadder_x = 50;
     static final double rightLadder_x = 750;
     
     // left and right boundaries of the game map
     static final double lBound = 0;
     static final double rBound = 820; 
   
    public GameStage(Menu menu, Character p1, Character p2, SoundController soundController) {
    	
    	// [1] initialize game scene
    	
    	// initialize panes and stack panes
    	this.root = new StackPane();
    	this.endPanes = new StackPane();
    	this.characterPane = new Pane();
    	this.p1Pane = new Pane();
    	this.p2Pane = new Pane();
    	this.powerupPane = new Pane();
    	this.miscPane = new Pane();
    
    	// initialize characters
    	this.p1 = p1;
    	this.p2 = p2;
    	
    	// set character image view order to be behind character icons
    	this.p1.sImg.setViewOrder(-1);
    	this.p2.sImg.setViewOrder(-1);
    	
    	// initialize powerup image
    	this.root.getChildren().add(powerupPane);	// add to stack pane
    	
        // set character icons
        this.setCharacterImages();
        this.miscPane.getChildren().addAll(this.p1Icon, this.p2Icon);  // add to miscPane
    	
    	// set background image
        this.bgImg = new Image("game_bg2.png", 950, 600, false, false);
		BackgroundImage myBI= new BackgroundImage(bgImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		this.root.setBackground(new Background(myBI));
		
		// add player images, health bars, and stats to character pane
		this.p1Pane.getChildren().addAll(p1.sImg, p1.getHealthBar());
		this.p2Pane.getChildren().addAll(p2.sImg, p2.getHealthBar());
		this.characterPane.getChildren().addAll(p1.getStatsPane(), p2.getStatsPane());
		
		// add nodes to root 
		this.root.getChildren().addAll(p1Pane, p2Pane, characterPane, miscPane);
		this.miscPane.toFront();
    	
    	// initialize scene and game timer
        this.scene = new Scene(root, WINDOW_HEIGHT, WINDOW_WIDTH);
        this.game = new GameTimer(this, scene, root, p1, p2);
        
        // initialize powerup timer frame
        this.setPowerupTimerFrame();
        
        // [2] initialize initial pane
        this.initialPane = new Pane();
        this.initialText = new Text();
        
        // set font and color
        this.initialText.setFont(Menu.pixelbook25);
        this.initialText.setFill(Color.web("#FCEABC"));		// off-white
        
        this.initialFrame = new ImageView(new Image("endSceneFrame.png", 450, 120, false, false));
        this.initialText.setX(360);
        this.initialText.setY(290);
        
        this.initialFrame.setX(245);
        this.initialFrame.setY(250);
        this.initialPane.getChildren().addAll(initialText, initialFrame);
        this.initialFrame.toBack();
        this.root.getChildren().add(initialPane);
        
        // [3] initialize end scene
        
        // initialize elements
        this.endPane = new Pane();
        this.winnerImg = new ImageView();
        this.endBoxFrame = new ImageView(new Image("endSceneFrame.png", 400, 300, false, false));
        this.endText = new Text("WINS!");
        this.winnerText = new Text();
        this.winnerText.setFont(pixelbook100);
        this.endText.setFont(pixelbook80);
        this.winnerText.setFill(Color.web("#FCEABC"));
        this.endText.setFill(Color.web("#FCEABC"));
        this.menuButton = new ImageView(new Image("ingame-menu.png", 200, 70, false, false));
        this.quitButton = new ImageView(new Image("ingame-quit.png", 200, 70, false, false));
        
        // positions
        this.endBoxFrame.setX(270);
        this.endBoxFrame.setY(110);
        
        this.winnerText.setX(370);
        this.winnerText.setY(250);
        
        this.endText.setX(370);
        this.endText.setY(330);
        
        this.menuButton.setX(150);
        this.menuButton.setY(450);
        
        this.quitButton.setX(560);
        this.quitButton.setY(450);
        
        // set button events
        this.setEndPaneButtonsEvent(menuButton);
        this.setEndPaneButtonsEvent(quitButton);
        
        // set background image
        Image endBGImg = new Image("end_bg.png", 950, 600, false, false);
        BackgroundImage endBG = new BackgroundImage(endBGImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.endPanes.setBackground(new Background(endBG));
        
		// add nodes to endPanes
        this.endPane.getChildren().addAll(endBoxFrame, endText, winnerText, menuButton, quitButton);
        this.endPanes.getChildren().addAll(endPane);
        this.endScene = new Scene(endPanes, WINDOW_HEIGHT, WINDOW_WIDTH);
        
        // audio 
        this.soundController = soundController;
        
        // menu
    	this.menu = menu;
        
    }
    
    // show main game scene
    public void setStage(Stage primaryStage) {	
        this.stage = primaryStage;
        this.stage.setTitle("Railway Bandit - PLAY"); // set stage title
        this.stage.setScene(this.scene);			
        this.stage.show();		
        this.stage.setResizable(false);
        this.setInitialPaneEvent();					 // set event for initial pane (hover and on click)
    }
    
    // hover and on click event for initial pane
    private void setInitialPaneEvent() {	
    	
    	this.initialText.setText(p1.getName().toUpperCase() + " has encountered a\n        bandit, " + p2.getName().toUpperCase() +"!\n  << Click to START >>");
		
    	this.initialText.setOnMouseEntered(e -> {
    		this.initialText.setFill(Color.WHITE);						// change text color when cursor hovers upon image
		});
		        
    	this.initialText.setOnMouseExited(e -> {
    		this.initialText.setFill(Color.web("#FCEABC"));				// replace text color to previous color when cursor is removed
		});
		        
    	this.initialText.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		// on click: game has to start
    		this.soundController.pause(SoundController.Menu);		// stop menu BGM
    		this.soundController.playSFX(SoundController.Click);	// play a click SFX
    		this.soundController.playBGM(SoundController.Game);		// play gameplay BGM
    		this.initialPane.setVisible(false);						// set initial pane to not visible
    		this.game.start();										// start game timer; by this point, players can now move and attack

		});		
	}
    
    // set hover and on click events for end pane buttons
    private void setEndPaneButtonsEvent(ImageView button) {	
		
    	button.setOnMouseEntered(e -> {
    		button.setOpacity(0.6);						// change opacity when cursor hovers upon button
		});
		        
    	button.setOnMouseExited(e -> {
    		button.setOpacity(1);						// change opacity back to 1 when cursor is removed from button
		});
		        
    	button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		if (button == this.menuButton) {		// back to menu button
    			this.soundController.playSFX(SoundController.Click);	// click SFX
    			this.soundController.stopSFX(SoundController.Win);		// stop win SFX
    			this.soundController.playBGM(SoundController.Menu);		// play menu BGM
    			this.stage.setScene(this.menu.getScene());				// show menu scene
    			this.stage.setTitle("Railway Bandit - MENU");			// set new stage title
    		} else if (button == this.quitButton) {
    			System.exit(0);											// quit button--exit application
    		}
		});		
	}
    
    public void endGame(Character winner) {					   // show winning/end scene
		this.stage.setTitle("Railway Bandit - " + winner.getName().toUpperCase() + " WINS!");	// set stage title
		this.soundController.pause(SoundController.Game);	// pause gameplay BGM
		this.soundController.playSFX(SoundController.Win);  // play win SFX
		
		this.winnerText.setText(winner.getName().toUpperCase());	// set text of winning player
		this.stage.setScene(endScene);								// show winning scene
		this.stage.setResizable(false);							
		
		this.winnerImg = new ImageView(new Image(winner.getName() + "Glow.png", 250, 250, false, false)); // initialize image of winning player
        this.winnerImg.setX(135);				// winning player image positioning
        this.winnerImg.setY(150);
        this.endPane.getChildren().add(this.winnerImg); // add winning player image to end pane
    }
    
    // initialize character images to be displayed in the corners
	private void setCharacterImages() {
	
		this.p1Icon = new ImageView(new Image(this.p1.getName() + "IconRight.png", 110, 100, false, false));	// set image
		this.p1Icon.setLayoutX(0);		// positioning
		this.p1Icon.setLayoutY(520);	
		
		this.p2Icon = new ImageView(new Image(this.p2.getName() + "IconLeft.png", 110, 100, false, false));
		this.p2Icon.setLayoutX(840);
		this.p2Icon.setLayoutY(520);
		
		this.p1Icon.setViewOrder(20);
		this.p2Icon.setViewOrder(20);
		
	}
	
	// initialize visible powerup timer 
	void setPowerupTimerFrame() {
		this.powerupTimerFrame = new ImageView(new Image("powerup_timer_frame.png", 430, 150, false, false));
		this.powerupTimerFrame.setX(250);
		this.powerupTimerFrame.setY(240);
		this.powerupPane.getChildren().add(powerupTimerFrame);
		
		this.powerupTimerTextName = new Text();	// contains name of player with powerup
		this.powerupTimerTextTime = new Text();	// contains time left (in seconds)
		this.powerupTimerTextName.setFont(Menu.pixelbook25);
		this.powerupTimerTextTime.setFont(Menu.pixelbook25);
		this.powerupTimerTextName.setFill(Color.web("#F2F2F2"));	// off-white
		this.powerupTimerTextTime.setFill(Color.web("#96DCF8"));	// light blue
		
		this.powerupTimerText = new TextFlow();
		this.powerupTimerText.getChildren().addAll(powerupTimerTextName, powerupTimerTextTime);
		this.powerupTimerText.setTranslateX(360);
		this.powerupTimerText.setTranslateY(300);
		this.powerupPane.getChildren().add(powerupTimerText);
		
		powerupTimerFrame.setVisible(false);
		powerupTimerText.setVisible(false);
	}
	
	// show powerup timer
	void showPowerupTimer(Character player, int timedPowerupNum) {
		String name = player.getName();
		String effect = "";
		switch (timedPowerupNum) {
		case 2:						// feather
			effect = "+3 speed";
			break;
		case 5:						// time machine
			effect = "Freeze Opp";
			break;
		case 7:						// heavy boots
			effect = "-4.5 speed";
			break;
		}
		this.powerupTimerTextName.setText(name.toUpperCase() + " " + effect + ": ");
		this.powerupTimerFrame.setVisible(true);
		this.powerupTimerText.setVisible(true);
	}
	
	// edit time displayed in visible powerup timer
	void editPowerupTimerText(long effectDuration, long time) {
		long timeInSeconds = Math.round((effectDuration/1000) - (time/1000));
		this.powerupTimerTextTime.setText(String.valueOf(timeInSeconds) + "s left");
	}
	
	// set powerup timer to not visible
	void removePowerupTimerFrame() {
		this.powerupTimerFrame.setVisible(false);
		this.powerupTimerText.setVisible(false);
	}
	
	// put attacker to front
	void reorderCharacters(Character attacker) {
		if (attacker == this.p1) {
			this.p1Pane.toFront();
		} else if (attacker == this.p2) {
			this.p2Pane.toFront();
		}
		this.miscPane.toFront();
	}
	
	// add a powerup image to the powerup pane
	void addToPowerupPane(Powerup powerup) {
		this.powerupPane.getChildren().add(powerup.getImg());
	}
	
	// remove a powerup image to the powerup pane
	void removeFromPowerupPane(Powerup powerup) {
		this.powerupPane.getChildren().remove(powerup.getImg());
	}

}
