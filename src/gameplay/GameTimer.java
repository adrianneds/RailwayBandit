/***********************************************************
* Railway Bandit - GameTimer Class; CMSC 22 WX-4L (Final Project)
*
* @author Adrianne D. Solis
* @created_date 2024-05-03 5:51 PM
*
***********************************************************/

package gameplay;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import tools.SoundController;

public class GameTimer extends AnimationTimer {
	
	private Scene scene;	     // scene
	private GameStage gamestage; // to store gamestage
	
	// players
	private Character player1; // passenger
	private Character player2; // bandit
	private Character winner;  // object to store winning player
	
	private SoundController soundController;	// sound controller for BGM and SFX
	
	// attack timer for player 1 and 2
	private AttackTimer attackTimer1;
	private AttackTimer attackTimer2;
	
	// attributes for powerup spawn/de-spawn
	private Random random;
	private boolean isPowerupActive;		// indicates if a powerup is active
	private boolean startPowerup;		
	private boolean powerupCollected;		// indicates if currently active powerup is collected
	private long startTime;
	
	// attributes for timed powerups (feather, time machine, heavy boots)
	private boolean timedPowerupActive;	// indicates if a timed powerup is active
	private Powerup timedPowerup;		// stores object of timed powerup
	private Character timedPlayer;	// store player being timed
	private long timedPowerupStartTime;
	
	// powerup objects
	private Powerup eapple;
	private Powerup feather;
	private Powerup goggles;
	private Powerup gear;
	private Powerup timeMachine;
	private Powerup poisonedApple;
	private Powerup heavyBoots;
	private Powerup activePowerup;
	private Powerup weakeningPotion;
	
	final static int ladderDistance = 12;	// allowable distance of player and ladder for player to climb

	// constructor
	public GameTimer(GameStage gamestage, Scene scene, StackPane root, Character player1, Character player2) {
		this.scene = scene;
		this.player1 = player1;
		this.player2 = player2;
		this.gamestage = gamestage;
		this.soundController = new SoundController();

		// initially, no powerups are active
		this.isPowerupActive = false;
		this.startPowerup = false;
		
		// instantiate the attack timers
		this.attackTimer1 = new AttackTimer(player1);
		this.attackTimer2 = new AttackTimer(player2);
		
		this.startTime = System.currentTimeMillis();
		startPowerup = true;			
		
		random = new Random();			// random number generator used for spawning powerups
		
		// instantiate powerup obejcts
		this.eapple = new Powerup(1);
		this.feather = new Powerup(2);
		this.goggles = new Powerup(3);
		this.gear = new Powerup(4);
		this.timeMachine = new Powerup(5);
		this.poisonedApple = new Powerup(6);
		this.heavyBoots = new Powerup(7);
		this.weakeningPotion = new Powerup(8);
		
		// set key press events (player controls)
		this.handleKeyPressEvent();
	
	}

