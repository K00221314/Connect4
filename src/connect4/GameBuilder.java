/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

public class GameBuilder
{

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getWin()
	{
		return win;
	}

	public ConnectFourLogger getLogger()
	{
		return logger;
	}

	private int width = 7;
	private int height = 6;
	private int win = 4;
	private ConnectFourLogger logger;

	GameBuilder()
	{
	}

	GameBuilder width(int width)
	{
		if (width < 3 || width > 20)
		{
			throw new IllegalArgumentException();
		}
		this.width = width;
		return this;
	}

	GameBuilder height(int height)
	{
		if (height < 3 || height > 20)
		{
			throw new IllegalArgumentException();
		}
		this.height = height;

		return this;
	}

	GameBuilder logger(ConnectFourLogger logger)
	{
		if (logger == null)
		{
			throw new IllegalArgumentException();
		}
		this.logger = logger;
		return this;
	}

	GameBuilder win(int win)
	{
		if (win < 3 || win > 10)
		{
			throw new IllegalArgumentException();
		}
		this.win = win;

		return this;
	}

	Game build()
	{
		return new Game(logger, height, width, win);
	}
}
