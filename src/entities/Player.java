package entities;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


import main.Game;
import utilz.LoadSave;

public class Player extends Entity{

	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 24;	// 120/24 = 5 animations
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 1.2f;
	private int[][] lvData;
	private float xDrawOffset = 7 * Game.SCALE;
	private float yDrawOffset = 8 * Game.SCALE;

	// Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.05f * Game.SCALE;
	private float jumpSpeed = -3f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitBox(x, y, 20 * Game.SCALE, 26 * Game.SCALE);
	}
	
	public void update() {
		updatePos();			// set moving
		updateAnimationTick();	// set animation tick
		setAnimation();			// check moving ? running : idle
	}
	
	public void render(Graphics g) {
		//Finn
		g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 32*2, 32*2, null);
//		drawHitBox(g);
	}
	
	private void updatePos() {
		moving = false;
		float xSpeed = 0;

		if (left)
			xSpeed -= playerSpeed;
		if (right)
			xSpeed += playerSpeed;

		if (jump)
			jump();

		// Xử lý ngang (X Axis)
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvData)) {
			hitbox.x += xSpeed;
			if(xSpeed != 0) moving = true;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}

		// Xử lý dọc (Y Axis)
		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed, lvData);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
			}
		} else {
			// Đảm bảo nhân vật bắt đầu rơi khi không còn đất phía dưới
			if (!IsEntityOnFloor(hitbox, lvData))
				inAir = true;
		}
	}

	private void jump() {
		if(inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if(CanMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvData)){
			hitbox.x += xSpeed;
		}else{
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
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
			}
		}
	}
	
	
	private void loadAnimations() {
		
		//Test Finn
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.FINN_ATLAS);
		
		animations = new BufferedImage[6][9];
		for(int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
		}
	}

	public void loadLvData(int[][] lvData) {
		this.lvData = lvData;
	}
	
	private void setAnimation() {
		int startAni = playerAction;
		
		//Finn
		if(moving) {
			playerAction = RUNNING;
		}
		else {
			playerAction = IDLE;
		}

		if(inAir) {
			playerAction = JUMP;
		}

		if(attacking) {
			playerAction = ATTACK_1;
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
		if(attacking) {
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

	public void setJump(boolean jump) {
		this.jump = jump;
	}

}
