/*
 *	The tile class determines the properties of each tile on the map.
 * 	Image is the selected image that will display and type will be used by the
 * 	collision map when determining if the tile is blocked, able to walk on
 *  or if it is water.
 */

package game;

import javafx.scene.image.Image;


public class Tile {

	public Image image;
	public int type;
	
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	public static final int WATER = 2;
	public static final int NPC = 3;
	
	public Tile(Image image, int type)
	{
		this.image = image;
		this.type = type;
	}
}
