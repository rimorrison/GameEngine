package com.modernjoys.renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.Dimension;

public class DisplayManager 
{
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);
	private static final int FPS_CAP = 120;
	
	private static long lastFrameTime;
	private static float delta;
	
	
	public static void createDisplay()
	{
		// 3,2 is the version of OpenGL we want to use with a few settings
		ContextAttribs attribs = new ContextAttribs(3,2)
				.withForwardCompatible(true)
				.withProfileCore(true);
		
		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Richard's first 3d game");
			// Display.setInitialBackground(0, 0, 1);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		// Tell the OpenGL to render the display in the whole display
		// We want whole display from bottom left to top right (buttom left (0, 0), top hight(width, height)
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDisplay()
	{
		// this sets the rate of the updates to the display refresh rate - FPS_CAP is set to 120
		Display.sync(FPS_CAP);
		Display.update();
		
		//long currentFrameTime = getCurrentTime();
		//delta = (currentFrameTime - lastFrameTime) / 1000f;
		//lastFrameTime = currentFrameTime;
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static void closeDisplay()
	{
		Display.destroy();
	}
	
	private static long getCurrentTime()
	{
		return Sys.getTime() * 1000 / Sys.getTimerResolution(); // sys.getTime is from LWJGL and it returns time in ticks
	}
			

}
