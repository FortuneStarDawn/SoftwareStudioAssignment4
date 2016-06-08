package human_OCR;

public class Game
{

	public static void main(String[] args)
	{
		GameStage gs = new GameStage(); //new a GameStage
		gs.setWinScore(20); //set winScore to 20
		gs.start(); //start game
	}

}
