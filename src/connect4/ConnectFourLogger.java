package connect4;

/**
 *
 * @author Rob
 */
public interface ConnectFourLogger
{

	void displayBoard(Game game);

	void drawGame();

	void gameStarted();

	void log(String text);

	void winner(int winner);
}
