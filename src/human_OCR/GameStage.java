package human_OCR;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameStage extends AbstractGameStage implements Runnable //inherit abstract class and implement Runnable
{
	private int score, winScore, bg_X, ball_X, ball_Y, duck_X, duck_Y, word_Y, direction, totalScore;
	private Thread gameThread, typeThread;
	private ImagePanel myPanel;
	private JFrame myFrame;
	private JLabel myLabel, winLabel;
	private JTextField myField;
	private Typing myType;
	private boolean bgMove, ballMove, duckMove;
	
	public GameStage()
	{
		totalScore = 0; //totalScore won't become 0 if you call replay()
		myFrame = new JFrame(); //set Frame
		myFrame.setSize(1000, 500);
		myFrame.setTitle("HumanOCRun");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myField = new JTextField(); //set text input area
		myField.setBounds(10,420,295,25);
		myFrame.add(myField);
		myLabel = new JLabel(); //set score label
		myLabel.setBounds(870, 10, 100, 40);
		myLabel.setFont(new Font("Calibri", 1, 26));
		myFrame.add(myLabel);
		winLabel = new JLabel(); //set replay remind label
		winLabel.setBounds(460, 380, 400, 40);
		winLabel.setFont(new Font("Calibri", 1, 26));
		myFrame.add(winLabel);
		myPanel = new ImagePanel(this); //set panel
		myFrame.add(myPanel);
		myType = new Typing(this);
		myFrame.setVisible(true);
		initial(); //initial variable value
	}
	
	public void start()
	{
		gameThread = new Thread(this); //create gameThread
		gameThread.start(); //start
		typeThread = new Thread(myType); //create typeThread
		typeThread.start(); //start
	}
	
	public void replay()
	{
		initial(); //initial value again
	}
	
	public void addScore()
	{
		score++; //add score and totalScore
		totalScore++;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setWinScore(int winScore)
	{
		this.winScore = winScore;
	}
	
	public int getWinScore()
	{
		return winScore;
	}
	
	public void run()
	{
		int delay1 = 0, delay2 = 0, delay3 = 0;
		Thread currentThread = Thread.currentThread();
		while (currentThread == gameThread)
		{
			myFrame.repaint(); //repaint
			
			if(bgMove) //if background need to move
			{
				if(delay1<25)
				{
					bg_X--;
					delay1++;
				}
				else
				{
					delay1 = 0;
					bgMove = false;
				}
			}
			
			if(ballMove) //if ball need to move
			{
				if(delay2<25)
				{
					ball_X--;
					delay2++;
				}
				else
				{
					delay2 = 0;
					ballMove = false;
				}
			}
			
			if(duckMove) //if duck need to move
			{
				if(delay3<30)
				{
					duck_X++;
					delay3++;
				}
				else
				{
					delay3 = 0;
					duckMove = false;
				}
			}
			
			if(score<winScore) //if still not win, let duck and ball floating and words move from up to down
			{
				floating();
				upToDown();
			}
			else //if win, set winLabel to tell to user how to replay the game
			{
				winLabel.setText("Type in \"replay\" to restart the game");
			}
			
			try 
			{
				Thread.sleep(40); //delay
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public int getbg_X()
	{
		return bg_X;
	}
	
	public int getBall_X()
	{
		return ball_X;
	}
	
	public int getBall_Y()
	{
		return ball_Y;
	}
	
	public int getDuck_X()
	{
		return duck_X;
	}
	
	public int getDuck_Y()
	{
		return duck_Y;
	}
	
	public int getWord_Y()
	{
		return word_Y;
	}
	
	public void setWord_Y(int in)
	{
		word_Y = in;
	}
		
	public void bgMove()
	{
		bgMove = true;
	}
	
	public void ballMove()
	{
		ballMove = true;
	}
	
	public void duckMove()
	{
		duckMove = true;
	}
	
	public void floating()
	{
		//let duck and ball float
		if (direction == 0 && duck_Y > 250) direction = 1;
		else if (direction == 1 && duck_Y < 230) direction = 0;
		if (direction == 0)
		{
			duck_Y++;
			ball_Y++;
		}
		else if(direction == 1)
		{
			duck_Y--;
			ball_Y--;
		}
	}
	
	public void upToDown()
	{
		//let words move from up to down
		if(word_Y>400) word_Y = 0;
		else word_Y+=3;
	}
	
	public Thread getTypeThread()
	{
		return typeThread;
	}
	
	public ImagePanel getPanel()
	{
		return myPanel;
	}
	
	public JLabel getLabel()
	{
		return myLabel;
	}
	
	public JTextField getField()
	{
		return myField;
	}
	
	public void initial()
	{
		//initial value
		score = 0;
		bgMove = false;
		ballMove = false;
		duckMove = false;
		direction = 0;
		bg_X = 320;
		duck_X = 580;
		duck_Y = 240;
		ball_X = 1000;
		ball_Y = 240;
		myField.setText("");
		myLabel.setText("Score: 0");
		winLabel.setText("");
		myPanel.setTempScore(-1); //when tempScore!=score, I will get a new known word and a new unknown word
		//so set tempScore to -1 at beginning to get first words
	}
	
	public int getTotalScore()
	{
		return totalScore;
	}
	
}
