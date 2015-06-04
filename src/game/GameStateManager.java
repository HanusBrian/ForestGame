package game;


public class GameStateManager 
{
	
	public GameState[] gameStates;
	int currentState;
	private int previousState;
	
	public static final int NUM_STATES = 3;
	
	public static final int INTRO = 0;
	public static final int MENU = 1;
	public static final int PLAY = 2;
	
	public static GameScene scene;
	
	//TODO property Bind gsm to the states.
	public GameStateManager()
	{
		
		gameStates = new GameState[NUM_STATES];
		setState(INTRO);

	}
	
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
//				scene = new Scene(gameStates[i]);
				
//				gameStates[i].init();
//				scene = new Scene(gameStates[i]);
				
			} break;
			
			case 1:
			{
				gameStates[i] = new MenuState(this);
				gameStates[i].init();
//				scene = new Scene(gameStates[i]);
//				Game.window.setScene(scene);
//				gameStates[i].init();
//				scene = new Scene(gameStates[i]);
				
			} break;
			
			case 2: 
			{
				gameStates[i] = new PlayState(this);
				gameStates[i].init();
//				scene = new Scene(gameStates[i]);
//				Game.window.setScene(scene);
//				gameStates[i].init();
//				scene = new Scene(gameStates[i]);

			} break;
			
			default: break;
		}
		
	}
	
	public void unloadState(int i)
	{
		gameStates[i] = null;
	}
	
}
