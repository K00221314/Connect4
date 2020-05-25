package connect4;

/**
 *
 * @author Rob
 */
interface Player
{
	void displayBoard(Game game);
	void drawGame();
	void gameStarted();
	void nextTurn();
	void winner(int winner);
}
