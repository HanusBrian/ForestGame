/* 
 *	PlayState displays the tile map and player.
 *	This is the initial state of the game where the player 
 *	can traverse the map.
 *
 */
package game;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class PlayState extends GameState 
{

	private Map map;
	public Player player;
	private NPC npc;
	private int tileSize = 64;
	
	private Canvas canvas;
	Group canvasGroup;
	
	private GraphicsContext gc;
	
	public PlayState(GameStateManager gsm)
	{
		super(gsm);
	}
	
	@Override
	public void init() {
		// Creates map with specified tile size, tile images, ascii map
		map = new Map(tileSize, "tiles2.png", "map6.txt");
		
		// For debugging, prints the ascii map in the console
		map.printCMap();
		
		// Creates the player object
		// (tile position x, tile position y, direction facing, tile size, starting sprite, map, game state)
		player = new Player(2, 1, Player.DOWN, tileSize, "player2.png", map, this);
		
		npc = new NPC(4, 3, NPC.LEFT, tileSize, "player2.png", "png5.jpg", map);
		
		// draw the map to a canvas
		draw();
		
		// Assigns the drawn canvas to a group
		canvasGroup = new Group(canvas);
		
		// Assigns the map and player groups to this Gamestates group
		this.getChildren().addAll(canvasGroup, player, npc);
		
		// Creates a new scene for the PlayState and sets it onto the Game window
		GameScene scene = new GameScene(this);
		Game.window.setScene(scene);
		
		// Gives the player's playerControl focus to utilize the keyListners
		player.requestFocus();
	}
	
	public void loadSaved(SaveState save) {
		// Creates map with specified tile size, tile images, ascii map
		map = new Map(tileSize, "tiles2.png", "map6.txt");
		
		// For debugging, prints the ascii map in the console
		map.printCMap();
		
		// Creates the player object
		// (tile position x, tile position y, direction facing, tile size, starting sprite, map, game state)
		player = new Player(save.playerx, save.playery, save.playerDirection, tileSize, "player2.png", map, this);
		
		npc = new NPC(4, 3, NPC.LEFT, tileSize, "player2.png", "png5.jpg", map);
		
		// draw the map to a canvas
		draw();
		
		// Assigns the drawn canvas to a group
		canvasGroup = new Group(canvas);
		
		// Assigns the map and player groups to this Gamestates group
		this.getChildren().addAll(canvasGroup, player, npc);
		
		this.setTranslateX(save.translateScreenx);
		this.setTranslateY(save.translateScreeny);
		
		// Creates a new scene for the PlayState and sets it onto the Game window
		GameScene scene = new GameScene(this);
		Game.window.setScene(scene);
		
		// Gives the player's playerControl focus to utilize the keyListners
		player.requestFocus();
	}
	
	// Draws the ascii map to a canvas
	public void draw()
	{
		canvas = null;
		canvas = new Canvas(map.getTilesWide() * tileSize, map.getTilesHigh() * tileSize);
		gc = canvas.getGraphicsContext2D();
		
		map.draw(gc);
		
		this.getChildren().add(canvas);

	}
	
	public void openDialogue()
	{
		
		Dialogue dialogue = new Dialogue(npc);
		this.setOpacity(.5);
		Group group = new Group(this, dialogue);
		Scene scene = new Scene(group);
		Game.window.setScene(scene);
		
		
		dialogue.init();
	}
}
