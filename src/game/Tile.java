package game;

import javafx.scene.image.Image;


public class Tile {

	public Image image;
	public int type;
	
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	public Tile(Image image, int type)
	{
		this.image = image;
		this.type = type;
	}
}
