package connect4;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Rob
 */
public class Connect4
{

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private static int choice;
	private static boolean turnbuttonOn = false;

	public Connect4()
	{
		prepareGUI();
	}

	public static void main(String[] args)
	{
		Connect4 swingLayout = new Connect4();
		swingLayout.showBorderLayout();
	}

	private void prepareGUI()
	{
		mainFrame = new JFrame("Connect 4 Game");
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(3, 1));

		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);
		statusLabel.setSize(350, 100);

		mainFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);
	}

	private void showBorderLayout()
	{
		headerLabel.setText("Welcome to connect 4 game");
		statusLabel.setText("<html>Instructions<br>1. Press the start button to begin the game. <br>2. Seperate the two screens. <br>3. Begin with player 1 and enter a number between 1 and 7. <br>4. Enjoy!.</html>");

		JButton okButton = new JButton("Start");
		JButton cancelButton = new JButton("Exit");

		okButton.setActionCommand("Start");
		cancelButton.setActionCommand("Exit");

		okButton.addActionListener(new ButtonClickListener());
		cancelButton.addActionListener(new ButtonClickListener());

		controlPanel.add(okButton);
		controlPanel.add(cancelButton);

		mainFrame.setVisible(true);
		while (true)
		{
			try
			{
				Thread.sleep(300);
			}
			catch (InterruptedException e)
			{

			}
			if (turnbuttonOn)
			{
				mainFrame.setVisible(false);
				break;
			}
		}
		int i = choice;

		switch (i)
		{
			case 1:
				playGame();
				break;
			default:
				playGame();
		}

	}

	private class ButtonClickListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();

			if (command.equals("Start"))
			{
				button1Clicked();
			}
			else if (command.equals("Exit"))
			{
				System.exit(0);
			}
		}
	}

	static void button1Clicked()
	{

		choice = 1;
		turnbuttonOn = true;

	}

	public static void playGame()
	{
		GameLogger.getInstance().log("Game is started");
		Game model = new Game.Builder().build();

		PlayerClass player = new PlayerClass(model, 1);
		PlayerClass player1 = new PlayerClass(model, 2);
		model.startGame();
	}
}
