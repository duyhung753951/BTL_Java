package entities;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.EnemyConstants.BOSS_IDLE;

public class Boss extends Enemy{
    // AttackBox
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    private float visionDistance = 8 * Game.TILES_SIZE; // Khoảng cách nhìn xa hơn
    private BufferedImage healthBar;

    public Boss(float x, float y){
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, BOSS);
        initHitBox(x, y, (int) (40 * Game.SCALE), (int) (88 * Game.SCALE));
        initAttackBox();
        // Thiết lập tốc độ di chuyển lớn hơn cho Boss
        walkSpeed = 0.5f * Game.SCALE;
        healthBar = LoadSave.GetSpriteAtlas(LoadSave.BOSS_HEALTH_BAR);
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (40 * Game.SCALE), (int) (112 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 10);
    }

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        if (walkDir == LEFT) {
            attackBox.x = hitbox.x - attackBox.width - attackBoxOffsetX;
        } else {
            attackBox.x = hitbox.x + hitbox.width + attackBoxOffsetX;
        }
        attackBox.y = hitbox.y;
    }

    // Ghi đè phương thức canSeePlayer để sử dụng logic riêng cho Boss
    @Override
    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        // Sử dụng phạm vi Y rộng hơn cho Boss
        float yDifference = Math.abs(player.getHitBox().y - hitbox.y);

        if (yDifference <= Game.TILES_SIZE * 2.0f) { // Cho phép sai số Y lớn hơn
            int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
            if (absValue <= visionDistance) {
                // Kiểm tra tầm nhìn, bỏ qua rào cản
                return true;
            }
        }
        return false;
    }

    // Ghi đè phương thức isPlayerInRange cho Boss
    @Override
    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= visionDistance;
    }

    // Ghi đè phương thức isPlayerCloseForAttack cho Boss
    @Override
    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 1.5; // Tầm tấn công xa hơn một chút
    }

    private void updateBehavior(int[][] lvlData, Player player) {
        tileY = (int) (hitbox.y / Game.TILES_SIZE);
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir)
            updateInAir(lvlData);
        else {
            switch (enemyState) {
                case BOSS_IDLE:
                    // Chỉ chuyển sang RUNNING khi thấy player
                    if (canSeePlayer(lvlData, player)) {
                        newState(BOSS_RUNNING);
                        turnTowardsPlayer(player);
                    }
                    break;
                case BOSS_RUNNING:
                    if (canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player)) {
                            newState(BOSS_ATTACK);
                        }
                    }
                    move(lvlData);
                    break;
                case BOSS_ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;

                    if (aniIndex == 4 && !attackChecked) {
                        checkPlayerHit(attackBox, player);
                        attackChecked = true;
                    }

                    // Khi hoàn thành animation tấn công, quay lại RUNNING
                    if (aniIndex >= 7) {
                        newState(BOSS_RUNNING);
                    }
                    break;

                case BOSS_HIT:
                    // Khi animation bị đánh hoàn thành, quay lại RUNNING
                    if (aniIndex >= GetSpriteAmount(enemyType, BOSS_HIT) - 1)
                        newState(BOSS_RUNNING);
                    break;

                case BOSS_DEAD:
                    // Xử lý trạng thái chết
                    if (aniIndex >= GetSpriteAmount(enemyType, BOSS_DEAD) - 1)
                        active = false;
                    break;
            }
        }
    }

    public void drawAttackBox(Graphics g, int xLvlOffset, int yLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y - yLvlOffset, (int) attackBox.width, (int) attackBox.height);
    }

    public int flipX() {
        if (walkDir == LEFT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDir == LEFT)
            return -1;
        else
            return 1;
    }

    @Override
    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(BOSS_IDLE); // Đảm bảo bắt đầu với BOSS_IDLE
        active = true;
        fallSpeed = 0;
    }
    
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
}
