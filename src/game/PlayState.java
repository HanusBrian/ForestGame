/* 
 *	PlayState displays the tile map and player.
 *	This is the initial state of the game where the player 
 *	can traverse the map.
 *
 */
package game;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class PlayState extends GameState 
{

	private Map map;
	private Player player;
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
		
		// Places the player object onto the map
		// (tile position x, tile position y, tile size, starting sprite, map, game state)
		player = new Player(2, 1, tileSize, "player2.png", map, this);
		
		// draw the map to a canvas
		draw();
		
		// Assigns the drawn canvas to a group
		canvasGroup = new Group(canvas);
		
		// Assigns the map and player groups to this Gamestates group
		this.getChildren().addAll(canvasGroup, player);
		
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
}
