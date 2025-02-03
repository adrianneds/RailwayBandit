/***********************************************************
* Railway Bandit - Sprite Class; CMSC 22 WX-4L (Final Project)
*
* @author Adrianne D. Solis
* @created_date 2024-05-03 5:32 PM
*
***********************************************************/

package gameplay;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {
	
	protected double x, y, dx, dy;	 // sprite positioning (x, y) and movement increment (dx, dy)
	
	// attributes for changing sprite frame/animation
	protected int spriteNum = 1;	
	protected Image[] frames;
	protected Image currentFrame;
	protected int spriteCnt = 0;
	
	protected ImageView sImg;		// sprite image
	
	// getters
	double getX() {
		return this.x;
	}
	
	double getY() {
		return this.y;
	}
	
	// setters
	void setX(double x) {
		this.x = x;
	}
	
	void setY(double y) {
		this.y = y;
	}
	
	void setImgFrame(Image newImg) {
		this.sImg.setImage(newImg);
	}
	
	ImageView getImg() {
		return this.sImg;
	}

	// change sprite number, which indicates the frame to be shown
	protected void spriteChange() {
		if (spriteNum == 2) {
			spriteNum = 0;
		} else {
			spriteNum++;
		}
		currentFrame = frames[spriteNum];
	}
	
	// update sprite counter to change frame depending on frame rate 
	// [1] increment sprite count; [2] sprite count > frame rate ? -> change sprite number to change image frame shown
	protected void movementSpriteChange(int frameRate) {
		spriteCnt++;					// increment sprite count variable
		if (spriteCnt > frameRate) {
			spriteChange();				// change sprite number when one round of sprite count increments has exceeded a certain frame rate
			spriteCnt = 0;				// reset sprite count variable
		}
	}
	

}
