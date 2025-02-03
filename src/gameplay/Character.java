/***********************************************************
* Railway Bandit - Character Class; CMSC 22 WX-4L (Final Project)
*
* @author Adrianne D. Solis
* @created_date 2024-05-03 5:46 PM
*
***********************************************************/

package gameplay;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tools.Main;
import javafx.scene.Group;

public class Character extends Sprite {
	
	private int role; // 0 - passenger, 1 - bandit
	private String charName; // name of character (betty, oliver, maggie, or miles)
	
	// player stats
	private double healthPts;
	private double dmgPts;
	private double speedPts;
	private double attackRange;
	private int charID;
	
	// timer for jump movement
	private JumpTimer jumpTimer;

	// pane for charater stats
	private Pane statsPane;
	
	private char facing;	// indicates where the player is facing ('L' - left, 'R' - right); irrelevant when climbing
	
	// frame rate for animation
	private int frameRate; 
	private int idleFrameRate;
	
	private boolean freeze; // indiciates whether the player is frozen due to time machine powerup
	boolean isJumpTimeDone;
	
	// player health bar
	private Group healthBar;							
	private Rectangle healthBarInner;
	private Rectangle healthBarBorder;
	
	// labels for player stats
	private Text statsLabels;
	Text[] statsLabelsValue = new Text[4];

