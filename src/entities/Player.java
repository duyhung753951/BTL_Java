package entities;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Player extends Entity{

	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;	// 120/12 = 10 animations
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down;
	private float playerSpeed = 2.0f;
	private int attackCounter = 0; 		// tracks the number of attack clicks
	private long lastAttackTime = 0; 	// tracks the last attack time
	private final int comboResetTime = 1000; 	// time in milliseconds to reset the combo
	private boolean canStartNextAttack = true;  // ensures one attack finishes before next starts
	
	public Player(float x, float y) {
		super(x, y);
		loadAnimations();
		
	}
	
	public void update() {
		updatePos();			// set moving
		updateAnimationTick();	// set animation tick
		setAnimation();			// check moving ? running : idle
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, 162*2, 162*2, null);
	}
	
	private void updatePos() {
		
		moving = false;
		
		if(left && !right) {
			x -= playerSpeed;
			moving = true;
		}else if(right && !left) {
			x += playerSpeed;
			moving = true;
		}
		
		if(up && !down) {
			y -= playerSpeed;
			moving = true;
		}else if(down && !up) {
			y += playerSpeed;
			moving = true;
		}
	}
	
	private void updateAnimationTick() {
		
		aniTick++;
		if(aniTick >= aniSpeed) {	// to control the number of frames we draw in 1 second, in here is 10 animations/sec
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= getSpriteAmount(playerAction)) {
				aniIndex = 0;		// reset index of animation
				attacking = false;
				canStartNextAttack = true; // allow the next attack in the combo
			}
		}
	}
	
	
	private void loadAnimations() {
		
		InputStream is = getClass().getResourceAsStream("/Assets/Character/FantasyWarrior/sprites.png");
		
		try {
			BufferedImage img = ImageIO.read(is);
			
			animations = new BufferedImage[9][10];
			for(int j = 0; j < animations.length; j++) {
				for(int i = 0; i < animations[j].length; i++) {
					animations[j][i] = img.getSubimage(i*162, j*162, 162, 162);
				}			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setAnimation() {
		int startAni = playerAction;
		
		if(attacking && canStartNextAttack) {
			long currentTime = System.currentTimeMillis();
			
			// reset combo if it too much time has passed
			if(currentTime - lastAttackTime > comboResetTime) {
				attackCounter = 0;
			}
			
			// Increment attackCounter and set the appropriate attack animation
			attackCounter = (attackCounter % 3) +1;
			if(attackCounter == 1) {
				playerAction = ATTACK_1;
			}else if(attackCounter == 2) {
				playerAction = ATTACK_2;
			} else {
				playerAction = ATTACK_3;
			}
			
			
			lastAttackTime = currentTime;
			canStartNextAttack = false; // prevent starting next attack until this one finishes
		}
		
		else if(!attacking) {
			if(moving) {
				playerAction = RUNNING;
			}else{
				playerAction = IDLE;
			}
		}
	
		if(startAni != playerAction) {	// reset animation
			aniIndex = 0;
			aniTick = 0;
		}
	}

	public void resetDirBoolean() {
		up = down = left = right = false;
	}
	
	public void setAttacking(boolean attacking) {
		if(attacking && canStartNextAttack) {
			this.attacking = attacking;
		}
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}	
	
}
