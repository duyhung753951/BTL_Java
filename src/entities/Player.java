package entities;
import static utilz.Constants.PlayerConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


import utilz.LoadSave;

public class Player extends Entity {

	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 24;	// 120/24 = 5 animations
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down;
	private float playerSpeed = 1.0f;
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
		//Finn
		g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, 32*2, 32*2, null);
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
		
		//Test Finn
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.FINN_ATLAS);
		
		animations = new BufferedImage[6][9];
		for(int j = 0; j < animations.length; j++) {
			for(int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i*32, j*32, 32, 32);
			}			
		}
		
	}
	
	private void setAnimation() {
		int startAni = playerAction;
		
		//Finn
		if(attacking) {
			playerAction = ATTACK_1;
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
