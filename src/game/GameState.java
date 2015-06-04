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
//	public abstract void update();
//	public abstract void draw();
}
