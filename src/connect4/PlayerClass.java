package connect4;

import com.sun.org.apache.xpath.internal.XPathVisitable;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

final class PlayerClass implements Player
{

	private final ConnectFourLogger logger;
	private Game game;
	private int id;
	private int column;
	private boolean turnbuttonFlag;
	private boolean buttonFlag;
	private JFrame frame = new JFrame("Connect4");
	private JButton button = new JButton("Submit");
	private JTextField textField = new JTextField();
	private JTextArea boardArea = new JTextArea(5, 5);
	private JTextArea logArea = new JTextArea();

	PlayerClass(ConnectFourLogger logger, Game model, int id) throws NullPointerException, IllegalStateException
	{
		this.logger = logger;
		this.game = model;
		this.id = id;
		this.buttonFlag = false;
		this.turnbuttonFlag = false;

		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(3, 0));

		JPanel topPanel = new JPanel();
		topPanel.add(boardArea);
		frame.add(topPanel);

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
		frame.getContentPane().add(centerPanel);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(button, BorderLayout.EAST);
		bottomPanel.add(textField, BorderLayout.CENTER);
		frame.getContentPane().add(bottomPanel);

		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				buttonClicked();
			}
		});
		frame.setVisible(true);
		logger.log("Created View for Player " + this.id);

	}

	void buttonClicked()
	{

		if (this.buttonFlag)
		{
			this.buttonFlag = false;
			int column = 0;
			try
			{

				column = Integer.parseInt(textField.getText());

				textField.setText("");
				this.column = column - 1;
				this.turnbuttonFlag = true;
				logger.log("Correct Input by player " + id);
			}
			catch (NumberFormatException e)
			{
				logArea.append("Wrong input \n");
				logger.log("Wrong input by Player " + id);
				this.buttonFlag = true;
			}
		}
	}

	@Override
	public void gameStarted()
	{
		logArea.append("Welcome Player " + id + "\n");
	}

	@Override
	public void nextTurn()
	{
		logArea.append("Your Turn Enter a number between 1 to 7" + "\n");
		logger.log("Player " + id + "'s turn");

		this.buttonFlag = true;
		while (true)
		{
			try
			{
				Thread.sleep(300);
			}
			catch (InterruptedException e)
			{
			}
			while (true)
			{
				try
				{
					Thread.sleep(300);
				}
				catch (InterruptedException e)
				{
				}
				if (this.turnbuttonFlag)
				{
					break;
				}
			}
			this.turnbuttonFlag = false;

			if (game.nextMoveColumn(column, id))
			{
				logArea.append("You dropped coin in column " + (column + 1) + System.lineSeparator());
				break;

			}
			else
			{
				logArea.append("Unable to play this move, try again" + "\n");
				this.buttonFlag = true;
			}
		}
	}

	@Override
	public void displayBoard(Game game)
	{
		boardArea.setText(game.boardToString());
	}

	@Override
	public void winner(int winner)
	{

		logArea.append("Player " + winner + " Won the game");
	}

	@Override
	public void drawGame()
	{
		logArea.append("Game Draw");

	}

}
