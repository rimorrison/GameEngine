package com.modernjoys.engineTester;

import org.lwjgl.opengl.Display;
import com.modernjoys.renderEngine.DisplayManager;

public class MainGameLoop 
{

	public static void main(String[] args) 
	{

		// open a display
		DisplayManager.createDisplay();

		while (!Display.isCloseRequested())

		// this is the game loop - objects are updated every frame and rendering
		// happens
		// the close rule is just checking if the 'x' is clicked in the windows
		// box
		{
			
			DisplayManager.updateDisplay();

		}

	}
}
