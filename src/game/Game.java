package game;
import javafx.application.Application;
import javafx.stage.Stage;


public class Game extends Application 
{

	public static double WINDOW_HEIGHT = 665;
	public static double WINDOW_WIDTH = 645; //645
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
		
		gsm = new GameStateManager();
		gameScene = new GameScene(gsm.gameStates[gsm.currentState]);
		
		window.setScene(gameScene);
		window.show();
		
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

}
