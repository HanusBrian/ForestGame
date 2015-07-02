/*
 *	GameStateManager is responsible for switching between the 
 *	game states.
 *
 */

package game;

public class GameStateManager 
{
	
	public GameState[] gameStates;
	int currentState;
	private int previousState;
	
	public static final int NUM_STATES = 4;
	
	public static final int INTRO = 0;
	public static final int MENU = 1;
	public static final int PLAY = 2;
	public static final int FISH = 3;
	
	public static GameScene scene;
	
	// On initialization the GameStateManager sets the current state to the intro state
	public GameStateManager()
	{
		
		gameStates = new GameState[NUM_STATES];
		setState(INTRO);

	}
	
	// Sets the last running state to null then loads and initializes the new state.
	public void setState(int i)
	{
		previousState = currentState;
		unloadState(previousState);
		currentState = i;
		switch (i)
		{
		
			case 0: 
			{
				gameStates[i] = new IntroState(this);
				gameStates[i].init();

				
			} break;
			
			case 1:
			{
				gameStates[i] = new MenuState(this);
				gameStates[i].init();

				
			} break;
			
			case 2: 
			{
				gameStates[i] = new PlayState(this);
				gameStates[i].init();


			} break;
			
			case 3:
			{
				gameStates[i] = new FishingState(this);
				gameStates[i].init();
				
			} break;
			
			default: break;
		}
		
	}
	
	// Sets state to null
	public void unloadState(int i)
	{
		gameStates[i] = null;
	}
	
}
