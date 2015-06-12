package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class PlayState extends GameState 
{

	private Map map;  // This is a canvas
	private Player player;
	private int tileSize = 64;
	
	private Canvas canvas;
	private GraphicsContext gc;
	
	public PlayState(GameStateManager gsm)
	{
		super(gsm);
	}
	
	@Override
	public void init() {
		
		map = new Map(tileSize, "tiles2.png", "map5.txt");
				
		map.printCMap();
		
		player = new Player(2, 1, tileSize, "player2.png", map, this);
		
		draw();
		
		player.pc.setMap(map);
		
		this.getChildren().addAll(map, player);
		
		GameScene scene = new GameScene(this);
		Game.window.setScene(scene);
		
		player.requestFocus();
	}
	
	public void draw()
	{
		canvas = null;
		canvas = new Canvas(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		
		map.draw(gc);
		
		this.getChildren().add(canvas);

	}
}
