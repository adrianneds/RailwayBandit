/***********************************************************
* Railway Bandit - CharacterSelectionScreen Class; CMSC 22 WX-4L (Final Project)
*
* @author Adrianne D. Solis
* @created_date 2024-05-08 7:36 PM
*
***********************************************************/

package tools;

import gameplay.Character;
import gameplay.GameStage;
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
import javafx.stage.Stage;

public class CharacterSelectionScreen {
	
	// used to access menu scene
	private Menu menu;
	
	// stage, scene, stack pane, and pane
	private Stage stage;
	private Scene scene;
	private StackPane root;
	private Pane selectPane;
	
	// stores information about players' character selections
	private Character player1Character;
	private Character player2Character;
	int p1CharIndex;	// note: each character has an assigned index; 0 - betty, 1 - oliver, 2 - maggie, 3 - miles
	int p2CharIndex;
	
	// stores info of the four characters 
	private Text[] cNames = new Text[4];					// character names
	private Text[][] cStats = new Text[4][4];				// character stats (text)
	private float cStatsArray[][] = new float[4][4];		// character stats (float -- to be passed on to game stage)
	private ImageView[] characImages = new ImageView[4];	// character images
	
	private int currentVisibleIndex;	// stores index of character currently selected/"clicked"
	private Pane onClickSelectPane;		// pane displaying stats and image of currently selected character
	
	// character tiles that user can click
	private ImageView bettyTile;
	private ImageView oliverTile;
	private ImageView maggieTile;
	private ImageView milesTile;
	
	// text for error messages
	private Text errorText;
	private Text errorText2;
	
	// sound controller for BGM and SFX
	private SoundController soundController;
	
