/***********************************************************
* Railway Bandit - JumpTimer Class; CMSC 22 WX-4L (Final Project)
*
* @author Adrianne D. Solis
* @created_date 2024-05-03 11:41 PM
*
***********************************************************/

package gameplay;

import javafx.animation.AnimationTimer;

public class JumpTimer extends AnimationTimer {
	
	private Character player;
	private double gravity;
	
    // constructor
	public JumpTimer(Character player) {
		gravity = 0;
		this.player = player;
	}
	
	public void handle(long now) {
		player.isJumpTimeDone = false;
		// x-movements depending on if player is also moving left/right while jumping
		if (player.isMovingRight() == true) {
			if (player.getX() + 3 < GameStage.rBound) {
				player.setX(player.getX() + 3);
			}
		} else if (player.isMovingLeft() == true) {
			if (player.getX() - 3 > GameStage.lBound) {
				player.setX(player.getX() - 3);
			}
		}
		
		// the jump -- decrease and continuously increase player y-coordinate
		player.setY(player.getY() - 10 + gravity);
		gravity += 1;
		player.setHealthBarY(player.getY() - 10);	// make health bar follow player as it jumps
		
		if (player.getY() > 400) {
			this.stop();	 // control measure for out of bounds Y
		}
		
		if (player.getStorey() == 2) {
			if (player.getY() == 70) {
				player.isJumpTimeDone = true;
				gravity = 0;
				this.stop(); // stop jump when previous y = 70 is reached (adjustment for storey 2)
			}
		} else if (player.getStorey() == 1) {
			if (player.getYPrev() == player.getY()) {
				player.isJumpTimeDone = true;
				gravity = 0;
				this.stop(); // stop jump when previous y-location is reached
			}
		} else if (player.getStorey() == 0) {
			this.stop();	// control measure for jumping while climbing
		}
	}
				
}
		
	
	

