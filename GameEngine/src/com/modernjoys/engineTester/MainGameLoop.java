package com.modernjoys.engineTester;

import org.lwjgl.opengl.Display;

import com.modernjoys.models.RawModel;
import com.modernjoys.renderEngine.DisplayManager;
import com.modernjoys.renderEngine.Loader;
import com.modernjoys.renderEngine.Renderer;
import com.modernjoys.shaders.StaticShader;

public class MainGameLoop 
{
	
	

	public static void main(String[] args) 
	{
		
		

		// open a display
		DisplayManager.createDisplay();		
		
		// Loader is used to load objects into a VAO - it also once returned keeps an indices buffer binded (as a current lookup)
		Loader loader = new Loader();
		
		// Render actually displays the object on the display using OpenGL
		Renderer renderer = new Renderer();
		
		// this build a shader program object that stores the vertex and fragment shader codes to apply to vertices this program overrides default draw calls when active
		StaticShader shader = new StaticShader();
		
		
	/*	float[] verticies = {			
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,				
				0.5f,0.5f,-0.5f,	
					
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f	
		};
		*/
		
		
		float[] verticies = 
		{

				-0.5f, 0.5f, 0f, // V0
				-0.5f, -0.5f, 0f, // V1
				0.5f, -0.5f, 0f, // V2
				0.5f, 0.5f, 0f, // V3

		};
		
		int[] indices = 
		{
				0, 1, 3, // Top left triangle (V0, V1, V3)
				3, 1, 2 // Bottom right triangle (V3, V1, V2)
		};
		
		RawModel model = loader.loadToVAO(verticies, indices);

		while (!Display.isCloseRequested())
		// this is the game loop - objects are updated every frame and rendering
		// happens
		// the close rule is just checking if the 'x' is clicked in the windows
		// box
		{
			
			renderer.prepare();
			// this tells the video card to use the OpenGL shader program while drawing to the screen to override default actions
			shader.start();
			// this tells the video card to draw the model to the screen - parts of its action is being over ridden by shader.start()
			renderer.render(model);
			// just tells video card to stop using the OpenGL shader program
			shader.stop();
			// // Update the window. If the window is visible clears the dirty flag and calls swapBuffers() and finally polls the input devices if processMessages is true.
			DisplayManager.updateDisplay();

		}
		
		// frees up all resources
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}
}
