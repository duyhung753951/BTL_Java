package utilz;

public class Constants {
	
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class PlayerConstants{
		//FINN
		public static final int ATTACK_1 = 0;
		public static final int DEATH = 1;
		public static final int TAKE_HIT = 2;
		public static final int IDLE = 3;
		public static final int JUMP = 4;
		public static final int RUNNING = 5;
		
		public static int getSpriteAmount(int player_action) {
			
			//FINN
			switch (player_action) {
			case ATTACK_1:
				return 5;
			case JUMP:
				return 1;
			case TAKE_HIT:
				return 3;
			case IDLE:
				return 9;
			case DEATH:
				return 4;
			case RUNNING:
				return 6;
			default:
				return 1;
			}
			
		}
		
	}
	
}
