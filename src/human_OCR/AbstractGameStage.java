package human_OCR;

public abstract class AbstractGameStage //abstract class
{
	public abstract void start();
	public abstract void replay();
	public abstract void addScore();
	public abstract int getScore();
	public abstract void setWinScore(int winScore);
}
