package connect4;

import java.awt.JobAttributes;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class Game
{

	
	
	private int[][] board;
	private final int HEIGHT;
	private final int WIDTH;
	private int WINNER;
	private final int WIN;
	private boolean draw;
	private final List<Player> players = new CopyOnWriteArrayList<>();

	static class Builder
	{

		private int width = 7;
		private int height = 6;
		private int win = 4;

		Builder()
		{
		}

		Builder width(int width)
		{
			if (width < 3 || width > 20)
			{
				throw new IllegalArgumentException();
			}
			return this;
		}

		Builder height(int height)
		{
			if (height < 3 || height > 20)
			{
				throw new IllegalArgumentException();
			}
			return this;
		}

		Builder win(int win)
		{
			if (win < 3 || win > 10)
			{
				throw new IllegalArgumentException();
			}
			return this;
		}

		Game build()
		{
			return new Game(this);
		}
	}

	Game(Builder builder)
	{
		WIN = builder.win;
		HEIGHT = builder.height;
		WIDTH = builder.width;

		board = new int[HEIGHT][WIDTH];
		GameLogger.getInstance().log("Created Model");
	}

	void addPlayer(Player player)
	{
		if (player == null)
		{
			throw new NullPointerException();
		}
		if (players.contains(player))
		{
			throw new IllegalStateException();
		}
		players.add(player);
	}

	void startGame()
	{

		initializeBoard();
		gameStarted();
		displayBoard();
		Player player1 = players.get(0);
		Player player2 = players.get(1);

		while (true)
		{

			yourTurn(player1);
			displayBoard();
			if (playerHasWon())
			{
				callWinner();
				break;
			}
			if (isDraw())
			{
				callWinner();
				break;
			}
			yourTurn(player2);
			displayBoard();
			if (playerHasWon())
			{

				callWinner();
				break;
			}
			if (isDraw())
			{
				callWinner();
				break;
			}
		}

	}

	boolean isDraw()
	{
		boolean flag = true;
		for (int i = 0; i < WIDTH; i++)
		{
			if (board[0][i] == 0)
			{
				flag = false;
			}
		}
		if (flag)
		{
			GameLogger.getInstance().log("Game is Draw");
			draw = true;
		}
		return flag;
	}

	void yourTurn(Player player)
	{
		player.nextTurn();
	}

	void gameStarted()
	{
		for (Player player : players)
		{
			player.gameStarted();
		}
	}

	boolean nextMoveColumn(int column, int id)
	{
		GameLogger.getInstance().log("Player " + id + " dropped in column " + column);
		if (column > 6 || column < 0)
		{
			GameLogger.getInstance().log("Wrong column value by Player " + id);
			return false;
		}
		boolean flag = false;
		for (int i = HEIGHT - 1; i >= 0; i--)
		{
			if (board[i][column] == 0)
			{
				board[i][column] = id;
				flag = true;
				break;
			}
		}
		if (!flag)
		{
			GameLogger.getInstance().log("No space in this column, cannot play move by Player " + id);
			return false;
		}
		return true;
	}

	void displayBoard()
	{
		for (Player player : players)
		{
			player.displayBoard(this);
		}
	}

	int[][] getBoard()
	{
		return board;
	}

	void setBoard(int[][] b)
	{
		this.board = b;
	}

	public String boardToString()
	{
		String textString = "";
		for (int row = 0; row < board.length; row++)
		{
			textString += boardRowToString(row) + System.lineSeparator();
		}
		return textString.trim();
	}

	private String boardRowToString(int row)
	{
		String textString = "";
		for (int column = 0; column < board[0].length; column++)
		{
			textString += board[row][column];
		}
		return textString;
	}

	int getWinner()
	{
		return WINNER;
	}

	boolean checkForVertical(int row, int column)
	{
		for (int i = 0; i < this.WIN; i++)
		{
			if (board[row][column] != 0 && board[row + i][column] != 0 && (board[row][column] == board[row + i][column]))
			{
				continue;
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	boolean checkForHorizontal(int row, int column)
	{
		for (int i = 0; i < this.WIN; i++)
		{
			if (board[row][column] != 0 && board[row][column + i] != 0 && (board[row][column] == board[row][column + i]))
			{
				continue;
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	boolean checkForDiagonalLR(int row, int column)
	{
		for (int i = 0; i < this.WIN; i++)
		{
			if (board[row][column] != 0 && board[row + i][column - i] != 0 && (board[row][column] == board[row + i][column - i]))
			{
				continue;
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	boolean checkForDiagonalRL(int row, int column)
	{
		for (int i = 0; i < this.WIN; i++)
		{
			if (board[row][column] != 0 && board[row + i][column + i] != 0 && (board[row][column] == board[row + i][column + i]))
			{
				continue;
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	boolean playerHasWon()
	{

		for (int j = 0; j < WIDTH; j++)
		{
			for (int i = 0; i < HEIGHT / 2; i++)
			{

				if (checkForVertical(i, j))
				{
					WINNER = board[i][j];
					return true;
				}
			}
		}
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH / 2 + 1; j++)
			{
				if (checkForHorizontal(i, j))
				{
					WINNER = board[i][j];
					return true;
				}
			}
		}

		for (int i = 0; i < HEIGHT / 2; i++)
		{
			for (int j = WIDTH - 1; j >= WIDTH / 2; j--)
			{
				if (checkForDiagonalLR(i, j))
				{
					WINNER = board[i][j];
					return true;
				}
			}
		}

		for (int i = 0; i < HEIGHT / 2; i++)
		{
			for (int j = 0; j < WIDTH / 2 + 1; j++)
			{
				if (checkForDiagonalRL(i, j))
				{
					WINNER = board[i][j];
					return true;
				}
			}
		}
		return false;
	}

	void callWinner()
	{
		if (draw)
		{
			for (Player player : players)
			{
				player.drawGame();
			}
		}
		else
		{
			for (Player player : players)
			{
				player.winner(WINNER);
			}
		}
	}

	void initializeBoard()
	{
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				board[i][j] = 0;
			}
		}
	}
}
