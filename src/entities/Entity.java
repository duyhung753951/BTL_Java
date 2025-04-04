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

	protected void drawHitBox(Graphics g) {
		// For debugging
		g.setColor(Color.RED);
		g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
	}

	protected void initHitBox(float x, float y, float width, float height) {
		hitbox = new Rectangle2D.Float((int)x,(int)y,width,height);
	}
//	public void updateHitBox() {
//		hitbox.x = (int)x;
//		hitbox.y = (int)y;
//	}
	public Rectangle2D.Float getHitBox() {
		return hitbox;
	}
}
