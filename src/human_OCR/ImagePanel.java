package human_OCR;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.Random;

public class ImagePanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage b, duck, h, win, known, unknown;
	private GameStage gs;
	private Words myWords;
	private int tempScore, mode;
	
	public ImagePanel(GameStage g)
	{
		gs = g;
		setLayout(null);
		myWords = new Words(); //new a word object
		try
		{
			//load in the pictures
			b = ImageIO.read(new File("b.png"));
			duck = ImageIO.read(new File("duck.png"));
			h = ImageIO.read(new File("h.png"));
			win = ImageIO.read(new File("win.png"));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	protected void paintComponent(Graphics g) //override paintComponent
	{
        super.paintComponent(g);
        setBackground(new Color(130, 213, 253)); //put the background
        //draw image
        g.drawImage(h, gs.getbg_X(), 0, h.getWidth(), h.getHeight(), null);
        g.drawImage(b, gs.getBall_X(), gs.getBall_Y(), b.getWidth(), b.getHeight(), null);
        g.drawImage(duck, gs.getDuck_X(), gs.getDuck_Y(), duck.getWidth(), duck.getHeight(), null);
        //if win the game, draw the win image
        if(gs.getScore() >= gs.getWinScore()) g.drawImage(win, 500, 10, win.getWidth(), win.getHeight(), null);
        //if temp!=now, means need to import new word
        else if(tempScore!=gs.getScore())
        {		
        	known = myWords.getKnown(gs.getTotalScore());
        	unknown = myWords.getUnknown(gs.getTotalScore());
        	tempScore = gs.getScore();
        	gs.setWord_Y(0);
            Random random = new Random();
            mode = random.nextInt(2);
            //mode means the order of word, known->unknown or unknown->known
        }

        g.setColor(new Color(209, 231, 252));
        g.fillRect(0, 0, 320, 500); // draw the left block background
        
        //two mode, known->unknown or unknown->known
        if(mode==0)
        {
        	g.drawImage(known, 10, gs.getWord_Y(), known.getWidth(), known.getHeight(), null);
        	g.drawImage(unknown, known.getWidth()+20, gs.getWord_Y(), unknown.getWidth(), unknown.getHeight(), null);
        }
        else
        {
        	g.drawImage(known, unknown.getWidth()+20, gs.getWord_Y(), known.getWidth(), known.getHeight(), null);
        	g.drawImage(unknown, 10, gs.getWord_Y(), unknown.getWidth(), unknown.getHeight(), null);
        }
    }
	
	public Words getWords()
	{
		return myWords;
	}
	
	public int getMode()
	{
		return mode;
	}
	
	public void setTempScore(int in)
	{
		tempScore = in;
	}
	
}
