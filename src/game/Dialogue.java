package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Dialogue extends Group
{

	NPC npc;
	ImageView npcImage;
	
	final Duration zero = Duration.millis(0);
	final Duration imageEnter = Duration.millis(500);
	
	Font font;
	Label prompt;
	Label answer1;
	Label answer2;
	
	Rectangle textBackground;
	Rectangle answerPointer;
	Rectangle answerBox;
	
	DialogueControl dialogueControl;
	
	public Dialogue(NPC npc)
	{

		this.npc = npc;
		npcImage = this.npc.dialogueImages[0];
		prompt = new Label("Hey there! \nHaven't seen you around before.  You interested in fishing?\n"
				+ "I have a spare rod I was going to toss, might as well have it put to use...");
		
		answer1 = new Label("Alright boss, show me how.");
		answer2 = new Label("Bugger off ya piker!");
		
		try{
			font = Font.loadFont("file:AmaticSC-Regular.ttf", 25);
		}
		catch(Exception a)
		{
			System.out.println("Dialogue Font failed");
		}
	}
	
	public void init()
	{

		npcImage.setScaleX(4);
		npcImage.setScaleY(5);
		
		npcImage.setX(-150);
		npcImage.setY(150);
		
		textBackground = new Rectangle(540, 200);
		textBackground.setFill(Color.LIGHTSTEELBLUE);
		textBackground.setLayoutX(50);
		textBackground.setLayoutY(400);
		textBackground.setStroke(Color.BLACK);
		textBackground.setVisible(false);
		
		prompt.setFont(font);
		prompt.setLayoutX(90);
		prompt.setLayoutY(425);
		prompt.setVisible(false);

		answer1.setFont(font);
		answer1.setLayoutX(90);
		answer1.setLayoutY(540);
		answer1.setVisible(false);

		answer2.setFont(font);
		answer2.setLayoutX(400);
		answer2.setLayoutY(540);
		answer2.setVisible(false);
		
		answerPointer = new Rectangle(10, 10);
		answerPointer.setFill(Color.BLANCHEDALMOND);
		answerPointer.setLayoutX(73);
		answerPointer.setLayoutY(552);
		answerPointer.setVisible(false);
		
		
		this.getChildren().addAll(npcImage, textBackground, prompt, answer1, answer2, answerPointer);
		
		Timeline tl = new Timeline();
		tl.getKeyFrames().add(new KeyFrame(zero, e -> {
			final TranslateTransition tt = new TranslateTransition(imageEnter, npcImage);
			tt.setFromX(npcImage.getLayoutX());
			tt.setFromY(npcImage.getLayoutY());
			tt.setToX(npcImage.getLayoutX() + 250);
			tt.setToY(npcImage.getLayoutY());
			
			tt.play();


		}));
		tl.getKeyFrames().add(new KeyFrame(Duration.millis(500), e -> {
			
			textBackground.setVisible(true);
			prompt.setVisible(true);
			answer1.setVisible(true);
			answer2.setVisible(true);
			answerPointer.setVisible(true);
			
		}));
		
		tl.play();
		
		dialogueControl = new DialogueControl(this);
		this.requestFocus();
	}
}
