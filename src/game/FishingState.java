/*
 * 	The fishing state is called from the play state. The fishing mechanics begin 
 * 	when the player presses the SpaceBar to start the cast power bar.  On the 
 * 	second press of the SpaceBar the cast power bar stops and calculates the power
 * 	which tells the oval shape (bobber) how far up the screen to move.  After casting 
 * 	a thread begins that produces random integers that determine if the fish is 
 * 	currently catchable.
 * 
 */

package game;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FishingState extends GameState 
{

	public FishingState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		
		Text instructions = new Text("Press Spacebar to cast!");
		instructions.setScaleX(2);
		instructions.setScaleY(2);
		instructions.setX(260);
		instructions.setY(150);
		
		ImageView water = new ImageView(new Image("file:Water1.png"));
		water.fitWidthProperty().set(645);
		water.fitHeightProperty().set(665);
		
		Rectangle castBackground = new Rectangle(590, 510, 30, 110);
		
		Rectangle castInnerBackground = new Rectangle(593, 514, 24, 102);
		castInnerBackground.setFill(Color.WHITE);
		
		Rectangle castPower = new Rectangle(593, 614, 24, 3);
		castPower.setFill(Color.GREEN);
		
		Group hud = new Group(castBackground, castInnerBackground, castPower);
		
		Ellipse bobber = new Ellipse(200, 540, 20, 20);
		
		ImageView playerImageWait = new ImageView(new Image("file:png3.jpg"));
		ImageView playerImageCast = new ImageView(new Image("file:png6.jpg"));
	
		playerImageWait.setScaleX(6);
		playerImageWait.setScaleY(6);
		
		playerImageCast.setScaleX(6);
		playerImageCast.setScaleY(6);
		
		Group player = new Group(playerImageWait);
		player.setLayoutX(150);
		player.setLayoutY(540);
		
		Group group = new Group(water, bobber, player, hud, instructions);

		group.setOnKeyPressed(e -> 
		{
			switch(e.getCode())
			{
				case SPACE:
				{
					
					System.out.println("Space was pressed");
					
					Timeline tl = new Timeline();
					
					final TranslateTransition textHide = new TranslateTransition(Duration.millis(600), instructions);
					textHide.setToY(-320);
					textHide.play();
					
					final TranslateTransition cast = new TranslateTransition(Duration.millis(500), castPower);
					cast.setCycleCount(2);
					cast.setToY(-100);
					cast.setAutoReverse(true);
					cast.play(); 
					
					group.setOnKeyPressed(t ->
					{
						if(t.getCode().equals(KeyCode.SPACE))
						{
							cast.stop();
							
							double castStrength = -(castPower.getTranslateY());
							int power = (int) Math.round(castStrength);
							
							System.out.println("castStrength = " + castStrength);
							System.out.println("castStrength Integer = " + power);
							
							player.getChildren().setAll(playerImageCast);
							
							TranslateTransition bobberCast = new TranslateTransition(Duration.millis(700), bobber);
							
							tl.getKeyFrames().add(new KeyFrame(Duration.millis(0) , b -> {
								
								bobberCast.setToY(-power * 3 - 150);
								bobberCast.setToX(100);
								bobberCast.play();
							}));
							tl.getKeyFrames().add(new KeyFrame(Duration.millis(700), d -> {
								
							}));
							
							
							Timeline tl2 = new Timeline();
							
							tl2.getKeyFrames().add(new KeyFrame(Duration.millis(500) , c -> {
								TranslateTransition bobberIdle = new TranslateTransition(Duration.millis(300), bobber);
								bobberIdle.setAutoReverse(true);
								bobberIdle.setToY(bobber.getTranslateY() - 10);
								bobberIdle.setCycleCount(Timeline.INDEFINITE);
								
								bobberIdle.play();
							}));
							
							tl.setOnFinished( d -> tl2.play());
							tl.play();
							
							
							Timeline tl3 = new Timeline();
							
							tl3.getKeyFrames().add(new KeyFrame(Duration.millis(500), d -> {
								TranslateTransition catchBobber = new TranslateTransition(Duration.millis(300), bobber);
								catchBobber.setAutoReverse(true);
								catchBobber.setToY(bobber.getTranslateY() - 30);
								catchBobber.setCycleCount(Timeline.INDEFINITE);
								System.out.println("catchBobber is playing");
								catchBobber.play();
							}));
							
							FishingThread ft = new FishingThread(tl2, tl3);
							Thread thread = new Thread(ft);
							thread.start();
							
						} 
					});
					
				} break;
				
				case ESCAPE:
				{
					System.out.println("ESC pressed");
					gsm.setState(GameStateManager.PLAY);
				} break;
				default:break;
			}
		});
		
		Scene scene = new Scene(group);
		group.requestFocus();
		
		Game.window.setScene(scene);
	
	}
	
	private class FishingThread implements Runnable
	{
		Random random = new Random();
		int randomNumber;
		int catchable = 80;
		int fail = 5;
		int sleepTime = 2000;
		
		Timeline t1;
		Timeline t2;
		
		// The constructor takes two time lines, a normal bob and 
		// a wider ranging bob that shows the player that the fish is on.
		public FishingThread(Timeline t1, Timeline t2)
		{
			this.t1 = t1;
			this.t2 = t2;
		}
		
		// When the thread starts a random number 1 - 100 is generated,
		// if the number is "catchable" or larger a fish is catchable and the bobber
		// oscillates a lot.  If the number is below "fail"
		
		@Override
		public void run()
		{
			
			boolean running = true;
			try
			{
				while(running)
				{
					randomNumber = random.nextInt(100);
					
					// Success condition
					if(randomNumber >= catchable && t2.getStatus() == Animation.Status.STOPPED)
					{
						t1.stop();
						t2.play();
						
						System.out.println("Big");
					}
					// Fail condition, bait is eaten
					else if(randomNumber < fail)
					{
						if(t2.getStatus() == Animation.Status.RUNNING)
						{
							t2.stop();
						}
						t1.play();
						System.out.println("Fail");
						running = false;
					}
					// Nothing is happening
					else if(randomNumber < 80 && t1.getStatus() == Animation.Status.STOPPED)
					{
						if(t2.getStatus() == Animation.Status.STOPPED)
						{
							t2.stop();
						}
						t1.play();
						System.out.println("Small");
					}
					
					System.out.println("Random number generated is: " + randomNumber);
					Thread.sleep(sleepTime);
				}
			}catch(Exception e)
			{
				
			}
		}
	}

}
