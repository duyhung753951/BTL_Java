package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import audio.AudioPlayer;
import gameState.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] crabbyArr;
    private ArrayList<Crabby> crabbies = new ArrayList<>();
    private BufferedImage[][] bossArr;
    private ArrayList<Boss> bosses = new ArrayList<>();
    private BufferedImage bossHealthBar;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();

    }

    public void loadEnemies(Level level) {
        crabbies = level.getCrabs();
        bosses = level.getBosses();
    }

    public void update(int[][] lvlData, Player player) {
       boolean isAnyActive = false;
        for (Crabby c : crabbies)
            if (c.isActive()) {
                c.update(lvlData, player);
                isAnyActive = true;
            }

        for(Boss b:bosses) {
            if (b.isActive()) {
                b.update(lvlData, player);
                isAnyActive = true;
            }
        }
        if(!isAnyActive){
           // playing.getGame().getAudioPlayer().playEffect(AudioPlayer.LVL_COMPLETED);
            if (!playing.isLevelCompleted()) {
                playing.setLevelCompleted(true);
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
        drawCrabs(g, xLvlOffset, yLvlOffset);
        drawBosses(g, xLvlOffset, yLvlOffset);
    }

    private void drawCrabs(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Crabby c : crabbies)
            if (c.isActive()) {
                g.drawImage(crabbyArr[c.getEnemyState()][c.getAniIndex()], (int) c.getHitBox().x - xLvlOffset - CRABBY_DRAWOFFSET_X + c.flipX(), (int) c.getHitBox().y - CRABBY_DRAWOFFSET_Y - yLvlOffset,
                        CRABBY_WIDTH * c.flipW(), CRABBY_HEIGHT, null);
//                c.drawHitBox(g, xLvlOffset, yLvlOffset);
//                c.drawAttackBox(g, xLvlOffset, yLvlOffset);

            }
    }

    private void drawBosses(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Boss b : bosses) {
            if (b.isActive()) {
                // Validate indices to prevent array out of bounds
                g.drawImage(bossArr[b.getEnemyState()][b.getAniIndex()],
                        (int) b.getHitBox().x - xLvlOffset - BOSS_DRAWOFFSET_X + b.flipX(),
                        (int) b.getHitBox().y - BOSS_DRAWOFFSET_Y - yLvlOffset,
                        BOSS_WIDTH * b.flipW(), BOSS_HEIGHT, null);
//                b.drawHitBox(g, xLvlOffset, yLvlOffset);
//                b.drawAttackBox(g, xLvlOffset, yLvlOffset);

                // Draw boss health bar only when boss is visible on screen
                if (isBossVisibleOnScreen(b, xLvlOffset, yLvlOffset)) {
                    drawBossHealthBar(g, b, xLvlOffset, yLvlOffset);
                }
            }
        }

        // Load boss health bar
        bossHealthBar = LoadSave.GetSpriteAtlas(LoadSave.BOSS_HEALTH_BAR);
    }

    private void drawBossHealthBar(Graphics g, Boss b, int xLvlOffset, int yLvlOffset) {
        // Calculate health bar position (centered above the boss)
        int bossHealthBarWidth = 350; // Điều chỉnh chiều rộng
        int bossHealthBarHeight = 80; // Điều chỉnh chiều cao
        int bossHealthBarOffset = 55; // Khoảng cách từ boss đến thanh máu

        int healthBarX = (int) (b.getHitBox().x - xLvlOffset - (int) (bossHealthBarWidth / 2) + (b.getHitBox().width / 2));
        int healthBarY = (int) (b.getHitBox().y - yLvlOffset - bossHealthBarOffset);

        // Tính toán phần trăm máu
        float healthPercentage = (float) b.getCurrentHealth() / b.getMaxHealth();

        // Tính toán kích thước phần máu bên trong
        int healthBarInnerWidth = (int) (bossHealthBarWidth * 0.865f); // 100% chiều rộng của thanh máu
        int healthFillWidth = (int) (healthBarInnerWidth * healthPercentage);

        // Điều chỉnh vị trí và kích thước phần máu bên trong
        int healthStartX = healthBarX + (bossHealthBarWidth - healthBarInnerWidth) / 2; // Căn giữa
        int healthStartY = healthBarY + (bossHealthBarHeight - 12) / 2; // Căn giữa theo chiều dọc

        // Vẽ phần máu (màu đỏ)
        g.setColor(new java.awt.Color(75, 0, 130));
        g.fillRect(healthStartX, healthStartY, healthFillWidth, 15); // Chiều cao của phần máu
        // Vẽ background của thanh máu
        g.drawImage(bossHealthBar, healthBarX, healthBarY, bossHealthBarWidth, bossHealthBarHeight, null);
    }


    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Crabby c : crabbies)
            if (c.isActive())
                if (attackBox.intersects(c.getHitBox())) {
                    c.hurt(10);
                    return;
                }

        for (Boss b : bosses)
            if (b.isActive())
                if (attackBox.intersects(b.getHitBox())) {
                    b.hurt(10);
                    return;
                }
    }

    private void loadEnemyImgs() {
        crabbyArr = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
        for (int j = 0; j < crabbyArr.length; j++)
            for (int i = 0; i < crabbyArr[j].length; i++)
                crabbyArr[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);

        BufferedImage boss = LoadSave.GetSpriteAtlas(LoadSave.BOSS_SPRITE);
        int tempWidth = boss.getWidth();
        int tempHeight = boss.getHeight();

        // Use 5 states as in the EnemyConstants (IDLE, RUNNING, ATTACK, HIT, DEAD)
        bossArr = new BufferedImage[5][8];

        for (int j = 0; j < bossArr.length; j++) {
            for (int i = 0; i < bossArr[j].length; i++) {
                bossArr[j][i] = boss.getSubimage(i * BOSS_WIDTH_DEFAULT, j * BOSS_HEIGHT_DEFAULT, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
            }
        }

        // Load boss health bar
        bossHealthBar = LoadSave.GetSpriteAtlas(LoadSave.BOSS_HEALTH_BAR);
    }

    public void resetAllEnemies() {
        for (Crabby c : crabbies)
            c.resetEnemy();
        for(Boss b:bosses)
            b.resetEnemy();
    }
    
    private boolean isBossVisibleOnScreen(Boss boss, int xLvlOffset, int yLvlOffset) {
        // Check if boss is within the viewable screen area
        if (!boss.isActive())
            return false;
            
        // Get boss position
        float bossX = boss.getHitBox().x - xLvlOffset;
        float bossY = boss.getHitBox().y - yLvlOffset;
        
        // Check if boss is within screen bounds
        return (bossX < playing.getGame().GAME_WIDTH && bossX + BOSS_WIDTH > 0 &&
                bossY < playing.getGame().GAME_HEIGHT && bossY + BOSS_HEIGHT > 0);
    }

}