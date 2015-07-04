package game;

public class SaveState 
{
	PlayState currentState;
	int playerx;
	int playery;
	int playerDirection;
	
	double translateScreenx;
	double translateScreeny;
	
	public SaveState(GameState playState)
	{
		currentState = (PlayState)playState;
		playerx = currentState.player.x;
		playery = currentState.player.y;
		playerDirection = currentState.player.direction;
		
		translateScreenx = currentState.getTranslateX();
		translateScreeny = currentState.getTranslateY();
	}
	
}