	// fonts
	private Font pixelbook21 = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("PixelBook-Regular.ttf"), 21);
	private Font pixelbook46 = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("PixelBook-Regular.ttf"), 46);
	
	// constructor
	CharacterSelectionScreen(Menu menu, Stage stage, SoundController soundController) {
		this.menu = menu;
		this.stage = stage;
        this.root = new StackPane();
        this.selectPane = new Pane();
        this.root.getChildren().addAll(selectPane);
        this.scene = new Scene(root, GameStage.WINDOW_HEIGHT, GameStage.WINDOW_WIDTH);
        
        // initialize sound controller and play BGM
        this.soundController = soundController;
        this.soundController.playBGM(SoundController.Menu);
        
        this.p1CharIndex = -1;
        this.p2CharIndex = -1;
        this.currentVisibleIndex = -1;	// current visible index of -1 indicates that no chaarcter is currently selected
        this.setCharStatsArray();		// initialize character stats array
        setCharacterSelectionScene();	// prepare chaarcter selection scene
	}
	
	// show character selection scene
	void showCharacterSelectionScene(Stage primaryStage) {
		primaryStage.setScene(this.scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	// prepares elements of character selection scene
	private void setCharacterSelectionScene() {
		
        this.onClickSelectPane = new Pane();
        this.selectPane.setPrefSize(950,600);

		// background image
		Image bgImg = new Image("selectBG.png", 950, 600, false, false);
		BackgroundImage selectBG = new BackgroundImage(bgImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		root.setBackground(new Background(selectBG));
		
		// header
		ImageView selectHeader = new ImageView(new Image("selectHeader.png", 700, 100, false, false));
		selectHeader.setX(140);
		selectHeader.setY(20);
		
		// containers for on click pane (displays currently selected character image and stats)
		ImageView selectTile = new ImageView(new Image("selecttile.png", 400, 380, false, false));
		selectTile.setX(50);
		selectTile.setY(150);
		
		ImageView statsFrame = new ImageView(new Image("statsframe.png", 310, 310, false, false));
		statsFrame.setX(400);
		statsFrame.setY(150);
		
		// character icons/tiles
		bettyTile = new ImageView(new Image("bettytile.png", 150, 150, false, false));
		bettyTile.setX(88);	// set position
		bettyTile.setY(190);
		
		oliverTile = new ImageView(new Image("olivertile.png", 155, 155, false, false));
		oliverTile.setX(250);
		oliverTile.setY(191);
		
		maggieTile = new ImageView(new Image("maggietile.png", 150, 150, false, false));
		maggieTile.setX(88);
		maggieTile.setY(350);
		
		milesTile = new ImageView(new Image("milestile.png", 152, 150, false, false));
		milesTile.setX(251);
		milesTile.setY(350);
		
		selectPane.getChildren().addAll(selectHeader);
		selectPane.getChildren().addAll(selectTile, bettyTile, oliverTile, maggieTile, milesTile);
		
		// text; indicates if player 1 or player 2 is currently choosing
		Text player1Text = new Text("(player 1    >>   passenger)");
		Text player2Text = new Text("(player 2    >>   bandit)");
		
		// set font style, color, positioning
		player1Text.setFill(Color.web("#FE9150"));
		player1Text.setFont(Menu.pixelbook28);
		player1Text.setX(340);
		player1Text.setY(130);
		
		player2Text.setFill(Color.web("#D9D9D9"));
		player2Text.setFont(Menu.pixelbook28);
		player2Text.setX(340);
		player2Text.setY(130);
		
		// initially, player 2 is not yet choosing
		player2Text.setVisible(false);
		
		// text and labels for character names and stats
		Text cStatsLabel = new Text("Weapon:\nHealth:\nDamage:\nSpeed:\nAttack Range:");
		
		// character names
		cNames[0] = new Text(500, 270, "B E T T Y");
		cNames[1] = new Text(500, 270, "O L I V E R");
		cNames[2] = new Text(500, 270, "M A G G I E");
		cNames[3] = new Text(500, 270, "M I L E S");
		
		// set font style and color for character names
		for (int i = 0; i<4; i++) {
			cNames[i].setFill(Color.web("#543520"));
			cNames[i].setFont(pixelbook46);
			onClickSelectPane.getChildren().add(cNames[i]);
		}
		
		// set font style and color for labels
		cStatsLabel.setFill(Color.web("#543520"));
		cStatsLabel.setFont(pixelbook21);
		cStatsLabel.setX(510);
		cStatsLabel.setY(345);
	
		// back to menu button
		ImageView menuButton = new ImageView(new Image("menu-button.png", 150, 70, false, false));
		menuButton.setX(485);
		menuButton.setY(475);
		
		// select button
		ImageView selectButton = new ImageView(new Image("selectbutton.png", 150, 70, false, false));
		selectButton.setX(650);
		selectButton.setY(475);
		
		// (shown in player 2 selection) back button
		ImageView selectBackButton = new ImageView(new Image("selectbackbutton.png", 150, 70, false, false));
		selectBackButton.setX(485);
		selectBackButton.setY(475);
		
		// (shown in player 2 selection) play button
		ImageView playButton = new ImageView(new Image("playbutton.png", 150, 70, false, false));
		playButton.setX(650);
		playButton.setY(475);
		
		// error messages
		errorText = new Text(485, 570, "Please select a character!");	// shown when clicking select/play but no character was selected
		errorText2 = new Text(485, 570, "Player 1 already chose this character!");	// shown when player 2 selects player 1's character
		
		// initially, error messages are not shown
		errorText.setVisible(false);
		errorText2.setVisible(false);
		
		// set font style and color
		errorText.setFont(pixelbook21);				
		errorText.setFill(Color.web("#D9D9D9"));	// light blue
		errorText2.setFont(pixelbook21);
		errorText2.setFill(Color.web("#D9D9D9"));	// light blue
		
		// set hover events for buttons
		setButtonsEvent(selectButton, "selectbutton");
		setButtonsEvent(selectBackButton, "selectbackbutton");
		setButtonsEvent(playButton, "playbutton");
		setButtonsEvent(menuButton, "menu-button");
		
		// on click event for menu button (goes back to menu)
		menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			soundController.playSFX(SoundController.Click);	// click sfx
			this.stage.setScene(this.menu.getScene());
			this.stage.setTitle("Railway Bandit - MENU");
		});
		
		// on click event for select button (player 2 is then allowed to select)
		selectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			soundController.playSFX(SoundController.Click);	// click sfx
			
			if (currentVisibleIndex < 0) {
				errorText.setVisible(true);	// shows error message if player 1 has not selected any character
				return;
			}

			// instantiate player 1's character
			player1Character = new Character(cStatsArray[currentVisibleIndex][0], cStatsArray[currentVisibleIndex][1], 
											cStatsArray[currentVisibleIndex][2], cStatsArray[currentVisibleIndex][3], 0, currentVisibleIndex);
		
			// begin showing back & play button and player 2 text for player 2 selection
			selectBackButton.setVisible(true);
			playButton.setVisible(true);
			player2Text.setVisible(true);
			
			// stop showing select & menu button and player 1 text
			selectButton.setVisible(false);
			menuButton.setVisible(false);
			player1Text.setVisible(false);
			
			for (int i = 0; i<4; i++) {
				cStats[currentVisibleIndex][i].setVisible(false);	// reset currently visible character stats
			}
			characImages[currentVisibleIndex].setVisible(false);	// reset currently visible character image
			
			p1CharIndex = currentVisibleIndex; // store player 1's selection
			
			if (p2CharIndex < 0) {	// check if player 2 has not selected a character
				currentVisibleIndex = -1;	// reset current visible index (no character is selected)
				onClickSelectPane.setVisible(false);	// reset the on click pane 
				
			} else {	// player 2 has selected a character
				currentVisibleIndex = p2CharIndex;
				onClickSelectPane.setVisible(true);	// show the on click pane 
				for (int i = 0; i<4; i++) {
					cStats[currentVisibleIndex][i].setVisible(true);	// show player 2 character stats
				}
				characImages[currentVisibleIndex].setVisible(true);	// show player 2 character image
			}
			
		
		});
		
		// on click event for back button (can go back to player 1 selection while player 2 is selecting)
		selectBackButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			
			this.soundController.playSFX(SoundController.Click); // click sfx
			p2CharIndex = currentVisibleIndex; // store player 2's current selection
			
			// stop showing player 2 selection and show player 1's previously selected character
			onClickSelectPane.setVisible(true);
			for (int i = 0; i<4; i++) {
				if (currentVisibleIndex >= 0) {
					cStats[currentVisibleIndex][i].setVisible(false);
				}
				cStats[p1CharIndex][i].setVisible(true);
			}
			if (currentVisibleIndex >= 0) {
				characImages[currentVisibleIndex].setVisible(false);
			}
			characImages[p1CharIndex].setVisible(true);
			currentVisibleIndex = p1CharIndex;			
			
			// remove error messages
			errorText.setVisible(false);
			errorText2.setVisible(false);
			
			// show player 1 text, menu button, select button  again
			player1Text.setVisible(true);
			menuButton.setVisible(true);
			selectButton.setVisible(true);
			
			// stop showing player 2 text, back and play button
			player2Text.setVisible(false);
			playButton.setVisible(false);
			selectBackButton.setVisible(false);
		});
		
		// on click event for play button (start game)
		playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			this.soundController.playSFX(SoundController.Click);	// click sfx
			
			if (currentVisibleIndex >= 0) {
				if (p1CharIndex == currentVisibleIndex) {
					errorText2.setVisible(true);            		// show error message if player 2 selects player 1's character
					return;
				}
				
				// instantiate player 2's character
				player2Character = new Character(cStatsArray[currentVisibleIndex][0], cStatsArray[currentVisibleIndex][1], 
											cStatsArray[currentVisibleIndex][2], cStatsArray[currentVisibleIndex][3], 1, currentVisibleIndex);
				
				// instantiate and show the game stage
				GameStage gamestage = new GameStage(this.menu, player1Character, player2Character, this.soundController);
				gamestage.setStage(this.stage);	
				this.stage.show();
				
			} else {
				errorText.setVisible(true);	// show error message if player 2 did not select a character
				return;
			}	

		});
		
		// add character stats frame and labels to on click pane 
		onClickSelectPane.getChildren().addAll(statsFrame, cStatsLabel);
		onClickSelectPane.setVisible(false);	// initially, on click pane is not shown since no character is selected
		
		// player 1 is first to choose; so back button and play button are not yet visible
		selectBackButton.setVisible(false);
		playButton.setVisible(false);
	
		
		// labels for character names and stats
		Text bettyStats[] = {new Text(508, 270, "B E T T Y"), new Text(610, 345, "Dagger\n" + cStatsArray[0][0] + "\n" + cStatsArray[0][1] + "\n\n\n"),
				new Text(610, 410, String.valueOf(cStatsArray[0][2])), new Text(610, 433, " " + cStatsArray[0][3]/10)};
		Text oliverStats[] = {new Text(508, 270, "O L I V E R"), new Text(610, 345, "Axe\n\n\n" + cStatsArray[1][2] + "\n " + cStatsArray[1][3]/10),
				new Text(610, 368, String.valueOf(cStatsArray[1][0])), new Text(610, 390, String.valueOf(cStatsArray[1][1]))};
		Text maggieStats[] = {new Text(508, 270, "M A G G I E"), new Text(610, 345, "Mech Arm\n\n\n" + cStatsArray[2][2] +"\n " + cStatsArray[2][3]/10),
				new Text(610, 390, String.valueOf(cStatsArray[2][1])), new Text(610, 370, String.valueOf(cStatsArray[2][0]))};
		Text milesStats[] = {new Text(508, 270, "M I L E S"), new Text(610, 345, "Sword\n" + cStatsArray[3][0] + "\n" + cStatsArray[3][1] +"\n\n\n"),
				new Text(610, 433, " " + cStatsArray[3][3]/10), new Text(610, 410, String.valueOf(cStatsArray[3][2]))};
		
		cStats[0] = bettyStats;
		cStats[1] = oliverStats;
		cStats[2] = maggieStats;
		cStats[3] = milesStats;
		
		// set font style and color
		for (int index = 0; index<4; index++) {
			for (int i = 0; i<4 ;i++) {
				
				if (i == 0) {
					cStats[index][i].setFont(pixelbook46);
				} else {
					cStats[index][i].setFont(pixelbook21);
				}
				
				if (i == 3) {
					cStats[index][i].setFill(Color.web("#C00000"));		// red		- indicates downgraded character ability/weakness
				} else if (i == 2) {
					cStats[index][i].setFill(Color.web("#084F6A"));		// blue		- indicates upgraded character ability/strength
				} else {
					cStats[index][i].setFill(Color.web("#543520"));	   // brown     - indicates normal character ability
				}
				onClickSelectPane.getChildren().add(cStats[index][i]);
				cStats[index][i].setVisible(false);	// initially, no character stats are shown
			}
		}
		
		// character images for preview
		characImages[0] = new ImageView(new Image("bettyGlow.png", 300, 300, false, false));
		characImages[1] = new ImageView(new Image("oliverGlow.png", 300, 300, false, false));
		characImages[2] = new ImageView(new Image("maggieGlow.png", 300, 300, false, false));
		characImages[3] = new ImageView(new Image("milesGlow.png", 300, 300, false, false));
		
		// set positioning 
		for (int i = 0; i<4; i++) {
			characImages[i].setX(650);
			characImages[i].setY(190);
			onClickSelectPane.getChildren().add(characImages[i]);
			characImages[i].setVisible(false);	// initially, no character image preview is shown
		}
		
		// add elements to select pane
		selectPane.getChildren().addAll(selectButton, selectBackButton, playButton, menuButton, player1Text, player2Text, errorText, errorText2, onClickSelectPane);
		
		// set hover and on click event for character tiles
		setCharacterTilesEvent(bettyTile, 1);
		setCharacterTilesEvent(oliverTile, 2);
		setCharacterTilesEvent(maggieTile, 3);
		setCharacterTilesEvent(milesTile, 4);
		
		// z-positioning
		bettyTile.toFront();
		oliverTile.toFront();
		maggieTile.toFront();
		milesTile.toFront();
	}
	
	// set hover event for buttons
	private void setButtonsEvent(ImageView button, String path) {
		
		button.setOnMouseEntered(e -> {
			button.setImage(new Image(path + "Hover.png", 150, 70, false, false));
		});
		        
		button.setOnMouseExited(e -> {
			button.setImage(new Image(path + ".png", 150, 70, false, false));
		});
		
	}
	
	// initialize character stats
	// 1st index indicates character 
	// 2nd index indicates specific character stat (0 - health, 1 - damage, 2 - speed, 3 - attack range)
	private void setCharStatsArray() {
		for (int i = 0; i<4; i++) {
			// normal stats: 100 health, 12 dmg, 10 speed, 100 true attack range (attack range/10 is displayed to player)
			if (i == 0 || i == 3) {
				cStatsArray[i][0] = 100;
				cStatsArray[i][1] = 12;
			} else {
				cStatsArray[i][2] = 10;
				cStatsArray[i][3] = 100;
			}
		}
		
		// upgraded/downgraded stats
		cStatsArray[0][2] = 12;
		cStatsArray[0][3] = 70;
		
		cStatsArray[3][2] = 8;
		cStatsArray[3][3] = 130;
		
		cStatsArray[1][0] = 120;
		cStatsArray[1][1] = (float) 10.6;
		
		cStatsArray[2][0] = 80;
		cStatsArray[2][1] = (float) 13.4;
	}

	// used to show character stats and image of a clicked character tile
	private void setCharStatsVisible(int index) {
		for (int i = 0; i<4; i++) {
			if (currentVisibleIndex >= 0) {
				cStats[currentVisibleIndex][i].setVisible(false);
				characImages[currentVisibleIndex].setVisible(false);
			}
			cStats[index][i].setVisible(true);
			characImages[index].setVisible(true);
		}
		this.currentVisibleIndex = index;
	}
	
	// set hover and on click event for character tiles
	private void setCharacterTilesEvent(ImageView cTile, int characterNum) {	

		cTile.setOnMouseEntered(e -> {
			cTile.setOpacity(0.5);	
		});
		        
		cTile.setOnMouseExited(e -> {
			cTile.setOpacity(1);	
		});
		        
		cTile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			this.soundController.playSFX(SoundController.Click);	// click sfx
			errorText.setVisible(false);	// remove error text
			switch (characterNum) {			// 0 - betty, 1 - oliver, 2 - maggie, 3 - miles
			case 1:
				setCharStatsVisible(0);
				break;
			case 2:
				setCharStatsVisible(1);
				break;
			case 3:
				setCharStatsVisible(2);
				break;
			case 4:
				setCharStatsVisible(3);
				break;
			}
			onClickSelectPane.setVisible(true);
		});		
	}
	
	// getters
	Scene getScene() {
		return this.scene;
	}

}