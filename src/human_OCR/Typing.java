package human_OCR;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Typing implements KeyListener, Runnable
{
	private GameStage gs;
	private ImagePanel myPanel;
	private JLabel myLabel;
	private JTextField myField;
	private FileWriter file;
	private BufferedWriter writer;
	
	public Typing(GameStage g)
	{
		gs = g;
		myPanel = gs.getPanel();
		myLabel = gs.getLabel();
		myField = gs.getField();
		myField.addKeyListener(this);
		try
		{
			file = new FileWriter("output.txt"); //write to output.txt
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		writer = new BufferedWriter(file);
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_ENTER) //Press ENTER
		{
			String str1, str2;
			String[] str3 = new String[2];
			str3[0]="";
			str3[1]="";
			str1 = myPanel.getWords().getCorrectWord(); //the known word
			str2 = myField.getText(); //user input
			if(str2.equals("replay")) //if input is "replay", then replay the game
			{
				gs.replay();
			}
			else if(gs.getScore() < gs.getWinScore()) //if still not win
			{
				if(str2.contains(" ")) str3 = str2.split(" ", 2); //split to two word
				if(myPanel.getMode()==0) //mode 1, known -> unknown
				{
					if(str1.equals(str3[0]) && !str3[1].equals("")) //known is correct and unknown!=""
					{
						gs.addScore();
						myLabel.setText("Score: " + gs.getScore()); //update the score label
						myField.setBackground(Color.green); //show green, means correct
						myField.setText(""); //clean the text area
						if(gs.getScore() >= gs.getWinScore()-10) //according the score, choose which image needs to move
						{
							if(gs.getScore() >= gs.getWinScore()-6)
							{
								gs.duckMove();
							}
							else
							{
								gs.bgMove();
								gs.ballMove();
							}
						}
						else gs.bgMove();
						
						//we have 73 unknown word. Before all get, we need to write it to output.txt
						if(gs.getTotalScore()<=73)
						{
							try 
							{
								writer.write(myPanel.getWords().getFilename() + " " + str3[1]);
								writer.flush();
								writer.newLine();
							}
							catch (IOException e1)
							{
								e1.printStackTrace();
							}
						}
					}
					else //show red, means wrong
					{
						myField.setBackground(Color.red);
					}
				}
				else //mode 2, unknown -> known
				{
					//this part is almost similar to mode 1, only change the order
					if(str1.equals(str3[1]) && !str3[0].equals(""))
					{
						gs.addScore();
						myLabel.setText("Score: " + gs.getScore());
						myField.setBackground(Color.green);
						myField.setText("");
						if(gs.getScore() >= gs.getWinScore()-10)
						{
							if(gs.getScore() >= gs.getWinScore()-6)
							{
								gs.duckMove();
							}
							else
							{
								gs.bgMove();
								gs.ballMove();
							}
						}
						else gs.bgMove();
						
						if(gs.getTotalScore()<=73)
						{
							try 
							{
								writer.write(myPanel.getWords().getFilename() + " " + str3[0]);
								writer.flush();
								writer.newLine();
							}
							catch (IOException e1)
							{
								e1.printStackTrace();
							}
						}
					}
					else
					{
						myField.setBackground(Color.red);
					}
				}
			}
		}
	}

	public void keyReleased(KeyEvent e)
	{
		
	}

	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public void run()
	{
		//if the text area is not white, then delay a period of time, then set back to white
		Thread currentThread = Thread.currentThread();
		while(currentThread == gs.getTypeThread())
		{
			if(!myField.getBackground().equals(Color.white))
			{
				try
				{
					Thread.sleep(500);
					myField.setBackground(Color.white);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}

	}
	
}
