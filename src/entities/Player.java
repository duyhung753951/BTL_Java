package entities;
import static gameState.Gamestate.state;
import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.EnemyConstants.DEAD;
import static utilz.Constants.EnemyConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import audio.AudioPlayer;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


import gameState.Playing;
import main.Game;
import utilz.LoadSave;

import javax.swing.text.LabelView;

public class Player extends Entity{

	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 24;	// 120/24 = 5 animations
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 2f;
	private int[][] lvData;
	private float xDrawOffset = 7 * Game.SCALE;
	private float yDrawOffset = 8 * Game.SCALE;
	private int delay = -1;
	// Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.05f * Game.SCALE;
	private float jumpSpeed = -3.5f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;

	//StatusBarUI
	private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);

	// Health
	private int maxHealth = 30;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;

	// AttackBox
	private Rectangle2D.Float attackBox;

	private int flipX = 0;
	private int flipW = 1;

	private boolean attackChecked;
	private Playing playing;


	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		loadAnimations();
		initHitBox(x, y, (int) (20 * Game.SCALE), (int) (27 * Game.SCALE));
		initAttackBox();
	}

	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}
	public void update() {
		updateHealthBar();

		int playerY = (int)getHitBox().y;
		int deathY = playing.getYlvlOffset() + Game.GAME_HEIGHT - Game.TILES_SIZE;
		System.out.println("Player Y: " + playerY);
		System.out.println("Death Y: " + deathY);
		if (playerY >= deathY && inAir && airSpeed > 2) {
			currentHealth = 0;
		}

		if (currentHealth <= 0) {
			playing.setPlayerDying(true); // Chỉ set dying, chưa game over liền

			// Nếu chưa ở trạng thái DEATH animation
			if (playerAction != DEATH) {
				playerAction = DEATH; // Đổi playerAction sang animation chết
				aniTick = 0;
				aniIndex = 0;
				playing.getGame().getAudioPlayer().stopSong();
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
				delay=350;

			} else {
				if(delay>0) {
					delay -= 1;
					if (delay == 0) {
						playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
					}
				}
				// Nếu animation chết đã chơi xong
				if (aniIndex >= getSpriteAmount(DEATH) - 1 && aniTick >= aniSpeed - 1) {
					playing.setGameOver(true); // Thực sự GameOver
				} else {
					updateAnimationTick(); // Tiếp tục chơi animation chết
				}
			}

			return;
		}


		// Nếu chưa chết thì chơi bình thường
		updateAttackBox();
		updatePos();
		if (attacking)
			checkAttack();
		updateAnimationTick();
		setAnimation();
	}


	public void render(Graphics g, int xLvlOffset, int yLvlOffset) {
		//Finn
		g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset) - xLvlOffset + flipX, (int)(hitbox.y - yDrawOffset) - yLvlOffset, 32*2 *flipW, 32*2, null);
		//		drawHitBox(g, xLvlOffset, yLvlOffset);
//		drawAttackBox(g, xLvlOffset, yLvlOffset);
		drawUI(g);
	}

	private void updatePos() {
		moving = false;

		if (jump)
			jump();

		if (lvData != null && !inAir && !IsEntityOnFloor(hitbox, lvData))
			inAir = true;

		if (!inAir)
			if ((!left && !right) || (right && left))
				return;

		float xSpeed = 0;

		if (left) {
			xSpeed -= playerSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right) {
			xSpeed += playerSpeed;
			flipX = 0;
			flipW = 1;
		}

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvData))
				inAir = true;

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed, lvData);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
		if (left || right)
			moving = true;
	}

	private void jump() {
		if(inAir)
			return;
		playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
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

	public void changeHealth(int value) {
		currentHealth += value;

		if (currentHealth <= 0)
			currentHealth = 0;
		else if (currentHealth >= maxHealth)
			currentHealth = maxHealth;
	}

	private void updateAnimationTick() {

		aniTick++;
		if(aniTick >= aniSpeed) {	// to control the number of frames we draw in 1 second, in here is 10 animations/sec
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= getSpriteAmount(playerAction)) {
				aniIndex = 0;		// reset index of animation
				attacking = false;
				attackChecked = false;
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
		statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
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
		    
		    public int getCurrentHealth() {
		return currentHealth;
		    }

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void setAttacking(boolean attacking) {
		if(attacking) {
			this.attacking = attacking;
		}
	}

	// Phần của Hoàng
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
	}

	private void checkAttack() {
		if (attackChecked || aniIndex != 2)
			return;
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
		playing.getGame().getAudioPlayer().playAttackSound();

	}

	private void updateAttackBox() {
		if (right)
			attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 5);
		else if (left)
			attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 5);

		attackBox.y = hitbox.y + (Game.SCALE * 10);
	}

	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}

	private void drawAttackBox(Graphics g, int lvlOffsetX, int lvlOffsetY) {
		g.setColor(Color.red);
		g.drawRect((int) attackBox.x - lvlOffsetX, (int) attackBox.y - lvlOffsetY, (int) attackBox.width, (int) attackBox.height);

	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
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

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;

		hitbox.x = x;
		hitbox.y = y;

		if (!IsEntityOnFloor(hitbox, lvData))
			inAir = true;
	}

}
