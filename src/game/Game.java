/*
 *	The Game class holds the main method of the application.
 *	It holds the dimensions of the window, initializes the 
 *	GameStateManager and sets the scene 
 */

package game;
import javafx.application.Application;
import javafx.stage.Stage;


public class Game extends Application 
{

	public static double WINDOW_HEIGHT = 665;
	public static double WINDOW_WIDTH = 645; //645 normal, 1290 to see entire map
	
	public static Stage window;
	public static GameScene gameScene;
	public static GameStateManager gsm;

	public void start(Stage stage)
	{
		window = stage;
		window.setTitle("Forest");
		window.setWidth(WINDOW_WIDTH);
		window.setHeight(WINDOW_HEIGHT);
		window.setResizable(false);
		
		// GameStateManager initializes to intro state.
		gsm = new GameStateManager();
		// A new gameScene is created and the current game state is handed over. 
		gameScene = new GameScene(gsm.gameStates[gsm.currentState]);
		
		window.setScene(gameScene);
		window.show();
		
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

}
