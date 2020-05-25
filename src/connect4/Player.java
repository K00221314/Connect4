package connect4;

/**
 *
 * @author Rob
 */
public interface Player
{

	void gameStarted();

	void nextTurn();

	void displayBoard(int[][] board);

	void winner(int winner);

	void draw();
}
