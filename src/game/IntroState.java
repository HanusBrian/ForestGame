package game;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class IntroState extends GameState
{
	
	// This class has the splash screen then calls into the MenuState Class
	public IntroState(GameStateManager gsm)
	{
		super(gsm);
//		init();
	}

	@Override
	public void init() {
		
		ImageView image = new ImageView(new Image("file:splash.png"));
		image.fitWidthProperty().bind(Game.window.widthProperty());
		image.fitHeightProperty().bind(Game.window.heightProperty());
		
		AudioClip introSound = new AudioClip("file:intro2.wav");
		introSound.setVolume(.1);
		
		
		final Rectangle rekt = new Rectangle(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
		
		this.getChildren().addAll(image, rekt);

		final Timeline tl = new Timeline();
		
		
		tl.getKeyFrames().add(new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e)
			{
				FadeTransition ft = new FadeTransition(Duration.millis(2000), rekt);
				ft.setFromValue(1.0);
				ft.setToValue(0.0);
				ft.play();
				introSound.play();
			}
		}));
		tl.getKeyFrames().add(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e)
			{
				FadeTransition ft = new FadeTransition(Duration.millis(2000), rekt);
				ft.setFromValue(0.0);
				ft.setToValue(1.0);
				ft.play();
			}
		}));
		tl.getKeyFrames().add(new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e)
			{
				introSound.stop();
			}
		}));

		tl.setOnFinished(e -> Game.gsm.setState(GameStateManager.MENU));
		
		tl.play();


	}
}
