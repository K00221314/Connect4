package connect4;

/**
 *
 *
 *
 * @author Rob
 */
import java.util.logging.Logger;

public final class GameLogger implements Player
{

	private final static Logger LOGGER = Logger.getLogger(GameLogger.class.getName());


	String lastLog;

	private GameLogger()
	{
	}

	
	//Lasy Loading  Singleton Patter
	public static GameLogger getInstance()
	{
		if (singleInstance == null)
		{
			singleInstance = new GameLogger();
		}
		return singleInstance;
	}
	private static GameLogger singleInstance;
	
	
	public void log(String text)
	{
		LOGGER.info(text);
		lastLog = text;
	}

	@Override
	public void gameStarted()
	{
		log("Game Started");
	}

	@Override
	public void nextTurn()
	{
	}

	@Override
	public void displayBoard(int[][] board)
	{
		String str = "";
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[0].length; j++)
			{
				str = str + board[i][j];
			}
			str = str + '\n';
		}
		LOGGER.info(str);
	}

	@Override
	public void winner(int winner)
	{
		log("Game won by player " + winner);
	}

	@Override
	public void draw()
	{
		log("Game Draw");

	}
}
