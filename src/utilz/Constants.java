package utilz;

public class Constants {
	
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class PlayerConstants{
		public static final int ATTACK_1 = 0;
		public static final int ATTACK_2 = 1;
		public static final int ATTACK_3 = 2;
		public static final int DEATH = 3;
		public static final int FALL = 4;
		public static final int IDLE = 5;
		public static final int JUMP = 6;
		public static final int RUNNING = 7;
		public static final int BACKWARD_RUNNING = 8;
		public static final int TAKE_HIT = 9;
		
		public static int getSpriteAmount(int player_action) {
			
			switch (player_action) {
			case ATTACK_1:
			case ATTACK_2:				
			case DEATH:
				return 7;
			case ATTACK_3:
				return 8;
			case FALL:
			case JUMP:
			case TAKE_HIT:
				return 3;
			case IDLE:
				return 9;
			case RUNNING:
				return 8;
			default:
				return 1;
			}
			
		}
		
	}
	
}