	// font for player stats text
	private Font pixelbook17 = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("PixelBook-Regular.ttf"), 17);
	
	// indicates whether the player is currently performing the respective movements
	boolean jump, down, left, right, climb;
	private boolean attack;
	
	private double yprev;	// previous y coordinate - used in jump timer
	private int storey;		// indicates player's current storey/floor (1 - 1st, 2 - 2nd, 0 - currently climbing)
	
	// constructor
	public Character(float hp, float dp, float sp, float ar, int role, int charID) {
		
		// initialize character stats
		this.healthPts = hp;
		this.dmgPts = dp;
		this.speedPts = sp;
		this.attackRange = ar;
		this.freeze = false;
		this.isJumpTimeDone = true;
		this.role = role;

		// initialize character frame rates for animations
		this.frameRate = 4;
		this.idleFrameRate = 14;
		
		// set charcter name based on ID (0 - betty, 1 - oliver, 2 - maggie, 3 - miles)
		this.charID = charID;
		if (this.charID == 0) {
			this.charName = "betty";
		} else if (this.charID == 1) {
			this.charName = "oliver";
		} else if (this.charID == 2) {
			this.charName = "maggie";
		} else if (this.charID == 3) {
			this.charName = "miles";
		}
		
		// change in character's x-coordinate/x-position per round of horizontal movement
		this.dx = this.speedPts;
		
		// initialize current floor of player
		this.storey = 1;
		
		// flags for controls; initially all false
		jump = false;
		down = false;
		left = false;
		right = false;
		climb = false;
		attack = false;
		
		// retrieve character image frames
		retrieveFrames();
		
		// initialize character imageView
		sImg = new ImageView();
		
		// initialize jump timer
		this.jumpTimer = new JumpTimer(this);
		
		// set health bar properties
		this.healthBarBorder = new Rectangle (this.healthPts,10);
		this.healthBarBorder.setStyle("-fx-stroke: white; -fx-stroke-width: 3;");
		this.healthBarInner = new Rectangle(this.healthPts,10);
		this.healthBarBorder.setViewOrder(0);
		this.healthBarInner.setViewOrder(-1);
		this.healthBar = new Group(healthBarBorder, healthBarInner);	// health bar border and inner portion is set to be part of a group
		
		// stats pane for character stats
		this.statsPane = new Pane();
		
		// character stats labels --- are rounded to 2 decimal places for simplicity
		this.statsLabelsValue[0] = new Text(String.valueOf(Math.floor(this.healthPts)));
		this.statsLabelsValue[1] = new Text(String.valueOf(Math.floor(this.dmgPts)));
		this.statsLabelsValue[2] = new Text(String.valueOf(Math.floor(this.speedPts)));
		this.statsLabelsValue[3] = new Text(String.valueOf(Math.floor(this.attackRange/10)));
		
		// set font style and color for stats labels
		for (int i=0; i<statsLabelsValue.length; i++) {
			statsLabelsValue[i].setFont(pixelbook17);
			statsLabelsValue[i].setFill(Color.web("#FBB67D"));	// off-white
			this.statsPane.getChildren().add(statsLabelsValue[i]);
		}
		
		// set different properties depending on role (0 = passenger, 1 = bandit)
		if (role == 0) {
			
			// passenger is initially facing right
			this.facing = 'R';
			currentFrame = frames[0];
			sImg.setImage(currentFrame);
			
			// initial location
			this.x = GameStage.init_x1;						
			
			// different health bar settings
			this.healthBarInner.setFill(Color.web("#FE6925"));		// color: yellow
			healthBar.setTranslateX(this.x + 50);				
			
			// character stats labels and its location
			statsLabels = new Text("Health:           " +  " Damage:           " + "\nSpeed:           " + " Attack Range: ");
			statsLabels.setX(110);
			statsLabels.setY(570);
			
			// stats labels positioning
			statsLabelsValue[0].setY(570);
			statsLabelsValue[0].setX(160);
			
			statsLabelsValue[1].setY(570);
			statsLabelsValue[1].setX(260);
			
			statsLabelsValue[2].setY(590);
			statsLabelsValue[2].setX(160);
			
			statsLabelsValue[3].setY(590);
			statsLabelsValue[3].setX(290);

		} else if (role == 1) {
			
			// bandit is initially facing left
			this.facing = 'L';
			currentFrame = frames[3];
			sImg.setImage(currentFrame);
			
			// initial location
			this.x = GameStage.init_x2;
			
			// different health bar settings
			this.healthBarInner.setFill(Color.web("#FBDF3F"));  // color: yellow
			healthBar.setTranslateX(this.x + 25);
			
			// character stats labels and its location
			statsLabels = new Text("Health:           " +  " Damage:           " + "\nSpeed:           " + " Attack Range: ");
			statsLabels.setY(570);
			statsLabels.setX(630);
			
			// stats labels positioning
			statsLabelsValue[0].setY(570);
			statsLabelsValue[0].setX(680);
			
			statsLabelsValue[1].setY(570);
			statsLabelsValue[1].setX(810);
			
			statsLabelsValue[2].setY(590);
			statsLabelsValue[2].setX(680);
			
			statsLabelsValue[3].setY(590);
			statsLabelsValue[3].setX(810);
		}
		
		// set character's initial position
		sImg.setX(x);
		this.y = GameStage.storey1_y;
		sImg.setY(this.y);
		
		// color and font style of character stats labels
		statsLabels.setFill(Color.web("#FCEABC"));
		statsLabels.setFont(pixelbook17);
		
		this.statsPane.getChildren().addAll(statsLabels); // add stats labels to stats pane

		healthBar.setTranslateY(this.y - 10); // health bar y-coordinate

	}
	
	// get image frames for movement animations
	public void retrieveFrames() {
		frames = new Image[20];

		// right animation
		frames[0] = new Image(charName + "right1.png",170,170,false,false);
		frames[1] = new Image(charName + "right2.png",170,170,false,false);
		frames[2] = new Image(charName + "right3.png",170,170,false,false);
		
		// left animation
		frames[3] = new Image(charName + "left1.png",170,170,false,false);
		frames[4] = new Image(charName + "left2.png",170,170,false,false);
		frames[5] = new Image(charName + "left3.png",170,170,false,false);
		
		// climb animation
		frames[6] = new Image(charName + "climb1.png",170,170,false,false);
		frames[7] = new Image(charName + "climb2.png",170,170,false,false);
		
		// attack animations
		frames[8] = new Image(charName + "attack2right.png",170,170,false,false);
		frames[9] = new Image(charName + "attack3right.png",170,170,false,false);
		frames[10] = new Image(charName + "attack4right.png",170,170,false,false);
		
		frames[11] = new Image(charName + "attack2left.png",170,170,false,false);
		frames[12] = new Image(charName + "attack3left.png",170,170,false,false);
		frames[13] = new Image(charName + "attack4left.png",170,170,false,false);
	
	}
	
	// changes player animation/image based on movements
	public void draw() {
		
		this.dx = this.speedPts;
		
		// character is idle
		if (right == false && left == false && jump == false && climb == false && down == false && attack == false && freeze == false) {
			this.idle();	// perform idle animation
			movementSpriteChange(this.idleFrameRate);	// change sprite count/sprite number
			return;
		}
		
		// [1] prohibited actions
		
		// player is immobilized due to time machine powerup
		if (this.freeze == true) {
			return;
		}
		
		// player cannot move when defeated
		if (this.healthPts <= 0) { 		
			return;
		}

		// player cannot climb and do other movements at the same time (jump, move right, left, down) 
		// player cannot climb when in storey 2 / floor 2
		if ((climb == true) && (jump == true || right == true || left == true || down == true || storey == 2)) {
			return;
		}
		
		// player cannot jump and move right/left while on the ladder
		if (storey == 0 ) {
		if (jump == true || right == true || left == true) {
			return;
			}
		}
		
		// player cannot climb down while being on storey 1 / floor 1
		if ((down == true && storey == 1)) {
			return;
		}
		
		if (down == true && (left == true || right == true || jump == true)) {
			return;
		}
		
		// [2] make images/animations respond to movements
		
		// move right
		if (right == true) {
			rightFrames();				// set frames to display
			if (x < GameStage.rBound) {	// check if player is beyond game map boundary
				this.x += this.dx;			// edit player location based on speed
			}
			movementSpriteChange(this.frameRate);		// change sprite
		}
		
		if (left == true) {
			leftFrames();
			if (x > GameStage.lBound) {
				this.x -= this.dx;
			}
			movementSpriteChange(this.frameRate);
		}
		
		if (jump == true) {
			
			// set a previous y-coordinate for the jumptimer
			if (this.storey == 1) {
				this.yprev = 400;		
			} else if (this.storey == 2) {
				this.yprev = 51;
			}
			
			jumpTimer.start();			// start the jump timer
		}
		
		if (climb == true) {
			this.storey = 0; // storey is set to 0 while player is climbing
			
			if (this.y == GameStage.storey2_y + 20) {
				currentFrame = frames[0]; // show right-facing frame when player reached storey 2
				this.storey = 2;
				climb = false;
				return;
			}
			
			// adjust y-position by small amount
			this.dy = -2;
			climbFrames();
			this.y += this.dy;
			
			movementSpriteChange(this.frameRate);
		}
		
		if (down == true) {
		this.storey = 0; // storey is also set to 0 while player is climbing down
		if (this.y == 400) {
			climb = false;
			currentFrame = frames[0]; // show right-facing frame when player reached storey 1
			this.storey=1;
			down = false;
			return;
		}
		
		this.dy = 2;
		climbFrames();
		this.y += this.dy;
		movementSpriteChange(this.frameRate);
		
	}

		// set player image and location based on current frame, x, and y as determined by controls
		sImg.setImage(currentFrame);
		sImg.setX(x);
		sImg.setY(y);
		
		// make health bar follow player	
		if (role == 0) {
			healthBar.setTranslateX(this.x + 50);
		} else if (role == 1) {
			healthBar.setTranslateX(this.x + 25);
		}
		healthBar.setTranslateY(this.y - 10);
		
	}
	
	// player image frames for moving right
	private void rightFrames() {
		if (spriteNum == 1) {
			currentFrame = frames[0];
		} else if (spriteNum == 2) {
			currentFrame = frames[1];
		} else if (spriteNum == 3) {
			currentFrame = frames[2];
		}
	}
	
	// player image frames for moving left 
	private void leftFrames() {
		if (spriteNum == 1) {
			currentFrame = frames[3];
		} else if (spriteNum == 2) {
			currentFrame = frames[4];
		} else if (spriteNum == 3) {
			currentFrame = frames[5];
		}
	}
	
	// player image frames for climbing
	private void climbFrames() {
		if (spriteNum == 1) {
			currentFrame = frames[6];
		} else if (spriteNum == 2) {
			currentFrame = frames[7];
		} else if (spriteNum == 3) {
			currentFrame = frames[7];
		}
	}
	
	// idle animation
	private void idle() {
		//System.out.println(spriteNum + " this.y: " + this.getY());
		if (spriteNum == 1) {
			this.sImg.setY(this.getY());
		} else if (spriteNum == 2) {
			this.sImg.setY(this.getY());
		} else if (spriteNum == 3) {
			this.sImg.setY(this.getY()+3);
		}
	}
	
	@Override
	protected void spriteChange() {
		if (spriteNum == 3) {
			spriteNum = 1;
		} else {
			spriteNum++;
		}
	}
	
	
	// getters
	int getCharID() {
		return this.charID; 
	}
	
	String getName() {
		return this.charName;
	}
	
	double getHp() {
		return this.healthPts;
	}
	
	double getDmg() {
		return this.dmgPts;
	}
	
	double getSpeed() {
		return this.speedPts;
	}
	
	double getAR() {
		return this.attackRange;
	}
	
	char getFacing() {
		return this.facing;
	}
	
	int getStorey() {
		return this.storey;
	}
	
	double getYPrev() {
		return this.yprev;
	}
	
	boolean isMovingRight() {
		return this.right;
	}
	
	boolean isMovingLeft() {
		return this.left;
	}
	
	Image getFrame(int i) {
		return this.frames[i];
	}
	
	Pane getStatsPane() {
		return this.statsPane;
	}
	
	Group getHealthBar() {
		return this.healthBar;
	}
	
	int getRole() {
		return this.role;
	}
	
	boolean isFrozen() {
		return this.freeze;
	}
	
	// setters
	void setHp(double newHp) {
		this.healthPts = newHp;
	}
	
	void setDmg(double newDmg) {
		this.dmgPts = newDmg;
	}
	
	void setSpeed(double newSpeed) {
		this.speedPts = newSpeed;
	}
	
	void setAR(double newAR) {
		this.attackRange = newAR;
	}
	
	void setFreeze(boolean value) {
		this.freeze = value;
	}
	
	void setHealthBarY(double y) {
		this.healthBar.setTranslateY(y);
	}
	
	void setHealthBarInnerWidth(double w) {
		this.healthBarInner.setWidth(w);
	}
	
	void setHealthBarBorderWidth(double w) {
		this.healthBarBorder.setWidth(w);
	}
	
	// set indicator for where character is facing
	void setFacing(char f) {
		this.facing = f;
	}
	
	void setAttack(boolean v) {
		this.attack = v;
	}
	
	
}


