/*
 *	All GameStates are created as a Group and hold a reference to the GameStateManager
 *	GameStates must also create a method init to initialize the state and display it
 *	on screen.
 *
 */

package game;
import javafx.scene.Group;

public abstract class GameState extends Group
{
	
	protected GameStateManager gsm;

	public GameState(GameStateManager gsm)
	{
		this.gsm = gsm;
	}

	public abstract void init();

}
