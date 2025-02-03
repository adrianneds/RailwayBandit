/***********************************************************
* Railway Bandit - AttackTimer Class; CMSC 22 WX-4L (Final Project)
*
* @author Adrianne D. Solis
* @created_date 2024-05-03 9:48 PM
*
***********************************************************/

package gameplay;

import javafx.animation.AnimationTimer;

public class AttackTimer extends AnimationTimer {
	
	// player object
	private Character player;
	
	// for animation
	private long startTime;			
	private int currentFrame;			// indicates index of current frame to display
	private int cycleNo;				
	static int duration;	
	static int frameRate;

    // constructor
	AttackTimer(Character player) {
		this.player = player;
		duration = 350000000;
		frameRate = 90000000;
	}

	
	// start attack timer, gets startTime
	void start (long startTime) {
		this.startTime = startTime;
		cycleNo = 1;
		
		if (player.getFacing() == 'R') { // first frame to show depending on whether character is facing right/left
			currentFrame = 8;
		} else if (player.getFacing() == 'L') {
			currentFrame = 11;
		}
	}
	
	public void handle(long now) {
		player.setAttack(true);
		if (now - startTime < duration) { // attack duration is 0.35 seconds
			
			// reset frame back to first frame when end frame is reached 
			if (player.getFacing() == 'R') {
				if (currentFrame > 10) {
					currentFrame = 8;
				}
			} else if (player.getFacing() == 'L') {
				if (currentFrame > 13) {
					currentFrame = 11;
				}
			}
			
			player.setImgFrame(player.getFrame(currentFrame)); // set frame for player
			
			if (now - startTime > frameRate*cycleNo) { // update frame every 0.09 seconds
				currentFrame++;
				cycleNo++;
			}
		} else {
			player.setAttack(false);
			this.stop(); // stop when duration is reached
		}
	}
				
}
		
	
	

