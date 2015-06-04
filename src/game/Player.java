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
import javafx.scene.media.AudioClip;


public class Player extends Group
{

	// Sprites
	Image[] upSprites;
	Image[] rightSprites;
	Image[] downSprites;
	Image[] leftSprites;
	
	Image[] currentSprites;
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;

	int tileSize;
	
	// Current x and y position 
	// the x and y are the grid positions not taking into account tileSize ( same with destx, desty )
	int x, y;
	int destx, desty;
	int screenx, screeny;

	PlayerControl pc;
	
	// Sound effects
	AudioClip bumpSound;
	
	public Player(int x, int y, int tileSize, String sprites, Map map)
	{
		this.tileSize = tileSize;
		loadSprites(sprites);
		
		this.x = x;
		this.y = y;
		setInitialPosition(x, y);
		
		setSprites(DOWN);
		
		bumpSound = new AudioClip("file:ungh.wav");
		
		pc = new PlayerControl(this, map);
		
	}
	
	public void setInitialPosition(int x, int y)
	{
		screenx = x * tileSize;
		screeny = y * tileSize;
		this.setTranslateX(screenx);
		this.setTranslateY(screeny);
	}
	
	public void moveSuccess()
	{
		this.x = destx;
		this.y = desty;
	}
	
	public void setDestination(int x, int y)
	{
		this.destx = x;
		this.desty = y;
	}
	
	// Set current sprites to direction of int input
	public void setSprites(int i)
	{
		switch (i)
		{
			case 0:
			{
				currentSprites = upSprites;
			} break;
			case 1:
			{
				currentSprites = rightSprites;
			} break;
			case 2:
			{
				currentSprites = downSprites;
			} break;
			case 3:
			{
				currentSprites = leftSprites;
			} break;
		}
	}

	
	// Cuts image up (hard coded rows and cols) 
	public void loadSprites(String s)
	{
		try
		{
			
			File file = new File(s);
			FileInputStream tileSheet = new FileInputStream(file);
			BufferedImage img = ImageIO.read(tileSheet);
			
			int numTilesWide = 3;
			int numTilesHigh = 4;
			
			//TODO Temporary in order to try different tile sizes
			BufferedImage image = resizeImage(img, tileSize * numTilesWide, tileSize * numTilesHigh);
			
			int imageWidth = image.getWidth() / numTilesWide;
			int imageHeight = image.getHeight() / numTilesHigh;
			

			
			upSprites = new Image[numTilesWide];
			rightSprites = new Image[numTilesWide];
			downSprites = new Image[numTilesWide];
			leftSprites = new Image[numTilesWide];
			
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
	
	// TODO Temporary method to be able to resize tiles
	public static BufferedImage resizeImage(BufferedImage image, int width, int height)
	{
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		
		return scaledImage;
	}

	
}
