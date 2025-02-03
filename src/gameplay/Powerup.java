/***********************************************************
* Railway Bandit - Powerup Class; CMSC 22 WX-4L (Final Project)
*
* @author Adrianne D. Solis
* @created_date 2024-05-09 8:32 PM
*
***********************************************************/

package gameplay;

import java.util.Random;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Powerup extends Sprite {
	
	private int assignedNum;	
	// 1 - electric apple, 2 - feather, 3 - goggles, 4 - gear, 5 - time machine,
	// 6 - poisoned apple, 7 - heavy boots, 8 - weakening potion
	
	// attributes for powerup animation
	private long effectDuration;
	private int frameRate;
	
	// static attribute for generating random spawn interval and location
	static Random random;
	static long spawnInterval = 5000;
	
	// overloading constructor
	Powerup(int assignedNum) {
		this.assignedNum = assignedNum;
		random = new Random();
		this.sImg = new ImageView();
		
		// initialize needed attributes and images for animation
		this.frames = new Image[3];
		retrievePowerupFrames();
		this.frameRate = 12;
		
		// assigns effect duration depending on type of powerup
		if (this.assignedNum == 2 || this.assignedNum == 7) {
			this.effectDuration = 10000;
		} else if (this.assignedNum == 5) {
			this.effectDuration = 5000;
		} else {
			this.effectDuration = 0;
		}
	}
	
	// generate random position for powerup (in an area where players can pick it up)
	private void generatePosition() {
		double yRandom = 55;
		double xRandom = 15 + random.nextInt(801);	// generate random number from 15 to 815 (x coordinate where player can pick up powerup)
		
		int choice = 1 + random.nextInt(2);			// randomly choose whether to spawn powerup in storey 1 or storey 2
		if (choice == 1) {
			yRandom = 400;
		} else if (choice == 2) { 
			yRandom = 50;
		}
		
		// set powerup position
		this.x = xRandom;
		this.y = yRandom;
	}
	
	// generate duration of powerup availability and waiting time from powerup despawn to next powerup spawn
	static void generateSpawnInterval() {
		spawnInterval = 15 + random.nextLong(31);
		spawnInterval = spawnInterval*1000;
	}
	
	// spawn powerup
	void spawn() {
		this.generatePosition();	// generate random position
		
		// show powerup image
		sImg.setImage(frames[0]);
		sImg.setTranslateX(this.x);
		sImg.setTranslateY(this.y);
	}
	
	// powerup image frames
	private void retrievePowerupFrames() {
	
		if (this.assignedNum == 1) {
			// electric apple
			frames[0] = new Image("apple1.png", 100, 100, false, false);
			frames[1] = new Image("apple2.png", 100, 100, false, false);
			frames[2] = new Image("apple3.png", 100, 100, false, false);
		} else if (this.assignedNum == 2) {
			// feather
			frames[0] = new Image("feather1.png", 100, 100, false, false);
			frames[1] = new Image("feather2.png", 100, 100, false, false);
			frames[2] = new Image("feather3.png", 100, 100, false, false);
		} else if (this.assignedNum == 3) {
			// goggles
			frames[0] = new Image("goggles1.png", 100, 100, false, false);
			frames[1] = new Image("goggles2.png", 100, 100, false, false);
			frames[2] = new Image("goggles3.png", 100, 100, false, false);
		} else if (this.assignedNum == 4) {
			// gear
			frames[0] = new Image("gear1.png", 100, 100, false, false);
			frames[1] = new Image("gear2.png", 100, 100, false, false);
			frames[2] = new Image("gear3.png", 100, 100, false, false);
		} else if (this.assignedNum == 5) {
			// time machine
			frames[0] = new Image("time1.png", 100, 100, false, false);
			frames[1] = new Image("time2.png", 100, 100, false, false);
			frames[2] = new Image("time3.png", 100, 100, false, false);	
		} else if (this.assignedNum == 6) {
			// poisoned apple
			frames[0] = new Image("papple1.png", 100, 100, false, false);
			frames[1] = new Image("papple2.png", 100, 100, false, false);
			frames[2] = new Image("papple3.png", 100, 100, false, false);	
		} else if (this.assignedNum == 7) {
			// heavy boots
			frames[0] = new Image("boots1.png", 100, 100, false, false);
			frames[1] = new Image("boots2.png", 100, 100, false, false);
			frames[2] = new Image("boots3.png", 100, 100, false, false);	
		} else if (this.assignedNum == 8) {
			// weakening potion
			frames[0] = new Image("potion1.png", 100, 100, false, false);
			frames[1] = new Image("potion2.png", 100, 100, false, false);
			frames[2] = new Image("potion3.png", 100, 100, false, false);		
		}
		
	}
	
	// powerup idle animation
	void powerupAnimation() {
		sImg.setImage(currentFrame);
		movementSpriteChange(this.frameRate);
	}
	
	// getter for powerup duration
	long getEffectDuration() {
		return this.effectDuration;
	}
	
	int getAssignedNum() {
		return this.assignedNum;
	}
	
	
	
	

}
