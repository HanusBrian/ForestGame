/*	
 * 	The player control class is responsible for moving the player   
 *	around the PlayState map. SpaceBar is the action button.
 *  When the player is next to a water tile pressing SpaceBar
 *	will change the state to the fishing state.
 *
 */

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
	
	final Duration MOVEDURATION = Duration.millis(400);
	final Duration BLOCKEDDURATION = Duration.millis(100);
	final Duration MAPSHIFTDURATION = Duration.millis(500);
	
	final Duration Q1 = Duration.millis(100);
	final Duration Q2 = Duration.millis(200);
	final Duration Q3 = Duration.millis(300);
	
	Group pg;
	
	ImageView[] sprites;
	
	Player player;
	
	Tile[][] cMap;
	
	PlayState gameState;
	
 
	public PlayerControl(Player player, Map map, PlayState gameState)
	{

		this.player = player;
		
		sprites = new ImageView[3];
		getSprites();
		player.getChildren().setAll(sprites[1]);
		
		this.cMap = map.cMap;
		
		moveOnKeyPressed(player);
		player.requestFocus();
		
		this.gameState = gameState;
		
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
		else if(cMap[player.desty][player.destx].type == Tile.WATER)
		{
			if(player.hasBoat()){
				
				// get boat sprites
				
				return false;
			}
			else 
			{
				return true;
			}
		}
		else 
		{ 
			return false; 
		}
	}
	
	public void moveOnKeyPressed(Player player)
	{
		
		// Time line tl sets the sprites for the walking animation in the correct direction
		
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
		tl.getKeyFrames().add(new KeyFrame(MOVEDURATION, new EventHandler<ActionEvent>(){
			public void handle(ActionEvent t)
			{
				player.getChildren().setAll(sprites[1]);
			}
		}));
		
		
		// Time line tl2 handles the translation/movement of the character object
		Timeline tl2 = new Timeline();
		
		player.setOnKeyPressed(e -> {
			if(tl.getStatus() == Animation.Status.STOPPED && tl2.getStatus() == Animation.Status.STOPPED)
			{
				// How far into the tile player should move when the tile is blocked
				int blockedMove = 16;
				
				// Switch checks for movement keys either using arrow keys or wasd
				// Also checks for spacebar as action button
				switch(e.getCode())
				{
				
					case W:
					case UP:
					{
						// Set sprite direction and destination
						player.setSprites(Player.UP);
						getSprites();
						player.setDestination(player.x, player.y - 1);

						// If the destination is blocked the animation will move "blockedMove" pixels into the square
						// and return to the original tile
						
						if(isDestinationBlocked())
						{
						
							if(tl2.getStatus() == Animation.Status.STOPPED)
							{
								
								// Move into the box blockedMove pixels and play bumpSound
								tl2.getKeyFrames().add(new KeyFrame(Duration.millis(0), new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[0]);
										final TranslateTransition transitionBlocked = new TranslateTransition(BLOCKEDDURATION, player);
										player.getChildren().setAll(sprites[0]);
										transitionBlocked.setFromX(player.getTranslateX());
										transitionBlocked.setFromY(player.getTranslateY());
										transitionBlocked.setToX(player.getTranslateX());
										transitionBlocked.setToY((player.getTranslateY() - blockedMove));
										transitionBlocked.playFromStart();
										
										player.bumpSound.play();
									}
								}));
								
								// Move back to original tile position
								tl2.getKeyFrames().add(new KeyFrame(Q2, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[1]);
										TranslateTransition transition2 = new TranslateTransition(BLOCKEDDURATION, player);
										player.getChildren().setAll(sprites[1]);
										transition2.setFromX(player.getTranslateX());
										transition2.setFromY(player.getTranslateY());
										transition2.setToX(player.getTranslateX());
										transition2.setToY((player.getTranslateY() + blockedMove));
										transition2.playFromStart();

									}
								}));
								
								// This empty keyFrame ensures the transition completes
								tl2.getKeyFrames().add(new KeyFrame(Q3, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
	
									}
								}));
	
								tl2.play();
							}

						}
						
						// If the destination is not blocked
						else
						{
							// Check if the transition/movement is putting the player off the screen
							if(player.desty % 10 == 9)
							{
								
								// Create and run map shift operation
								final TranslateTransition moveMap = new TranslateTransition(MAPSHIFTDURATION, gameState);
								moveMap.setFromX(gameState.getTranslateX());
								moveMap.setFromY(gameState.getTranslateY());
								moveMap.setToY(0);
								moveMap.playFromStart();
								
								System.out.println("Player has walked off screen");
								
							}
							
							// Move the player
							final TranslateTransition playerTransition = new TranslateTransition(MOVEDURATION, player);
							playerTransition.setFromX(player.getTranslateX());
							playerTransition.setFromY(player.getTranslateY());
							playerTransition.setToX(player.getTranslateX());
							playerTransition.setToY(player.getTranslateY() - STEP_SIZE);
							playerTransition.playFromStart();
							
							tl.play();
							// Update the player position on the map
							player.moveSuccess();
					}
						
					} break;
					
					
					case S:
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
										TranslateTransition transition1 = new TranslateTransition(BLOCKEDDURATION, player);
										player.getChildren().setAll(sprites[0]);
										transition1.setFromX(player.getTranslateX());
										transition1.setFromY(player.getTranslateY());
										transition1.setToX(player.getTranslateX());
										transition1.setToY((player.getTranslateY() + blockedMove));
										transition1.playFromStart();
										
										player.bumpSound.play();
									}
								}));

								tl2.getKeyFrames().add(new KeyFrame(Q2, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[1]);
										TranslateTransition transition2 = new TranslateTransition(BLOCKEDDURATION, player);
										player.getChildren().setAll(sprites[1]);
										transition2.setFromX(player.getTranslateX());
										transition2.setFromY(player.getTranslateY());
										transition2.setToX(player.getTranslateX());
										transition2.setToY((player.getTranslateY() - blockedMove));
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
							if(player.desty % 10 == 0)
							{
								
								// Create and run map shift operation
								TranslateTransition moveMap = new TranslateTransition(MAPSHIFTDURATION, gameState);
								moveMap.setFromX(gameState.getTranslateX());
								moveMap.setFromY(gameState.getTranslateY());
								moveMap.setToY(-Game.WINDOW_HEIGHT + 20);
								moveMap.playFromStart();
								
								System.out.println("Player has walked off screen");
								
							}
							
							final TranslateTransition playerTransition = new TranslateTransition(MOVEDURATION, player);
							playerTransition.setFromX(player.getTranslateX());
							playerTransition.setFromY(player.getTranslateY());
							playerTransition.setToX(player.getTranslateX());
							playerTransition.setToY(player.getTranslateY() + STEP_SIZE);
							playerTransition.playFromStart();
							tl.play();
							
							player.moveSuccess();
					
						}
					} break;
					
					
					case A:
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
										TranslateTransition transition1 = new TranslateTransition(BLOCKEDDURATION, player);
										player.getChildren().setAll(sprites[0]);
										transition1.setFromX(player.getTranslateX());
										transition1.setFromY(player.getTranslateY());
										transition1.setToX((player.getTranslateX() - blockedMove));
										transition1.setToY(player.getTranslateY());
										transition1.playFromStart();
										
										player.bumpSound.play();
									}
								}));

								tl2.getKeyFrames().add(new KeyFrame(Q2, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[1]);
										TranslateTransition transition2 = new TranslateTransition(BLOCKEDDURATION, player);
										player.getChildren().setAll(sprites[1]);
										transition2.setFromX(player.getTranslateX());
										transition2.setFromY(player.getTranslateY());
										transition2.setToX((player.getTranslateX() + blockedMove));
										transition2.setToY(player.getTranslateY());
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
							
							if(player.destx % 10 == 9)
							{
								
								// Create and run map shift operation
								TranslateTransition moveMap = new TranslateTransition(MAPSHIFTDURATION, gameState);
								moveMap.setFromX(gameState.getTranslateX());
								moveMap.setFromY(gameState.getTranslateY());
								moveMap.setToX(0);
								moveMap.playFromStart();
								
								
								System.out.println("Player has walked off screen");
								
							}
							
							final TranslateTransition playerTransition = new TranslateTransition(MOVEDURATION, player);
							playerTransition.setFromX(player.getTranslateX());
							playerTransition.setFromY(player.getTranslateY());
							playerTransition.setToX(player.getTranslateX() - STEP_SIZE);
							playerTransition.setToY(player.getTranslateY());
							playerTransition.playFromStart();
							tl.play();
							
							player.moveSuccess();
						}
						
					} break;
					
					
					case D:
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
										TranslateTransition transition1 = new TranslateTransition(BLOCKEDDURATION, player);
										player.getChildren().setAll(sprites[0]);
										transition1.setFromX(player.getTranslateX());
										transition1.setFromY(player.getTranslateY());
										transition1.setToX((player.getTranslateX() + blockedMove));
										transition1.setToY(player.getTranslateY());
										transition1.playFromStart();
										
										player.bumpSound.play();
									}
								}));

								tl2.getKeyFrames().add(new KeyFrame(Q2, new EventHandler<ActionEvent>(){
									public void handle(ActionEvent t)
									{
										player.getChildren().setAll(sprites[1]);
										TranslateTransition transition2 = new TranslateTransition(BLOCKEDDURATION, player);
										player.getChildren().setAll(sprites[1]);
										transition2.setFromX(player.getTranslateX());
										transition2.setFromY(player.getTranslateY());
										transition2.setToX((player.getTranslateX() - blockedMove));
										transition2.setToY(player.getTranslateY());
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
							
							// Shift the map when player walks off screen
							if(player.destx % 10 == 0)
							{
								
								// Create and run map shift operation
								TranslateTransition moveGroup = new TranslateTransition(MAPSHIFTDURATION, gameState);
								moveGroup.setFromX(gameState.getTranslateX());
								moveGroup.setFromY(gameState.getTranslateY());
								moveGroup.setToX(-Game.WINDOW_WIDTH + 10);
								moveGroup.playFromStart();

								
								System.out.println("Player has walked off screen");
								
							}
							
							final TranslateTransition playerTransition = new TranslateTransition(MOVEDURATION, player);
							playerTransition.setFromX(player.getTranslateX());
							playerTransition.setFromY(player.getTranslateY());
							playerTransition.setToX(player.getTranslateX() + STEP_SIZE);
							playerTransition.setToY(player.getTranslateY());
							playerTransition.playFromStart();
							tl.play();
							
							player.moveSuccess();
						}
	
					} break;
					
					
					case SPACE:
					{
						// Check if any of the surrounding tiles are water
						if(cMap[player.y][player.x + 1].type == Tile.WATER || cMap[player.y][player.x - 1].type == Tile.WATER || 
								cMap[player.y + 1][player.x].type == Tile.WATER || cMap[player.y - 1][player.x].type == Tile.WATER)
						{

							System.out.println("SpaceBar pressed");
							// Open new fishing scene
							Game.gsm.setState(3);
							
						}
						
					}
					
					default:break;
				
				}
			}
		});
	}
}