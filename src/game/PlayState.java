package game;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class PlayState extends GameState 
{

	private Map map;  // This is a canvas
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
		
		map = new Map(tileSize, "tiles2.png", "map6.txt");
				
		map.printCMap();
		
		player = new Player(2, 1, tileSize, "player2.png", map, this);
		
		draw();
		
		player.pc.setMap(map);
		canvasGroup = new Group(canvas);
		
		this.getChildren().addAll(canvasGroup, player);
		
		GameScene scene = new GameScene(this);
		Game.window.setScene(scene);
		
		player.requestFocus();
	}
	
	public void draw()
	{
		canvas = null;
		canvas = new Canvas(map.getTilesWide() * tileSize, map.getTilesHigh() * tileSize);
		gc = canvas.getGraphicsContext2D();
		
		map.draw(gc);
		
		this.getChildren().add(canvas);

	}
}
