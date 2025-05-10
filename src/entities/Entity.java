package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;

	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	// For debugging
	protected void drawHitBox(Graphics g, int xLvlOffset, int yLvlOffset) {
		g.setColor(Color.BLUE);
		g.drawRect((int)hitbox.x - xLvlOffset, (int)hitbox.y - yLvlOffset, (int)hitbox.width, (int)hitbox.height);
	}

	protected void initHitBox(float x, float y, float width, float height) {
		hitbox = new Rectangle2D.Float((int)x,(int)y,width,height);
	}

	public Rectangle2D.Float getHitBox() {
		return hitbox;
	}
}