	// set on key press/released events for character controls
	private void handleKeyPressEvent() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent e) {
				
				// player 1 controls: W, S, A, D, X
				// player 2 controls: UP, DOWN, LEFT, RIGHT, SLASH
				
				if (e.getCode() == KeyCode.UP) {		
					// check if player is within allowable distance with ladder to climb
					if (player2.isJumpTimeDone == true && Math.abs(player2.x - GameStage.leftLadder_x) <= ladderDistance || Math.abs(player2.x - GameStage.rightLadder_x) <= ladderDistance) {
						player2.climb = true;
						soundController.playSFX(SoundController.Climb);	// climb sfx
					} else {
						// if not, player will jump
						player2.jump = true;
					}
				}
				if (e.getCode() == KeyCode.W) {
					if (player1.isJumpTimeDone == true && Math.abs(player1.x - GameStage.leftLadder_x) <= ladderDistance || Math.abs(player1.x - GameStage.rightLadder_x) <= ladderDistance) {
						player1.climb = true;
						soundController.playSFX(SoundController.Climb);
					} else {
						player1.jump = true;
					}
				}	
				if (e.getCode() == KeyCode.DOWN) {
					if (player2.isJumpTimeDone == true && Math.abs(player2.x - GameStage.leftLadder_x) <= ladderDistance || Math.abs(player2.x - GameStage.rightLadder_x) <= ladderDistance) {
						player2.down = true;
						soundController.playSFX(SoundController.Climb);
					}
				}
				if (e.getCode() == KeyCode.S) {
					if (player1.isJumpTimeDone == true && Math.abs(player1.x - GameStage.leftLadder_x) <= ladderDistance || Math.abs(player1.x - GameStage.rightLadder_x) <= ladderDistance) {
						player1.down = true;
						soundController.playSFX(SoundController.Climb);
					}
				}
				if (e.getCode() == KeyCode.LEFT) {
					player2.left = true;
					player2.setFacing('L');	// set facing attribute for character (L/R)
				}
				if (e.getCode() == KeyCode.A) {
					player1.left = true;
					player1.setFacing('L');
				}
				if (e.getCode() == KeyCode.RIGHT) {
					player2.right = true;
					player2.setFacing('R');
				}
				if (e.getCode() == KeyCode.D) {
					player1.right = true;
					player1.setFacing('R');
				}
				if (e.getCode() == KeyCode.X) {
					// check if opponent is within player's attack range and player is not frozen
					if (   player1.down == false &&  player1.climb == false && player1.isFrozen() == false && (Math.abs(player2.getX() - player1.getX())  <= player1.getAR())  &&    (Math.abs(player2.getY() - player1.getY()) <= player1.getAR()) ) { 
						attackEvent(player1,player2);						 
						soundController.playSFX(SoundController.Attack);	 // attack sfx
						gamestage.reorderCharacters(player1);				 // put attacker on top of the game stack pane
					}
				}
				if (e.getCode() == KeyCode.SLASH) {
					if (  player2.down == false && player2.isFrozen() == false && (Math.abs(player1.getX() - player2.getX())  <= player2.getAR())  &&    (Math.abs(player1.getY() - player2.getY()) <= player2.getAR()) ) { 
						attackEvent(player2, player1);
						soundController.playSFX(SoundController.Attack);	
						gamestage.reorderCharacters(player2);               
					}
				}
			}

		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent e) {
				
				if (e.getCode() == KeyCode.UP) {
					player2.jump = false;
					player2.climb = false;
					soundController.stopSFX(SoundController.Climb);	// stop climb sfx once player stops climbing up
				}
				if (e.getCode() == KeyCode.W) {
					player1.jump = false;
					player1.climb = false;
					soundController.stopSFX(SoundController.Climb); 
				}	
				if (e.getCode() == KeyCode.DOWN) {
					player2.down = false;
					soundController.stopSFX(SoundController.Climb);	// stop climb sfx once player stops climbing down
				}
				if (e.getCode() == KeyCode.S) {
					player1.down = false;
					soundController.stopSFX(SoundController.Climb);
				}
				if (e.getCode() == KeyCode.LEFT) {
					player2.left = false;
				}
				if (e.getCode() == KeyCode.A) {
					player1.left = false;
				}
				if (e.getCode() == KeyCode.RIGHT) {
					player2.right = false;
				}
				if (e.getCode() == KeyCode.D) {
					player1.right = false;
				}
			}

		});
	}

	@Override
	public void handle(long now) {	
		// this code block will run continuously as long as the game timer is active
		
		// make player imageViews respond to controls
		player1.draw();	
		player2.draw();
		
		if (player1.getHp() <= 0 || player2.getHp() <= 0) { // game ends when one player has 0 or less health points
			if (player1.getHp() <= 0) {			// set winner of the game
				this.winner = player2;
			} else {
				this.winner = player1;
			}
			try {
				Thread.sleep(300); 						// pause for a while before showing winning scene
			} catch (InterruptedException e) {}			
			this.stop();								// stop game timer
			gamestage.endGame(this.winner);				// show winning scene 
		}
		
		// activate powerup timer to keep track of powerup spawn and de-spawn
		powerupTimer();
		
		// activate timer for timed powerups
		timedPowerupTimer();
		
		try {
			activePowerup.powerupAnimation();	
			detectPowerup(player1);				// activate powerup detector
			detectPowerup(player2);
		} catch (NullPointerException e) {}		// catch for null pointer exception since there may be no current active powerup (null)
		
	}
	
	// timer to keep track of powerup spawn-despawn
	private void powerupTimer() {	
		
		if (startPowerup == true) {	// check if powerup spawning is allowed to start
			
			// the spawn interval indicates the time between 1) spawn and despawn of the same powerup, 2) despawn and next spawn of another powerup
			// only one powerup at a time can be spawned
			
			if (System.currentTimeMillis() - startTime > Powerup.spawnInterval) {	// check if interval has elapsed
				// either possibly spawn new powerup or end despawn
		
				// check if there is a currently active powerup
				if (this.isPowerupActive == false) {
					this.determinePowerupSpawn();	// determine if a powerup should be spawned; spawn will follow if yes
				} else if (this.isPowerupActive == true) {
					this.powerupDespawn();			// despawn powerup 
				}
			}
			
		}
		
	}
	
	// despawn a powerup
	private void powerupDespawn() {

		gamestage.removeFromPowerupPane(activePowerup);	// remove active powerup from the powerup pane
		activePowerup = null;
		this.isPowerupActive = false;
		Powerup.generateSpawnInterval();	// generate a new spawn interval; in this case, the interval between despawn and the next powerup to spawn
	}
	
	// method to randomly determine if a powerup will be spawned
	private void determinePowerupSpawn() {
		int willPowerupSpawn = 1 + random.nextInt(2);	// random number (RN) to determine powerup spawn status
		if (willPowerupSpawn == 1 || willPowerupSpawn == 2) {		// 	RN = 1 or RN = 2 = spawn a random powerup (or reverse powerup)			
			spawnPowerup();										
		} else if (willPowerupSpawn == 3) {							// RN = 3; not spawn
			// wait for another 15-45 seconds
			this.setPowerupTimer();					// reset powerup timer
		}
	}
	
	// method to reset powerup timer
	private void setPowerupTimer() {
		startTime = System.currentTimeMillis();		// set new start time
		Powerup.generateSpawnInterval();			// generate new spawn interval				
	}
	
	// spawn a powerup
	private void spawnPowerup() {
		this.soundController.playSFX(SoundController.Pop);	// powerup spawn sfx
		powerupCollected = false;							// powerup initially not yet collected
		
		// determine powerup to spawn				// random number (RN) range from 1 to 7
		int powerupToSpawn = 1 + random.nextInt(8);	// RN = 1 -> electric apple, 2 -> feather, 3 -> goggles, 4 -> gear, 5 -> time machine
		switch (powerupToSpawn) {					// 6 -> poisoned apple, 7 -> heavy boots, 8 -> weakening potion
		case 1:
			// spawn apple
			eapple.spawn();
			activePowerup = eapple;
			gamestage.addToPowerupPane(eapple);	// show powerup image
			break;
		case 2:
			// spawn feather
			feather.spawn();
			activePowerup = feather;
			gamestage.addToPowerupPane(feather);
			break;
		case 3: 
			// spawn goggles
			goggles.spawn();
			activePowerup = goggles;
			gamestage.addToPowerupPane(goggles);
			break;
		case 4: 
			// spawn gear
			gear.spawn();
			activePowerup = gear;
			gamestage.addToPowerupPane(gear);
			break;
		case 5: 
			// spawn time machine
			timeMachine.spawn();
			activePowerup = timeMachine;
			gamestage.addToPowerupPane(timeMachine);
			break;
		 case 6:
			// spawn poisoned apple
			poisonedApple.spawn();
			activePowerup = poisonedApple;
			gamestage.addToPowerupPane(poisonedApple);
			break;
		 case 7:
			 // spawn heavy boots
			 heavyBoots.spawn();
			 activePowerup = heavyBoots;
			 gamestage.addToPowerupPane(heavyBoots);
			 break;
		 case 8:
			 // spawn weakening potion
			 weakeningPotion.spawn();
			 activePowerup = weakeningPotion;
			 gamestage.addToPowerupPane(weakeningPotion);
		}
		
		this.isPowerupActive = true;  // indicate that a powerup is now active through isPowerupActive variable
		this.setPowerupTimer();		  // reset powerup timer
	}
	
	// detect if player picks up powerup
	private void detectPowerup(Character player) {
		// check if player is near powerup with allowable distance of 20px
		if (Math.abs(player.getX() - activePowerup.getX()) <= 20 && (Math.abs(player.getY() - activePowerup.getY()) <= 20)) {
			//System.out.println("Powerup picked up!");	// NOTE: for checking
			updatePlayerStats(player);	// update player stats depending on powerup picked up
			powerupDespawn();			// despawn the powerup since it is now picked up
		}
	}
	
	// update player stats upon powerup pickup
	private void updatePlayerStats(Character player) {
		this.soundController.playSFX(SoundController.Powerup);	// powerup pickup sfx
		if (powerupCollected == false) {	
			if (activePowerup == eapple) {	
				// electric apple , +8 health
				player.setHp(Math.floor(player.getHp()+8));
				player.setHealthBarBorderWidth(player.getHp());
				player.setHealthBarInnerWidth(player.getHp());
				player.statsLabelsValue[0].setText(String.valueOf(player.getHp()));
			} else if (activePowerup == feather) {
				// feather, + 3 speed for 10s
				player.setSpeed(Math.floor(player.getSpeed()+3));
				player.statsLabelsValue[2].setText(String.valueOf(player.getSpeed()));
				this.setTimedPowerupTimer(activePowerup, player);
			} else if (activePowerup == goggles) {
				// goggles, +0.8 attack range (note: +8 in terms of true attack range, but +0.8 for display purposes of player stats)
				player.setAR(player.getAR()+8);
				player.statsLabelsValue[3].setText(String.valueOf(player.getAR()/10));
			} else if (activePowerup == gear) {
				// gear, +0.7 damage
				player.setDmg(player.getDmg()+0.70);
				player.statsLabelsValue[1].setText(String.format("%.2f", player.getDmg()));
			} else if (activePowerup == timeMachine) {
				// time machine, immobilize player for 5 seconds
				if (player == player1) {
					player2.setFreeze(true);
				} else {
					player1.setFreeze(true);
				}
				this.setTimedPowerupTimer(activePowerup, player);
			} else if (activePowerup == poisonedApple) {
				// poisoned apple, decreases health by 10 points
				player.setHp(Math.floor(player.getHp()-10));
				player.setHealthBarInnerWidth(player.getHp());
				player.statsLabelsValue[0].setText(String.valueOf(player.getHp()));
			} else if (activePowerup == heavyBoots) {
				// heavy boots, decrease player speed by 4.5 pts for 10s
				player.setSpeed(Math.floor(player.getSpeed()-4.5));
				player.statsLabelsValue[2].setText(String.valueOf(player.getSpeed()));
				this.setTimedPowerupTimer(activePowerup, player);
			} else if (activePowerup == weakeningPotion) {
				// weakening potion, decrease damage by 1.5 pts
				player.setDmg(player.getDmg()-1.5);
				player.statsLabelsValue[1].setText(String.format("%.2f", player.getDmg()));
			}
			powerupCollected = true;	// indicate that powerup is collected
		}
	}
	
	// timer for timed powerups (feather, time machine, heavy boots)
	private void timedPowerupTimer() {
		if (timedPowerupActive == true) { // check if a timed powerup is picked up
			gamestage.editPowerupTimerText(timedPowerup.getEffectDuration(), System.currentTimeMillis() - timedPowerupStartTime);
			if (System.currentTimeMillis() - timedPowerupStartTime > timedPowerup.getEffectDuration()) { // check if the timed powerup's effect duration has elapsed
				gamestage.removePowerupTimerFrame();
				// timer done	
				// revert character stats back to previous depending on the timed powerup 
				if (timedPowerup == feather) { 
					timedPlayer.setSpeed(timedPlayer.getSpeed()-3);
					timedPlayer.statsLabelsValue[2].setText(String.valueOf(timedPlayer.getSpeed()));
				} else if (timedPowerup == timeMachine) {
					if (timedPlayer == player1) {
						player2.setFreeze(false);
					} else {
						player1.setFreeze(false);
					}
				} else if (timedPowerup == heavyBoots) {
					timedPlayer.setSpeed(timedPlayer.getSpeed()+4.5);
					timedPlayer.statsLabelsValue[2].setText(String.valueOf(timedPlayer.getSpeed()));
				}
				timedPowerupActive = false;
			}
		}
	}
		
	// set timer for timed powerups (feather, time machine, heavy boots)
	private void setTimedPowerupTimer(Powerup powerup, Character player) {
		timedPowerupStartTime = System.currentTimeMillis();
		timedPowerupActive = true;
		timedPlayer = player;
		timedPowerup = powerup;
		timedPowerupTimer();
		gamestage.showPowerupTimer(timedPlayer, timedPowerup.getAssignedNum());
	}
		
	// method for character attack
	private void attackEvent(Character attacker, Character attackedPlayer) {
		AttackTimer attackTimer;	
		
		// set attack timer object depenidng on the attacker
		if (attacker == player1) {
			attackTimer = attackTimer1; 
		} else {
			attackTimer = attackTimer2;
		}
		
		// start attack timer
		attackTimer.start(System.nanoTime());
		attackTimer.start();								// attack animation
		
		attackedPlayer.setHp(Math.floor(attackedPlayer.getHp()-(attacker.getDmg()*0.20)));   // change opponent's health points
		attackedPlayer.setHealthBarInnerWidth(attackedPlayer.getHp());	                     // edit opponent's health bar
		attackedPlayer.statsLabelsValue[0].setText(String.valueOf(attackedPlayer.getHp()));  // edit opponent's health points label
	}

}
