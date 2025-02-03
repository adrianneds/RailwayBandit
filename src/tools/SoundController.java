/***********************************************************
* Railway Bandit - SoundController Class; CMSC 22 WX-4L (Final Project)
*
* @author Adrianne D. Solis
* @created_date 2024-05-10 9:27 PM
*
***********************************************************/

package tools;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

public class SoundController {
	
	// background music (BGM0
	private MediaPlayer menuBGMPlayer;	// menu BGM
	private MediaPlayer gameBGMPlayer;	// in-game BGM
	
	// sound effects (SFX; quick, short audio clips)
	private AudioClip clicksfx;	    // click SFX
	private AudioClip climbsfx;     // ladder climbing SFX
	private AudioClip popsfx;       // pop SFX (for spawning powerups/reverse powerups)
	private AudioClip powerupsfx;   // powerup pickup SFX
	private AudioClip winsfx;		// player win SFX
	private AudioClip attacksfx;	// player attack SFX
	
	// Strings used to play sound effects or BGM
	public static final String Menu = "menu";
	public static final String Game = "game";
	public static final String Click = "click";
	public static final String Climb = "climb";
	public static final String Pop = "pop";
	public static final String Powerup = "powerup";
	public static final String Win = "win";
	public static final String Attack = "attack";

	public SoundController() {
		
		// initialize BGM (MediaPlayer objects)
		this.menuBGMPlayer = new MediaPlayer( new Media (getClass().getResource("/menubgm.mp3").toString()) );
		this.gameBGMPlayer = new MediaPlayer( new Media (getClass().getResource("/gameplaybgm.mp3").toString()) );
		
		// set media properties for BGM
		this.setMediaProperties(this.menuBGMPlayer, (float) 0.4);
		this.setMediaProperties(this.gameBGMPlayer, (float) 0.05);
		
		// intialize SFX (AudioClip objects)
		this.clicksfx = new AudioClip(getClass().getResource("/clicksfx.mp3").toString());
		this.climbsfx = new AudioClip(getClass().getResource("/climbsfx.mp3").toString());
		this.popsfx =  new AudioClip(getClass().getResource("/popsfx.mp3").toString());
		this.powerupsfx = new AudioClip(getClass().getResource("/powerupsfx.mp3").toString());
		this.winsfx = new AudioClip(getClass().getResource("/winsfx.mp3").toString());
		this.attacksfx = new AudioClip(getClass().getResource("/attacksfx.mp3").toString());
		
		// set volume of sfx
		popsfx.setVolume(0.2);
		powerupsfx.setVolume(0.2);
		winsfx.setVolume(0.3);
		attacksfx.setVolume(0.6);
	}

	// pause BGM
	public void pause(String s) {
		if (s.equals(SoundController.Menu)) {
			this.menuBGMPlayer.pause();
		} else if (s.equals(SoundController.Game)) {
			this.gameBGMPlayer.stop();
		}
	}
	
	// set volume and cycle count of BGM
	private void setMediaProperties(MediaPlayer mediaPlayer, float volume) {
		mediaPlayer.setVolume(volume);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	}
	
	// play a certain BGM
	public void playBGM(String s) {
		if (s.equals(SoundController.Menu)) {
			this.menuBGMPlayer.play();
		} else if (s.equals(SoundController.Game)) {
			this.gameBGMPlayer.play();
		}
	}
	
	// stop a certain BGM
	public void stopBGM(String s) {
		if (s.equals(SoundController.Menu)) {
			this.menuBGMPlayer.stop();
		} else if (s.equals(SoundController.Game)) {
			this.gameBGMPlayer.stop();
		}
	}
	
	// play a certain SFX
	public void playSFX(String s) {
		if (s.equals(SoundController.Click)) {
			this.clicksfx.play();
		} else if (s.equals(SoundController.Climb)) {
			this.climbsfx.play();
		} else if (s.equals(SoundController.Pop)) {
			this.popsfx.play();
		} else if (s.equals(SoundController.Powerup)) {
			this.powerupsfx.play();
		} else if (s.equals(SoundController.Win)) {
			this.winsfx.play();
		} else if (s.equals(SoundController.Attack)) {
			this.attacksfx.play();
		}
	}
	
	// forcibly stop a certain SFX
	public void stopSFX(String s) {
		if (s.equals(SoundController.Click)) {
			this.clicksfx.stop();
		} else if (s.equals(SoundController.Climb)) {
			this.climbsfx.stop();
		} else if (s.equals(SoundController.Pop)) {
			this.popsfx.stop();
		} else if (s.equals(SoundController.Powerup)) {
			this.powerupsfx.stop();
		} else if (s.equals(SoundController.Win)) {
			this.winsfx.stop();
		} else if (s.equals(SoundController.Attack)) {
			this.attacksfx.stop();
		}
	}
	
	
}
