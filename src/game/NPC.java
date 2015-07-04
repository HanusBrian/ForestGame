package game;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class NPC extends Group
{
	Image[] upSprites;
	Image[] rightSprites;
	Image[] downSprites;
	Image[] leftSprites;
	
	ImageView[] currentSprites;
	
	Image[] dialogueImage;
	ImageView[] dialogueImages;
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	int tileSize;
	Map map;
	
	int x, y;
	int screenx, screeny;
	
	PlayState gameState;
	
	public NPC(int x, int y, int direction, int tileSize, String sprites, String dialogueImages, Map map)
	{
		this.tileSize = tileSize;
		this.map = map;
//		this.gameState = gameState;
		
		loadSprites(sprites, 3, 4);
		loadDialogueImages(dialogueImages, 1, 1);	
		
		this.x = x;
		this.y = y;
		setInitialPosition(x, y);
		
		setSprites(direction);
		this.getChildren().setAll(currentSprites[1]);

		map.setTileType(x, y, Tile.NPC);
	}
	
	public void setInitialPosition(int x, int y)
	{
		screenx = x * tileSize;
		screeny = y * tileSize;
		
		this.setLayoutX(screenx);
		this.setLayoutY(screeny);
	}
	
	public void setDialogueImage(ImageView[] image)
	{
		this.dialogueImages = image;
	}
	
	public void loadSprites(String s, int tilesWide, int tilesHigh)
	{
		try
		{
			
			File file = new File(s);
			FileInputStream tileSheet = new FileInputStream(file);
			BufferedImage img = ImageIO.read(tileSheet);
			
			int numTilesWide = tilesWide;
			int numTilesHigh = tilesHigh;
			
			//TODO Temporary in order to try different tile sizes
			BufferedImage image = resizeImage(img, tileSize * numTilesWide, tileSize * numTilesHigh);
			
			int imageWidth = image.getWidth() / numTilesWide;
			int imageHeight = image.getHeight() / numTilesHigh;
			
			// Initialize the arrays for each direction group of movement sprites
			upSprites = new Image[numTilesWide];
			rightSprites = new Image[numTilesWide];
			downSprites = new Image[numTilesWide];
			leftSprites = new Image[numTilesWide];
			
			// Separates and saves the images to the sprite arrays
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
					
					switch (i)
					{
					
						case 0:
						{
							upSprites[j] = tempImage;
						}
						case 1:
						{
							rightSprites[j] = tempImage;
						}
						case 2: 
						{
							downSprites[j] = tempImage;
						}
						case 3: 
						{
							leftSprites[j] = tempImage;
						}
					
					}					
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Load playerSprites failure.");
		}
	}
	
	public void loadDialogueImages(String s, int tilesWide, int tilesHigh)
	{
		try
		{
			
			File file = new File(s);
			FileInputStream tileSheet = new FileInputStream(file);
			BufferedImage img = ImageIO.read(tileSheet);
			
			int numTilesWide = tilesWide;
			int numTilesHigh = tilesHigh;
			
			//TODO Temporary in order to try different tile sizes
			BufferedImage image = resizeImage(img, tileSize * numTilesWide, tileSize * numTilesHigh);
			
			int imageWidth = image.getWidth() / numTilesWide;
			int imageHeight = image.getHeight() / numTilesHigh;
			
			// Initialize the arrays for each direction group of movement sprites
			dialogueImage = new Image[numTilesWide];
			
			// Separates and saves the images to the sprite arrays
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
					
					dialogueImage[j] = tempImage;
				}
			}
			dialogueImages = toImageView(dialogueImage);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Load playerSprites failure.");
		}
	}
	
	public static BufferedImage resizeImage(BufferedImage image, int width, int height)
	{
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		
		return scaledImage;
	}
	
	public void setSprites(int i)
	{
		switch (i)
		{
			case 0:
			{
				currentSprites = toImageView(upSprites);
			} break;
			case 1:
			{
				currentSprites = toImageView(rightSprites);
			} break;
			case 2:
			{
				currentSprites = toImageView(downSprites);
			} break;
			case 3:
			{
				currentSprites = toImageView(leftSprites);
			} break;
		}
	}
	
	public ImageView[] toImageView(Image[] image)
	{
		ImageView[] view = new ImageView[image.length];
		for(int i = 0; i < image.length; i++)
		{
			view[i] = new ImageView(image[i]);
		}
		
		return view;
	}
}
