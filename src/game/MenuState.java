package game;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class MenuState extends GameState
{
	
	private ImageView background;
	private Button startButton;
	private Label title;
	
	private VBox vbox;
	private StackPane stackPane; 
	
	private final double BUTTON_WIDTH = 150;
	private final double BUTTON_SCALE = 2.2;
	
	private Rectangle rekt; 
	

	
	public MenuState(GameStateManager gsm)
	{
		
		super(gsm);
//		init();
	}
	
	public void init()
	{
		
		background = new ImageView(new Image("http://trickvilla.com/wp-content/uploads/Light-Wood-Background.jpg"));
		background.fitWidthProperty().bind(Game.window.widthProperty());
		background.fitHeightProperty().bind(Game.window.heightProperty());
		
		
		title = new Label("Forest");
		title.setScaleX(4);
		title.setScaleY(4);
		title.setTranslateY(-100);
		title.setFont(Font.font ("Serif", 20));
		title.setTextFill(Color.OLIVE);		
		
		startButton = new Button("Start Game");
		startButton.setScaleX(BUTTON_SCALE);
		startButton.setScaleY(BUTTON_SCALE);
		startButton.setMaxWidth(BUTTON_WIDTH);
		startButton.setOnAction(e -> Game.gsm.setState(2));
		
		vbox = new VBox(120);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(title, startButton);
		
		
		stackPane = new StackPane();
		
		stackPane.getChildren().add(background);
		stackPane.getChildren().add(vbox);
		
		rekt = new Rectangle(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
		
		this.getChildren().addAll(stackPane, rekt);
		GameScene scene = new GameScene(this);
		Game.window.setScene(scene);
		

		FadeTransition ft = new FadeTransition(Duration.millis(2000), rekt);
		ft.setFromValue(1.0);
		ft.setToValue(0.0);
		ft.play();
				
		ft.setOnFinished(e -> this.getChildren().remove(rekt));

		ft.play();
		
	}
}
