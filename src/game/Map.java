package game;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;



public class Map extends Canvas
{
	
	//map
	private int width, height;
	
	private char[][] map;
	private int tileSize;
	private Tile[] tileSet;
	
	GraphicsContext gc = this.getGraphicsContext2D();
	
	public Tile[][] cMap; //Collision map

	// tileSize in allows for screen size changes in the future
	// could add map select here too in the future 
	public Map(int tileSize, String tiles, String map)
	{
		this.tileSize = tileSize;
		loadTiles(tiles);
		loadMap(map);
//		draw();	
		
	}
	
	// Loads Tile Images and types from file and puts them into the tile array (tileSet)
	
	public void loadTiles(String s)
	{
		try
		{
			
			File file = new File(s);
			FileInputStream tileSheet = new FileInputStream(file);
			BufferedImage img = ImageIO.read(tileSheet);
			
			int numTilesWide = 3;
			int numTilesHigh = 1;
			int numTiles = numTilesWide * numTilesHigh;
			
			//TODO Temporary in order to try different tile sizes
			BufferedImage image = resizeImage(img, tileSize * numTilesWide, tileSize * numTilesHigh);
			
			int imageWidth = image.getWidth() / numTilesWide;
			int imageHeight = image.getHeight() / numTilesHigh;
			
			int count = 0;
			
			tileSet = new Tile[numTiles];
			
			for(int i = 0; i < numTilesHigh; i++)
			{
				for(int j = 0; j < numTilesWide; j++)
				{
					
					BufferedImage temp = new BufferedImage(imageWidth, imageHeight, image.getType());
					Graphics2D gr = temp.createGraphics();
					// image, write top left (x, y), write bot right (x, y), read top left (x, y), read bot right (x, y), image observer(usually null)
					gr.drawImage(image, 0, 0, imageWidth,  imageHeight, j * imageWidth, i * imageHeight, j * imageWidth + imageWidth, i * imageHeight + imageHeight, null);
					gr.dispose();
					
					Image tempImage = SwingFXUtils.toFXImage(temp, null);
					
					if(count < 1)
					{
						tileSet[count] = new Tile(tempImage, Tile.NORMAL);
					}
					else
					{
						tileSet[count] = new Tile(tempImage, Tile.BLOCKED);
					}
					count++;
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Load tiles failure");
		}
	}
	
	
	// TODO Temporary method to be able to resize tiles of the (tileSet)
	public static BufferedImage resizeImage(BufferedImage image, int width, int height)
	{
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		
		return scaledImage;
	}
	
		// Loads from text file (s) and creates a 2D char array (map) to serve as the map
	public void loadMap(String s)

	{
		try
		{
			Scanner in = new Scanner(new File(s));
//			FileReader in = new FileReader(new File("map4.txt"));
			
			width = Integer.parseInt(in.nextLine());
			height = Integer.parseInt(in.nextLine());

			
			map = new char[height][width];
			cMap = new Tile[height][width];
			
			for(int row = 0; row < height; row++)
			{
				String temp = in.nextLine();
				for(int col = 0; col < width; col++)
				{
					
					map[row][col] = temp.charAt(0);
					temp = temp.substring(1);
					if(map[row][col] == '0')
					{
						cMap[row][col] = new Tile(tileSet[0].image, Tile.NORMAL);
					}
					else if(map[row][col] == '1')
					{
						cMap[row][col] = new Tile(tileSet[1].image, Tile.BLOCKED);
					}
					else if(map[row][col] == '2')
					{
						cMap[row][col] = new Tile(tileSet[2].image, Tile.BLOCKED);
					}
				}
			}
			in.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("CreateMapArray has failed.");
		}
	}
	
	// Draw the images to a canvas (gc.canvas) and creates collision map (cMap)
	public void draw(GraphicsContext gc)
	{
//		showMap();
		

		// create a tile[][] to keep track of collisions
		
		for(int row = 0; row < map.length; row++)
		{
			for(int col = 0; col < map[0].length; col++)
			{
				if(map[row][col] == '0')
				{
					
					gc.drawImage(tileSet[0].image, col * tileSize, row * tileSize);

				}
				else if(map[row][col] == '1')
				{

					gc.drawImage(tileSet[1].image, col * tileSize, row * tileSize);
				}
				else if(map[row][col] == '2')
				{
					
					gc.drawImage(tileSet[2].image, col * tileSize, row * tileSize);
				}
			}
		}
	}
	
	
	//TODO Temp method to display the (char[][] map)
	public void showMap()
	{
		for(int i = 0; i < map.length; i++)
		{
			for( int j = 0; j < map[i].length;j++)
			{
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}

	public void printCMap()
	{
		for(int i = 0; i < cMap.length; i++)
		{
			for(int j = 0; j < cMap[i].length; j++)
			{
				System.out.print(cMap[i][j].type);
			}
			System.out.println();
		}
	}
}


