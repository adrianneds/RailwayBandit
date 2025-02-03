/***********************************************************
* Railway Bandit - Menu Class; CMSC 22 WX-4L (Final Project)
*
* @author Adrianne D. Solis
* @created_date 2024-05-02 6:43 PM
*
***********************************************************/

package tools;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Menu {

	// stage and scenes
	private Stage stage;
	private Scene scene;
	private Scene aboutScene;
	private Scene devScene;
	private StackPane root = new StackPane();
	
	// sound controller for music and sfx
	private SoundController soundController;
	
	// stack pane and panes for about scene
	private StackPane aboutPanes;
	private Pane aboutPane1;
	private Pane aboutPane2;
	private Pane aboutPane3;
	private Pane aboutPane4;
	private Pane aboutPane5;
	private Pane referencesPane1;
	private Pane referencesPane2;
	
	// stack pane for developer scene
	private StackPane devSPane;
	
	// window dimensions
	public final static int WINDOW_WIDTH = 950;
	public final static int WINDOW_HEIGHT = 600;
	
	// fonts
	static Font pixelbook12 = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("PixelBook-Regular.ttf"), 12);
	static Font pixelbook15 = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("PixelBook-Regular.ttf"), 15);
	public static Font pixelbook25 = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("PixelBook-Regular.ttf"), 25);
	public static Font pixelbook28 = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("PixelBook-Regular.ttf"), 28);
	static Font pixelbook30 = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("PixelBook-Regular.ttf"), 30);
	
	// header for instructions (in about scene)
	private ImageView instructionsHeader = new ImageView(new Image("about_instructions_header.png", 600, 120, false, false));
	
	// header for references (in about scene)
	private ImageView refHeader = new ImageView(new Image("references_header.png", 600, 120, false, false));
	
	// HBox for main menu "buttons"/images
	private HBox buttons;

	// constructor
	Menu () {
		
		// initialize stage
		this.stage = new Stage();
		
		// initialize stack pane for menu
		this.root = new StackPane();
		
		// stack panes for developer and about scene
		this.devSPane = new StackPane();
		this.aboutPanes = new StackPane();
		
		// panes to use in about scene
		this.aboutPane1 = new Pane();
		this.aboutPane2 = new Pane();
		this.aboutPane3 = new Pane();
		this.aboutPane4 = new Pane();
		this.aboutPane5 = new Pane();
		this.referencesPane1 = new Pane();
		this.referencesPane2 = new Pane();
		
		// initialize soundController
		this.soundController = new SoundController();
		
		this.setButtons();			// set buttons for main menu
		setAboutScene();			// set about scene
		setDeveloperScene();		// set developer scene
		
		// set main menu background
		Image bgImg = new Image("menu-bg.png", 950, 600, false, false);
		BackgroundImage myBI= new BackgroundImage(bgImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		this.root.setBackground(new Background(myBI));
	}
	
	// set stage and initialize scenes
		public void setStage(Stage stage) {
				
			// initialize menu scene
			this.scene = new Scene(root, Menu.WINDOW_WIDTH, Menu.WINDOW_HEIGHT);
			
			// set stage for main menu
			this.stage = stage;																
			this.stage.setTitle("Railway Bandit - MENU");				
			this.stage.setScene(this.scene);
			this.stage.setResizable(false);
			this.stage.show();							
			
			// initialize about and developer scene
			this.aboutScene = new Scene(aboutPanes, Menu.WINDOW_WIDTH, Menu.WINDOW_HEIGHT);		
			this.devScene = new Scene(devSPane, Menu.WINDOW_WIDTH, Menu.WINDOW_HEIGHT);
			
			// play background music for menu
			this.soundController.playBGM(SoundController.Menu);
			
			// add stage icon
			this.stage.getIcons().add(new Image("stage_icon.png")); 
		}
	
	// set properties for main menu "buttons"/images
	private void setButtons() {
        
		// images that would serve as the buttons
        ImageView startBtnView = new ImageView(new Image("start_button.png", 180, 85, false, false));
        ImageView aboutBtnView = new ImageView(new Image("about_button.png", 180, 85, false, false));
        ImageView devBtnView = new ImageView(new Image("dev_button.png", 180, 85, false, false));
        ImageView quitBtnView = new ImageView(new Image("quit_button.png", 180, 85, false, false));
        
        // place the images/"buttons" in an HBox
        this.buttons = new HBox(30); 
        this.buttons.getChildren().addAll(startBtnView, aboutBtnView, devBtnView, quitBtnView);
        StackPane.setMargin(buttons, new javafx.geometry.Insets(500, 100, 100, 100)); // Insets: top, right, bottom, left
        this.root.getChildren().add(buttons);
        
        // set events for "buttons"/images
        setMainButtonsEvent(startBtnView, "start_button.png", "start_hover_button.png");
        setMainButtonsEvent(aboutBtnView, "about_button.png", "about_hover_button.png");
        setMainButtonsEvent(devBtnView, "dev_button.png", "dev_hover_button.png");
        setMainButtonsEvent(quitBtnView, "quit_button.png", "quit_hover_button.png");
	}
	
	// set hover and onclick actions for main menu "buttons" / images
		private void setMainButtonsEvent(ImageView button, String imgPath, String imgHoverPath) {	
			
			button.setOnMouseEntered(e -> {
				button.setImage(new Image(imgHoverPath, 180, 85, false, false));		// change button image when cursor hovers upon image
			});
			        
			button.setOnMouseExited(e -> {
				button.setImage(new Image(imgPath, 180, 85, false, false));				// replace button image to previous image when cursor is removed
			});
			        
			button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				this.soundController.playSFX(SoundController.Click);
				if (imgPath.equals("quit_button.png")) {
					System.exit(0);								// EXIT program
				} else if (imgPath.equals("about_button.png")) {
					showAboutScene();							// about scene
				} else if (imgPath.equals("dev_button.png")) {
					showDeveloperScene();						// developer scene
				} else if (imgPath.equals("start_button.png")) {    // start game
					
					// show character selection screen
					CharacterSelectionScreen selectCharacterScreen = new CharacterSelectionScreen(this, this.stage, this.soundController);
					this.stage.setTitle("Railway Bandit - Select a Character!");	// set new stage title
					this.stage.setScene(selectCharacterScreen.getScene());			// set new scene
				}
			});		
		}
	
	// method used for showing about scene
	private void showAboutScene() {
		this.stage.setTitle("Railway Bandit - About the Game");	// window title
		this.stage.setScene(aboutScene);
		this.stage.setResizable(false);
		this.aboutPane1.setVisible(true);
	}
	
	// method to show developer scene
	private void showDeveloperScene() {		
		this.stage.setTitle("Railway Bandit - The Developer");	// set new stage title
		this.stage.setScene(devScene);
		this.stage.setResizable(false);
	}
	
	// method for setting about scene elements
	private void setAboutScene() {

		// initially set all panes to not visible (except 1st pane -- aboutPane1)
		this.instructionsHeader.setVisible(false);
		this.refHeader.setVisible(false);
		this.aboutPane2.setVisible(false);
		this.aboutPane3.setVisible(false);
		this.aboutPane4.setVisible(false);
		this.aboutPane5.setVisible(false);
		this.referencesPane1.setVisible(false);
		this.referencesPane2.setVisible(false);
		
		// header for instructions
        StackPane.setMargin(instructionsHeader, new javafx.geometry.Insets(0, 0, 355, 30)); // Insets: top, right, bottom, left
        this.aboutPanes.getChildren().addAll(instructionsHeader);
        
        // header for references
        StackPane.setMargin(refHeader, new javafx.geometry.Insets(0, 0, 355, 30)); // Insets: top, right, bottom, left
        this.aboutPanes.getChildren().addAll(refHeader);

		// set background
		ImageView aboutBgPane = new ImageView(new Image("about_bg.png", 950, 600, false, false));
		this.aboutPanes.getChildren().add(aboutBgPane);
		aboutBgPane.toBack();
		
		// PANE 1
		
		// about scene header
		ImageView aboutHeader = new ImageView(new Image("about_header.png", 600, 120, false, false));
		aboutHeader.setX(185);
		aboutHeader.setY(65);
		
		// gear icon
		ImageView gearIcon = new ImageView(new Image("gear.png", 45, 45, false, false));
		gearIcon.setX(130); // positioning
		gearIcon.setY(280);
		
		// buttons
		ImageView aboutMenuButton = new ImageView(new Image("about_menu_button.png", 200, 50, false, false));
		ImageView aboutInstructionsButton = new ImageView(new Image("about_instructions_button.png", 200, 50, false, false));
		ImageView aboutReferencesButton = new ImageView(new Image("about_references_button.png", 200, 50, false, false));
		
		// set coordinates/positioning for buttons
        aboutMenuButton.setX(110);
        aboutMenuButton.setY(530);
        aboutInstructionsButton.setX(415);
        aboutInstructionsButton.setY(530);
        aboutReferencesButton.setX(650);
        aboutReferencesButton.setY(530);
        
        // set hover and on click events for buttons
		setAboutButtonsEvent(aboutMenuButton, 0);
		setAboutButtonsEvent(aboutInstructionsButton, 3);
		setAboutButtonsEvent(aboutReferencesButton, 2);

		// text body (story sypnosis)		
		Text aboutp1_texts[] = {new Text("       "), new Text("This good ol’ town is in danger! "),
				              new Text("The notorious "), new Text("bandits"), 
				              new Text(" have kidnapped the captain and took over the Southern Express after disguising themselves as civilians.\n\n      It is now up to the "),
				              new Text("passengers"), new Text(" to fight against this reign of terror!")};		
		TextFlow aboutp1_textflow = new TextFlow();	// use text flow to organize text
		
		// set font style and color
		for (int i = 0; i<aboutp1_texts.length; i++) {
			aboutp1_texts[i].setFont(pixelbook30);
			aboutp1_texts[i].setFill(Color.web("#DEAE36"));
			if (i == 3 || i == 5) {
				aboutp1_texts[i].setFill(Color.web("#FE9150"));
				aboutp1_texts[i].setUnderline(true);
			}
			aboutp1_textflow.getChildren().add(aboutp1_texts[i]);
		}
		
		// set textflow size and positioning
		aboutp1_textflow.setPrefSize(700, 100);
		aboutp1_textflow.setLayoutX(130);
		aboutp1_textflow.setLayoutY(300);
		
		// add elements to aboutPane1
		this.aboutPane1.getChildren().addAll(aboutHeader, aboutMenuButton, aboutInstructionsButton, aboutReferencesButton, gearIcon, aboutp1_textflow);
		
		this.aboutPanes.getChildren().addAll(aboutPane1);	// add aboutPane1 to about pane stack pane
		
		// PANE 2
		
		// gear icons
		ImageView gearIcon2 =  new ImageView(new Image("gear.png", 35, 35, false, false));
		ImageView gearIcon3 =  new ImageView(new Image("gear.png", 35, 35, false, false));
		gearIcon2.setX(130); // positioning
		gearIcon2.setY(290);
		gearIcon3.setX(130);
		gearIcon3.setY(390);
		
		// buttons
		ImageView aboutaboutButton = new ImageView(new Image("about_about_button.png", 200, 50, false, false));
		ImageView aboutPage2Button = new ImageView(new Image("about_page2_button.png", 200, 50, false, false));
		
		// buttons positioning
		aboutaboutButton.setX(110);
		aboutaboutButton.setY(530);
		aboutPage2Button.setX(650);
		aboutPage2Button.setY(530);
		
		// set hover and on click events for buttons
		setAboutButtonsEvent(aboutaboutButton, 1);
		setAboutButtonsEvent(aboutPage2Button, 4);
		
		// text body (instructions part 1)
		Text aboutp2_texts1[] = {new Text("        The two players (passenger and bandit) will fight each other on a moving train. The game ends when one player is "),
				                 new Text("defeated."), new Text(" The other is then declared as the winner.")};		
		TextFlow aboutp2_textflow1 = new TextFlow(); // use text flow to organize text
		
		// set font style and color
		for (int i = 0; i<aboutp2_texts1.length; i++) {
			aboutp2_texts1[i].setFont(pixelbook25);
			aboutp2_texts1[i].setFill(Color.web("#DEAE36"));	// gold
			if (i == 1) {
				aboutp2_texts1[i].setFill(Color.web("#FE9150"));	// orange
				aboutp2_texts1[i].setUnderline(true);
			}
			aboutp2_textflow1.getChildren().add(aboutp2_texts1[i]);
		}
		
		// set text flow size and layout
		aboutp2_textflow1.setPrefSize(700, 100);
		aboutp2_textflow1.setLayoutX(130);
		aboutp2_textflow1.setLayoutY(300);
		
		Text aboutp2_texts2[] = {new Text("PLAYER 1 "), new Text("(Passenger)"), new Text("\n\nPLAYER 2 "), new Text("(Bandit)")};
		TextFlow aboutp2_textflow2 = new TextFlow(); // use text flow to organize text
		
		// set font style and color
		for (int i = 0; i<aboutp2_texts2.length; i++) {
			aboutp2_texts2[i].setFont(pixelbook30);
			aboutp2_texts2[i].setFill(Color.web("#DEAE36"));	// gold
			if (i == 1 || i == 3) {
				aboutp2_texts2[i].setFill(Color.web("#FE9150"));	// orange
			}
			aboutp2_textflow2.getChildren().add(aboutp2_texts2[i]);
		}
		
		aboutp2_textflow2.setPrefSize(700, 100);
		aboutp2_textflow2.setLayoutX(170);
		aboutp2_textflow2.setLayoutY(400);
		
		// image for keyboard controls
		ImageView controls = new ImageView(new Image("controls.png", 220, 120, false, false));
		controls.setX(450);
		controls.setY(390);
		
		// add elements to aboutPane2
		this.aboutPane2.getChildren().addAll(aboutaboutButton, gearIcon2, gearIcon3, aboutPage2Button, aboutp2_textflow1, aboutp2_textflow2, controls);
		
		this.aboutPanes.getChildren().add(aboutPane2); // add aboutPane2 to about about scene stack pane
		
		// PANE 3
		
		// buttons
		ImageView aboutPage1Button = new ImageView(new Image("about_page1_button.png", 200, 50, false, false));
		ImageView aboutPage3Button = new ImageView(new Image("about_page3_button.png", 200, 50, false, false));
		
		// buttons positioning
		aboutPage1Button.setX(110);
		aboutPage1Button.setY(530);
		aboutPage3Button.setX(650);
		aboutPage3Button.setY(530);
		
		// set hover and on click events for buttons
		setAboutButtonsEvent(aboutPage1Button, 3);
		setAboutButtonsEvent(aboutPage3Button, 5);
		
		// gear icons
		ImageView gearIcon4 =  new ImageView(new Image("gear.png", 35, 35, false, false));
		ImageView gearIcon5 =  new ImageView(new Image("gear.png", 35, 35, false, false));
		gearIcon4.setX(100);	// positioning
		gearIcon4.setY(300);
		gearIcon5.setX(100);
		gearIcon5.setY(400);
		
		// text body (powerups details)
		Text aboutp3_texts[] = {new Text("Powerups"), 
								new Text(" will randomly spawn during the game. The player picks it up by simply moving over it. These de-spawn after 15-45 seconds of not being picked up.\n\n"), 
								new Text("Reverse Powerups"), new Text(" that weaken the player also randomly spawn during the game. The player picks it up by simply moving over it. These de-spawn after 15-45 seconds of not being picked up. ")};		
		TextFlow aboutp3_textflow = new TextFlow();	// use text flow to organize text
		
		// set font style and color
		for (int i = 0; i<aboutp3_texts.length; i++) {
			aboutp3_texts[i].setFont(pixelbook25);
			aboutp3_texts[i].setFill(Color.web("#DEAE36"));	// gold
			if (i == 0 || i == 2) {
				aboutp3_texts[i].setFill(Color.web("#FE9150"));	// orange
			}
			aboutp3_textflow.getChildren().add(aboutp3_texts[i]);
		}
		
		// set text flow size and positioning
		aboutp3_textflow.setPrefSize(700, 60);
		aboutp3_textflow.setLayoutX(145);
		aboutp3_textflow.setLayoutY(300);
		
		// add elements to aboutPane3
		this.aboutPane3.getChildren().addAll(aboutPage1Button, aboutPage3Button, gearIcon4, gearIcon5, aboutp3_textflow);
		
		// add aboutPane3 to about scene stack pane
		this.aboutPanes.getChildren().add(aboutPane3);
		
		// PANE 4
		
		// buttons
		ImageView aboutPage2BackButton = new ImageView(new Image("about_page2_back_button.png", 200, 50, false, false));
		ImageView aboutPage4Button = new ImageView(new Image("about_page4_button.png", 200, 50, false, false));
		
		// set button positioning
		aboutPage2BackButton.setX(110);
		aboutPage2BackButton.setY(530);
		aboutPage4Button.setX(650);
		aboutPage4Button.setY(530);
		
		// sub-header and bullets for powerups list
		ImageView powerupsHeader = new ImageView(new Image("powerups_bullets.png", 250, 260, false, false));
		powerupsHeader.setX(160);
		powerupsHeader.setY(250);
		
		// text body (powerups list)
		Text aboutp4_text1 = new Text("Electric Apple\nFeather\nGear\nGoggles\nTime Machine\n");	
		aboutp4_text1.setLineSpacing(12);
		
		// set font style and color
		aboutp4_text1.setFont(pixelbook28);	
		aboutp4_text1.setFill(Color.web("#FE9150"));	// orange
		
		// set text positioning
		aboutp4_text1.setX(245);
		aboutp4_text1.setY(330);
		
		// text body (powerups details)
		Text aboutp4_text2 = new Text("+8 health\n+3 speed for 7 seconds\n+0.7 damage\n+0.8 attack range\nfreeze surroundings (except the player)\n");		
		aboutp4_text2.setLineSpacing(12);
		
		// set font style and color
		aboutp4_text2.setFont(pixelbook28);	
		aboutp4_text2.setFill(Color.web("#DEAE36"));	// gold
		
		// set text positioning
		aboutp4_text2.setX(445);
		aboutp4_text2.setY(330);
		
		// set hover and on click events for button
		setAboutButtonsEvent(aboutPage2BackButton, 4);
		setAboutButtonsEvent(aboutPage4Button, 6);
		
		// add elements to aboutPane4
		this.aboutPane4.getChildren().addAll(aboutPage2BackButton, aboutPage4Button, powerupsHeader, aboutp4_text1, aboutp4_text2);
		
		// add aboutPane4 to about scene stack pane
		this.aboutPanes.getChildren().add(aboutPane4);
		
		// PANE 5
		
		// buttons
		ImageView aboutaboutButton2 = new ImageView(new Image("about_about_button.png", 200, 50, false, false));
		ImageView aboutPage3BackButton = new ImageView(new Image("about_page3_back_button.png", 200, 50, false, false));
		
		// buttons positioning
		aboutaboutButton2.setX(320);
		aboutaboutButton2.setY(530);
		aboutPage3BackButton.setX(110);
		aboutPage3BackButton.setY(530);
		
		// set hover and on click event for button
		setAboutButtonsEvent(aboutaboutButton2, 1);
		setAboutButtonsEvent(aboutPage3BackButton, 5);
		
		// header and bullets for reverse powerups list
		ImageView reverseHeader = new ImageView(new Image("reverse_powerup_bullets.png", 330, 200, false, false));
		reverseHeader.setX(160);	// positioning
		reverseHeader.setY(250);
		
		// text body (reverse powerups list)
		Text aboutp5_text1 = new Text("Poisoned Apple\nHeavy Boots\nWeakening Potion");		
		aboutp5_text1.setLineSpacing(20);
		
		// set font style and color
		aboutp5_text1.setFont(pixelbook28);	
		aboutp5_text1.setFill(Color.web("#FE9150"));	// orange
		
		// text positioning
		aboutp5_text1.setX(230);
		aboutp5_text1.setY(330);
		
		// text body (reverse powerups details)
		Text aboutp5_text2 = new Text("-10 health\n-4.5 speed for 10 seconds\n-1.5 damage");		
		aboutp5_text2.setLineSpacing(20);
		
		// set font style and color
		aboutp5_text2.setFont(pixelbook28);	
		aboutp5_text2.setFill(Color.web("#DEAE36"));	// gold
		
		// text positioning
		aboutp5_text2.setX(530);
		aboutp5_text2.setY(330);
		
		// add elements to aboutPane5
		this.aboutPane5.getChildren().addAll(aboutaboutButton2, aboutPage3BackButton, reverseHeader, aboutp5_text1, aboutp5_text2);
		
		// add aboutPane5 to about scene stack pane
		this.aboutPanes.getChildren().add(aboutPane5);
		
		// REFERENCES
		
		// buttons
		ImageView aboutPage2Button2 = new ImageView(new Image("about_page2_button.png", 200, 50, false, false));
		ImageView aboutaboutButton3 = new ImageView(new Image("about_about_button.png", 200, 50, false, false));

		// buttons positioning
		aboutPage2Button2.setX(650);
		aboutPage2Button2.setY(530);
		aboutaboutButton3.setX(110);
		aboutaboutButton3.setY(530);
		
		// set hover and onclick event for buttons
		setAboutButtonsEvent(aboutPage2Button2, 7);
		setAboutButtonsEvent(aboutaboutButton3, 1);
		
		// text body (links)
		Text ref_texts1[] = {new Text("Images\n"), 
				(new Text("Various steampunk icons: https://pixeljoint.com/pixelart/47874.htm\r\nLadder: https://www.pixilart.com/art/ladder\n-sr2697ef67517d7?ft=user&ft_id=325488\r\n"
						+ "Train interior: https://www.artstation.com/artwork/J9Wdkd\r\n"
						+ "Clouds: https://www.pinterest.ph/pin/clouds-please-use-credit\n-if-use-freetoedit--764415736745376339/\r\n"
						+ "Steampunk pixel art icons: https://www.pinterest.ph/\npin/207587864051531890/\r\n"
						+ "Steampunk pixel art icons (2): https://itch.io/game-assets/tag-\npixel-art/tag-steampunk\r\n"
						+ "Steampunk pixel art icons (3): https://www.deviantart.com/tdele\neuw/art/Mo-Steampunk-sprites-1-2-pixelart-432646877\r\n")), 
						(new Text("Credits to Rianne Cantos\n for character graphics"))};

		TextFlow ref_textsflow1 = new TextFlow();				// use text flow to organize text
		
		// set font style and color
		for (int i = 0; i<ref_texts1.length; i++) {
			ref_texts1[i].setFont(pixelbook12);
			ref_texts1[i].setFill(Color.web("#DEAE36"));	    // gold
			if (i == 0) {
				ref_texts1[i].setFill(Color.web("#FE9150"));	// orange
			} if (i==ref_texts1.length-1) {
				ref_texts1[i].setFont(pixelbook15);
			}
			ref_textsflow1.getChildren().add(ref_texts1[i]);
		}
		
		// set text flow size and positioning
		ref_textsflow1.setPrefSize(400, 30);
		ref_textsflow1.setLayoutX(120);
		ref_textsflow1.setLayoutY(280);
		
		// text body (links)
		Text ref_texts2[] = {new Text("Font\n"), new Text("PixelBook: https://www.dafont.com/pixel-book.font\n"),
				new Text("Steampunk furnace: https://www.vecteezy.com/png/27226460-an-8-bit\b-retro-styled-pixel-art-illustration-of-a-bronze-steampunk-furnace\r\n"
						+ "Apple: https://br.pinterest.com/pin/532761830921370064\n/?amp_client_id=CLIENT_ID%28_%29&mweb_unauth_id=%7B%7Bdefault.session%7D%7D&from_amp_pin_page=true\r\n"
						+ "Feather: https://the-messenger.fandom.com/wiki/Voodoo_Feathers\r\n"
						+ "Gear icons: https://www.pinterest.co.kr/pin/811351689095548769/\r\n"
						+ "Steampunk goggles: https://pixel-planet.fandom.com/wiki/Steampunk_Goggles\r\n"
						+ "Steampunk clock: https://www.deviantart.com/smallcuteskitty/art/Repairs-982678537\r\n"
						+ "Poisoned apple: https://www.deviantart.com/chanman25/art/Poison-\nApple-608173367Steampunk boot: https://undermine.fandom.com/wiki/Wayland%27s_Boots\r\n"
						+ "Train pixel art: https://itch.io/game-assets/tag-trains\r\n"
						+ "Green potion: https://www.vecteezy.com/png/28651800-pixel-art-green-potion-icon\n" 
						+ "Light bulb: https://www.vecteezy.com/png/27190663-pixel-art-yellow-light-bulb-icon")};
		TextFlow ref_textsflow2 = new TextFlow();          // use text flow to organize text
		
		// set font style and color
		for (int i = 0; i<ref_texts2.length; i++) {
			ref_texts2[i].setFont(pixelbook12);
			ref_texts2[i].setFill(Color.web("#DEAE36"));	// gold
			if (i == 0) {
				ref_texts2[i].setFill(Color.web("#FE9150"));	// orange
			}
			ref_textsflow2.getChildren().add(ref_texts2[i]);
		}
		
		// set text flow size and positioning
		ref_textsflow2.setPrefSize(400, 30);
		ref_textsflow2.setLayoutX(450);
		ref_textsflow2.setLayoutY(280);
		
		// add elements to referencesPane1
		this.referencesPane1.getChildren().addAll(aboutPage2Button2, aboutaboutButton3, ref_textsflow1, ref_textsflow2);
		
		// add referencesPane1 to about scene stack pane
		this.aboutPanes.getChildren().add(referencesPane1);
		
		// text body (links)
		Text ref_texts3[] = {new Text("Sounds and Music: \n"),
							new Text("Attack SFX: https://www.youtube.com/watch?v=g-lcamn3VRE\n" 
							+ "Climb SFX: https://www.youtube.com/watch?v=pO71J_o1Cwg\n"
							+ "Pop SFX: https://www.youtube.com/watch?v=-alGcEs7XYo\n"
							+ "Powerup SFX: https://www.youtube.com/watch?v=SoZhpnTuQBo\n"
							+ "Win SFX: https://www.youtube.com/watch?v=teUWsONJkk8\n"
							+ "Game BGM: https://www.youtube.com/watch?v=BfzuI-zrVag&list=PLNSlKYf_48dcnjMINFOtj96mCEqMAVCCP&index=6\n"
							+ "Menu BGM: https://www.youtube.com/watch?v=K3uxxpsbVgA")};
		TextFlow ref_textsflow3 = new TextFlow();          // use text flow to organize text
		
		// set font style and color
		for (int i = 0; i<ref_texts3.length; i++) {
			ref_texts3[i].setFont(pixelbook12);
			ref_texts3[i].setFill(Color.web("#DEAE36"));	    // gold
			if (i == 0) {
				ref_texts3[i].setFill(Color.web("#FE9150"));	// orange
			}
			ref_textsflow3.getChildren().add(ref_texts3[i]);
		}
		
		// set text flow size and positioning
		ref_textsflow3.setPrefSize(400, 30);
		ref_textsflow3.setLayoutX(120);
		ref_textsflow3.setLayoutY(280);
		
		// back button
		ImageView refBackButton = new ImageView(new Image("about_page1_button.png", 200, 50, false, false));
		refBackButton.setX(110);
		refBackButton.setY(530);
		setAboutButtonsEvent(refBackButton, 2);
		
		// add elements to referencesPane2
		this.referencesPane2.getChildren().addAll(ref_textsflow3, refBackButton);
		
		// add referencesPane2 to scene stack pane
		this.aboutPanes.getChildren().add(referencesPane2);
		
	}
	
	// set hover and on click actions for about scene "buttons"/images
	private void setAboutButtonsEvent(ImageView button, int i) {
		button.setOnMouseEntered(e -> {			
			button.setOpacity(0.5);					// change to 50% opacity when cursor hovers over image
		});
		
		button.setOnMouseExited(e -> {				// revert back to 100% opacity when cursor is removed
			button.setOpacity(1);
		});
		
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			this.soundController.playSFX(SoundController.Click);		// click sfx
			if (i == 0) {
				this.stage.setScene(this.scene);				// back to main menu
				this.stage.setTitle("Railway Bandit - MENU");	
			} else if (i == 3) {
				this.aboutPane3.setVisible(false);					// about pane 2 (instructions page 1)
				this.aboutPane1.setVisible(false);
				this.instructionsHeader.setVisible(true);
				this.aboutPane2.setVisible(true); 					
			} else if (i == 2) {
				this.referencesPane2.setVisible(false);
				this.aboutPane1.setVisible(false);
				this.refHeader.setVisible(true);
				this.referencesPane1.setVisible(true);				// references pane 1
			} else if (i == 1) {
				this.referencesPane1.setVisible(false);
				this.aboutPane5.setVisible(false);
				this.aboutPane2.setVisible(false);
				this.aboutPane3.setVisible(false);
				this.instructionsHeader.setVisible(false);
				this.refHeader.setVisible(false);
				this.aboutPane1.setVisible(true);					// about pane 1
			} else if (i == 4) {
				this.aboutPane4.setVisible(false);
				this.aboutPane2.setVisible(false);
				this.instructionsHeader.setVisible(true);
				this.aboutPane3.setVisible(true);					// about pane 3
			} else if (i == 5) {
				this.aboutPane3.setVisible(false);
				this.aboutPane5.setVisible(false);
				this.instructionsHeader.setVisible(true);
				this.aboutPane4.setVisible(true);					// about pane 4
			} else if (i == 6) {
				this.aboutPane4.setVisible(false);
				this.instructionsHeader.setVisible(true);
				this.aboutPane5.setVisible(true);					// about pane 5
			} else if (i == 7) {
				this.referencesPane1.setVisible(false);				// references pane 2
				this.refHeader.setVisible(true);
				this.referencesPane2.setVisible(true);
			}
		});		
		
	}
	
	private void setDeveloperScene() {		// set nodes in devSPane for developer scene
	
		// background
		ImageView devBg = new ImageView(new Image("developer.png", 950, 600, false, false));
		this.devSPane.getChildren().add(devBg);
		devBg.toBack();
		
		// developer's name
		ImageView devName = new ImageView(new Image("adriannedsolis.png", 350, 150, false, false));
		StackPane.setMargin(devName, new javafx.geometry.Insets(0, 0, 50, 0)); // Insets: top, right, bottom, left
		
		// text	
		Text devText1 = new Text(">> Student, UPLB BS Statistics Batch 2022");
		Text devText2 = new Text("    adsolis@up.edu.ph");
		
		// set text positioning, font style, and color
		StackPane.setMargin(devText1, new javafx.geometry.Insets(80, 0, 0, 0)); // Insets: top, right, bottom, left
		StackPane.setMargin(devText2, new javafx.geometry.Insets(145, 15, 0, 0)); 
		devText1.setFont(pixelbook30);
		devText2.setFont(pixelbook30);
		devText1.setFill(Color.web("#DEAE36"));	// gold
		devText2.setFill(Color.web("#DEAE36"));	// gold
		
		// mail icon
		ImageView mailIcon = new ImageView(new Image("mail.png", 30, 30, false, false));
		StackPane.setMargin(mailIcon, new javafx.geometry.Insets(250, 240, 100, 0)); // Insets: top, right, bottom, left
		
		// bottom text
		ImageView devBottomText = new ImageView(new Image("railwaybanditwas.png", 450, 30, false, false));
		StackPane.setMargin(devBottomText, new javafx.geometry.Insets(350, 0, 0, 0)); // Insets: top, right, bottom, left
		
		// back to menu button
		ImageView devMenuButton = new ImageView(new Image("about_menu_button.png", 200, 50, false, false));
		StackPane.setMargin(devMenuButton, new javafx.geometry.Insets(515, 500, 0, 0)); // Insets: top, right, bottom, left
		
		// set hover and on click event for back to menu button
		setAboutButtonsEvent(devMenuButton, 0);
		
		// add elements to developer stack pane
		this.devSPane.getChildren().addAll(devName, devText1, devText2, devBottomText, mailIcon, devMenuButton);	
	}
	
	// getter for menu scene
	public Scene getScene() {
		return this.scene;
	}
		
}







