package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Dialogue extends StackPane
{

	NPC npc;
	ImageView npcImage;
	
	final Duration zero = Duration.millis(0);
	final Duration imageEnter = Duration.millis(500);
	
	Font font;
	Label hello;
	
	public Dialogue(NPC npc)
	{

		this.npc = npc;
		npcImage = this.npc.dialogueImages[0];
		hello = new Label("Hey there! \nYou look fat as fuck. Why dont you go fishing\n"
				+ "and fill your fat fucking stomach up\nyou goddamn piece of shit...");
		
	}
	
	public void init()
	{
		npcImage.setTranslateX(-240);
		npcImage.setTranslateY(400);
		
		npcImage.setScaleX(4);
		npcImage.setScaleY(5);
		
		try{
			font = Font.loadFont("file:AmaticSC-Regular.ttf", 25);
		}
		catch(Exception a)
		{
			System.out.println("Dialogue Font failed");
		}
		
		Rectangle rekt = new Rectangle(400, 200);
		rekt.setFill(Color.LIGHTSTEELBLUE);
		rekt.setVisible(false);
		rekt.setTranslateX(250);
		rekt.setTranslateY(450);
		
		hello.setFont(font);
		hello.setTranslateX(230);
		hello.setTranslateY(420);
//		hello.setTextFill(Color.WHITE);	
		hello.setVisible(false);
		
		this.getChildren().addAll(npcImage, rekt, hello);
		
		
		Timeline tl = new Timeline();
		tl.getKeyFrames().add(new KeyFrame(zero, e -> {
			final TranslateTransition tt = new TranslateTransition(imageEnter, npcImage);
			tt.setFromX(npcImage.getTranslateX());
			tt.setFromY(npcImage.getTranslateY());
			tt.setToX(npcImage.getTranslateX() + 170);
			tt.setToY(npcImage.getTranslateY());
			tt.play();
//			npcImage.setX(0);
//			npcImage.setY(440);

		}));
		tl.getKeyFrames().add(new KeyFrame(Duration.millis(500), e -> {
			
			rekt.setVisible(true);
			hello.setVisible(true);
		
		}));
		
		tl.play();
	}
}
