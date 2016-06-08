package human_OCR;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;


public class Words
{
	private BufferedImage[] known;
	private BufferedImage[] unknown;
	private int nowKnownWord, nowUnknownWord;
	private String[] correctWord = new String[51];
	private File f1, f2;
	private String[] s1;
	private String[] s2;
	private int[] box1;
	private int[] box2;
	
	public Words()
	{
		int i;
		known = new BufferedImage[51];
		unknown = new BufferedImage[73];
		f1 = new File("known");
		f2 = new File("unknown");
		s1 = f1.list(); //s1 has all known word file name
		s2 = f2.list(); //s2 has all unknown word file name
		box1 = new int[51]; //box is for random choose
		box2 = new int[73];
		
		FileReader f3 = null;
		try 
		{
			f3 = new FileReader("known_words.txt");
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(f3);
		for(i=0; i<51; i++)
		{
			try
			{
				correctWord[i] = br.readLine().substring(10); //only read the word
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		for(i=0; i<51; i++)
		{
			try
			{
				known[i] = ImageIO.read(new File("./known/" + s1[i])); //put all known file into known[]
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		for(i=0; i<73; i++)
		{
			try
			{
				unknown[i] = ImageIO.read(new File("./unknown/" + s2[i])); //put all unknown file into unknown[]
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public BufferedImage getKnown(int range)
	{
		Random random = new Random();
		if(range%51==0) initialBox(1); //if the all random number is used, then initial it
		if(range>=51) range%=51; 
		int select = random.nextInt(51-range); //select the number of word want to choose
		nowKnownWord = box1[select];
		box1[select] = box1[50-range]; //change the choose number with the last number in array
		//every time range will +1, so next time will not choose to the number has been chosen
		return known[nowKnownWord];
	}
	
	public BufferedImage getUnknown(int range)
	{
		Random random = new Random();
		if(range%73==0) initialBox(2); //if the all random number is used, then initial it
		if(range>=73) range%=73;
		int select = random.nextInt(73-range);  //select the number of word want to choose
		nowUnknownWord = box2[select];
		box2[select] = box2[72-range]; //change the choose number with the last number in array
		//every time range will +1, so next time will not choose to the number has been chosen
		return unknown[nowUnknownWord];
	}
	
	public String getCorrectWord()
	{
		return correctWord[nowKnownWord];
	}
	
	public String getFilename()
	{
		return s2[nowUnknownWord];
	}
	
	public void initialBox(int in)
	{
		int i;
		if(in==1) for(i=0; i<51; i++) box1[i] = i;
		if(in==2) for(i=0; i<73; i++) box2[i] = i;
	}
	
}
