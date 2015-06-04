package game;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class PlayerControl 
{	
	final int STEP_SIZE = 64;
	
	final Duration DURATION = Duration.millis(400);
	final Duration Q1 = Duration.millis(100);
	final Duration Q2 = Duration.millis(200);
	final Duration Q3 = Duration.millis(300);
	
	Group pg;
	
	ImageView[] sprites;
	
	Player player;
	
	Tile[][] cMap;
	
	Map map;
	
	
	
	public PlayerControl(Player player, Map map)
	{

		this.player = player;
		sprites = new ImageView[3];
		getSprites();
		
		player.getChildren().setAll(sprites[1]);

		moveOnKeyPressed(player);
		player.requestFocus();
		
	}
	
	public void setMap(Map map)
	{
		this.cMap = map.cMap;
	}
	
	private void getSprites()
	{
		sprites[0] = new ImageView(player.currentSprites[0]);
		sprites[1] = new ImageView(player.currentSprites[1]);
		sprites[2] = new ImageView(player.currentSprites[2]);
	}
	
	// Check if the destination of player movement is normal or blocked
	public boolean isDestinationBlocked()
	{	
		
		System.out.println("DestX: x = " + player.destx + " DestY = " + player.desty);
		System.out.println(cMap[player.desty][player.destx].type);
		if(cMap[player.desty][player.destx].type == Tile.BLOCKED) 
		{ 
			return true; 
		}
		else 
		{ 
			return false; 
		}
	}
	
	public void moveOnKeyPressed(Player player)
	{
		
		final Timeline tl = new Timeline();
		tl.getKeyFrames().add(new KeyFrame(Q1, new EventHandler<ActionEvent>(){
			public void handle(ActionEvent t)
			{
				player.getChildren().setAll(sprites[0]);
			}
		}));
		tl.getKeyFrames().add(new KeyFrame(Q2, new EventHandler<ActionEvent>(){
			public void handle(ActionEvent t)
			{
				player.getChildren().setAll(sprites[1]);
			}
		}));
		tl.getKeyFrames().add(new KeyFrame(Q3, new EventHandler<ActionEvent>(){
			public void handle(ActionEvent t)
			{
				player.getChildren().setAll(sprites[2]);
			}
		}));
		tl.getKeyFrames().add(new KeyFrame(DURATION, new EventHandler<ActionEvent>(){
			public void handle(ActionEvent t)
			{
				player.getChildren().setAll(sprites[1]);
			}
		}));
		
		Timeline tl2 = new Timeline();
		
		player.setOnKeyPressed(e -> {
			if(tl.getStatus() == Animation.Status.STOPPED && tl2.getStatus() == Animation.Status.STOPPED)
			{
					
				final TranslateTransition transition = new TranslateTransition(DURATION, player);
				
				switch(e.getCode())
				{
				
					case UP:
					{
	
						player.setSprites(Player.UP);
						getSprites();
						player.setDestination(player.x, player.y - 1);

						if(isDestinationBlocked())
						{
							
						
							if(tl2.getStatus() == Animation.Status.STOPPED)
							{
								
								tl2.getKeyFrames().add(new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[0]);
										TranslateTransition transition1 = new TranslateTransition(Duration.millis(100), player);
										player.getChildren().setAll(sprites[0]);
										transition1.setFromX(player.getTranslateX());
										transition1.setFromY(player.getTranslateY());
										transition1.setToX(player.getTranslateX());
										transition1.setToY((player.getTranslateY() - 16));
										transition1.setDuration(Duration.millis(100));
										transition1.playFromStart();
										
										player.bumpSound.play();
									}
								}));

								tl2.getKeyFrames().add(new KeyFrame(Q2, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[1]);
										TranslateTransition transition2 = new TranslateTransition(Duration.millis(100), player);
										player.getChildren().setAll(sprites[1]);
										transition2.setFromX(player.getTranslateX());
										transition2.setFromY(player.getTranslateY());
										transition2.setToX(player.getTranslateX());
										transition2.setToY((player.getTranslateY() + 16));
										transition2.setDuration(Duration.millis(100));
										transition2.playFromStart();

									}
								}));
								
								tl2.getKeyFrames().add(new KeyFrame(Q3, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
	
									}
								}));
	
								tl2.play();
							}

						}
						else
						{

							transition.setFromX(player.getTranslateX());
							transition.setFromY(player.getTranslateY());
							transition.setToX(player.getTranslateX());
							transition.setToY(player.getTranslateY() - STEP_SIZE);
							transition.playFromStart();
							
							tl.play();
							player.moveSuccess();
					}
						
					} break;
					case DOWN:
					{
						
						player.setSprites(Player.DOWN);
						getSprites();
						
						player.setDestination(player.x, player.y + 1);
						
						if(isDestinationBlocked())
						{
							
						
							if(tl2.getStatus() == Animation.Status.STOPPED)
							{
								
								tl2.getKeyFrames().add(new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[0]);
										TranslateTransition transition1 = new TranslateTransition(Duration.millis(100), player);
										player.getChildren().setAll(sprites[0]);
										transition1.setFromX(player.getTranslateX());
										transition1.setFromY(player.getTranslateY());
										transition1.setToX(player.getTranslateX());
										transition1.setToY((player.getTranslateY() + 16));
										transition1.setDuration(Duration.millis(100));
										transition1.playFromStart();
										
										player.bumpSound.play();
									}
								}));

								tl2.getKeyFrames().add(new KeyFrame(Q2, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[1]);
										TranslateTransition transition2 = new TranslateTransition(Duration.millis(100), player);
										player.getChildren().setAll(sprites[1]);
										transition2.setFromX(player.getTranslateX());
										transition2.setFromY(player.getTranslateY());
										transition2.setToX(player.getTranslateX());
										transition2.setToY((player.getTranslateY() - 16));
										transition2.setDuration(Duration.millis(100));
										transition2.playFromStart();

									}
								}));
								
								tl2.getKeyFrames().add(new KeyFrame(Q3, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
	
									}
								}));
	
								tl2.play();
							}
			
						}
						else
						{
							transition.setFromX(player.getTranslateX());
							transition.setFromY(player.getTranslateY());
							transition.setToX(player.getTranslateX());
							transition.setToY(player.getTranslateY() + STEP_SIZE);
							transition.playFromStart();
							tl.play();
							
							player.moveSuccess();
					
						}
					} break;
					case LEFT:
					{
						player.setSprites(Player.LEFT);
						getSprites();
						
						player.setDestination(player.x - 1, player.y);
						
						
						if(isDestinationBlocked())
						{
							
						
							if(tl2.getStatus() == Animation.Status.STOPPED)
							{
								
								tl2.getKeyFrames().add(new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[0]);
										TranslateTransition transition1 = new TranslateTransition(Duration.millis(100), player);
										player.getChildren().setAll(sprites[0]);
										transition1.setFromX(player.getTranslateX());
										transition1.setFromY(player.getTranslateY());
										transition1.setToX((player.getTranslateX() - 16));
										transition1.setToY(player.getTranslateY());
										transition1.setDuration(Duration.millis(100));
										transition1.playFromStart();
										
										player.bumpSound.play();
									}
								}));

								tl2.getKeyFrames().add(new KeyFrame(Q2, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[1]);
										TranslateTransition transition2 = new TranslateTransition(Duration.millis(100), player);
										player.getChildren().setAll(sprites[1]);
										transition2.setFromX(player.getTranslateX());
										transition2.setFromY(player.getTranslateY());
										transition2.setToX((player.getTranslateX() + 16));
										transition2.setToY(player.getTranslateY());
										transition2.setDuration(Duration.millis(100));
										transition2.playFromStart();

									}
								}));
								
								tl2.getKeyFrames().add(new KeyFrame(Q3, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
	
									}
								}));
	
								tl2.play();
							}			
						}
						else
						{
							player.setSprites(Player.LEFT);
							getSprites();
							
							transition.setFromX(player.getTranslateX());
							transition.setFromY(player.getTranslateY());
							transition.setToX(player.getTranslateX() - STEP_SIZE);
							transition.setToY(player.getTranslateY());
							transition.playFromStart();
							tl.play();
							
							player.moveSuccess();
						}
						
					} break;
					case RIGHT:
					{
						player.setSprites(Player.RIGHT);
						getSprites();
						
						player.setDestination(player.x + 1, player.y);
						
						
						if(isDestinationBlocked())
						{
							
						
							if(tl2.getStatus() == Animation.Status.STOPPED)
							{	
								
								tl2.getKeyFrames().add(new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[0]);
										TranslateTransition transition1 = new TranslateTransition(Duration.millis(100), player);
										player.getChildren().setAll(sprites[0]);
										transition1.setFromX(player.getTranslateX());
										transition1.setFromY(player.getTranslateY());
										transition1.setToX((player.getTranslateX() + 16));
										transition1.setToY(player.getTranslateY());
										transition1.setDuration(Duration.millis(100));
										transition1.playFromStart();
										
										player.bumpSound.play();
									}
								}));

								tl2.getKeyFrames().add(new KeyFrame(Q2, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[1]);
										TranslateTransition transition2 = new TranslateTransition(Duration.millis(100), player);
										player.getChildren().setAll(sprites[1]);
										transition2.setFromX(player.getTranslateX());
										transition2.setFromY(player.getTranslateY());
										transition2.setToX((player.getTranslateX() - 16));
										transition2.setToY(player.getTranslateY());
										transition2.setDuration(Duration.millis(100));
										transition2.playFromStart();
										
									}
								}));
								
								tl2.getKeyFrames().add(new KeyFrame(Q3, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
	
									}
								}));
	
								tl2.play();
							}		
						}
						else
						{
							if(player.destx == 10)
							{
								
								// add map shift operation
//								TranslateTransition moveMap = new TranslateTransition();
//								moveMap.
								
								
								
							}
							player.setSprites(Player.RIGHT);
							getSprites();
							
							transition.setFromX(player.getTranslateX());
							transition.setFromY(player.getTranslateY());
							transition.setToX(player.getTranslateX() + STEP_SIZE);
							transition.setToY(player.getTranslateY());
							transition.playFromStart();
							tl.play();
							
							player.moveSuccess();
						}
	
					} break;
					
					default:break;
				
				}
			}
		});
	}
}