package connect4;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class Game
{

	private final ConnectFourLogger logger;

	private int[][] board;
	private final int height;
	private final int width;
	private int playerWinner;
	private final int win;
	private boolean draw;
	private final List<Player> players = new CopyOnWriteArrayList<>();

	Game(ConnectFourLogger logger, int height, int width, int win)
	{
		this.logger = logger;
		this.height = height;
		this.width = width;
		this.win = win;

		board = new int[height][width];

		logger.log("Created Model");
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
		logger.log("Game is started");

		Player player1 = new PlayerClass(logger, this, 1);
		Player player2 = new PlayerClass(logger, this, 2);
		this.players.clear();
		this.addPlayer(player1);
		this.addPlayer(player2);

		initializeBoard();
		gameStarted();
		displayBoard();

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
		for (int i = 0; i < width; i++)
		{
			if (board[0][i] == 0)
			{
				flag = false;
			}
		}
		if (flag)
		{
			logger.log("Game is Draw");
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
		logger.log("Player " + id + " dropped in column " + column);
		if (column > 6 || column < 0)
		{
			logger.log("Wrong column value by Player " + id);
			return false;
		}
		boolean flag = false;
		for (int i = height - 1; i >= 0; i--)
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
			logger.log("No space in this column, cannot play move by Player " + id);
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
		return playerWinner;
	}

	boolean checkForVertical(int row, int column)
	{
		for (int i = 0; i < this.win; i++)
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
		for (int i = 0; i < this.win; i++)
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
		for (int i = 0; i < this.win; i++)
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
		for (int i = 0; i < this.win; i++)
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

		for (int j = 0; j < width; j++)
		{
			for (int i = 0; i < height / 2; i++)
			{

				if (checkForVertical(i, j))
				{
					playerWinner = board[i][j];
					return true;
				}
			}
		}
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width / 2 + 1; j++)
			{
				if (checkForHorizontal(i, j))
				{
					playerWinner = board[i][j];
					return true;
				}
			}
		}

		for (int i = 0; i < height / 2; i++)
		{
			for (int j = width - 1; j >= width / 2; j--)
			{
				if (checkForDiagonalLR(i, j))
				{
					playerWinner = board[i][j];
					return true;
				}
			}
		}

		for (int i = 0; i < height / 2; i++)
		{
			for (int j = 0; j < width / 2 + 1; j++)
			{
				if (checkForDiagonalRL(i, j))
				{
					playerWinner = board[i][j];
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
				player.winner(playerWinner);
			}
		}
	}

	void initializeBoard()
	{
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				board[i][j] = 0;
			}
		}
	}
}
