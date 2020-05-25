package connect4;

/**
 *
 *
 *
 * @author Rob
 */
import java.util.logging.Logger;

final class GameLogger implements ConnectFourLogger
{

	/**
	 * Singleton Pattern
	 * @return 
	 */
	static GameLogger getInstance()
	{
		if (singleInstance == null)
		{
			singleInstance = new GameLogger();
		}
		return singleInstance;
	}
	private static GameLogger singleInstance;


	private final static Logger LOGGER = Logger.getLogger(GameLogger.class.getName());


	private GameLogger()
	{
	}

	public void log(String text)
	{
		LOGGER.info(text);
	}

	@Override
	public void gameStarted()
	{
		log("Game Started");
	}

	@Override
	public void displayBoard(Game game)
	{
		LOGGER.info(game.boardToString());
	}

	@Override
	public void winner(int winner)
	{
		log("Game won by player " + winner);
	}

	@Override
	public void drawGame()
	{
		log("Game Draw");

	}
}
