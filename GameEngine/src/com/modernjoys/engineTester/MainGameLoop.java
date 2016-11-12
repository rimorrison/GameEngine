package com.modernjoys.engineTester;

import org.lwjgl.opengl.Display;

import com.modernjoys.renderEngine.DisplayManager;
import com.modernjoys.renderEngine.Loader;
import com.modernjoys.renderEngine.RawModel;
import com.modernjoys.renderEngine.Renderer;
import com.modernjoys.shaders.StaticShader;

public class MainGameLoop 
{
	
	

	public static void main(String[] args) 
	{
		
		

		// open a display
		DisplayManager.createDisplay();		
		
		// Loader is used to load an object into a VAO
		Loader loader = new Loader();
		
		// Render actually displays the object on the display
		Renderer renderer = new Renderer();
		
		// this build a shader program object that stores the vertex and fragment shader codes to apply to vertices
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
		
		
		float[] verticies = {

				-0.5f, 0.5f, 0f, // V0
				-0.5f, -0.5f, 0f, // V1
				0.5f, -0.5f, 0f, // V2
				0.5f, 0.5f, 0f, // V3

		};
		
		int[] indices = {
				0, 1, 3, // Top left triangle (V0, V1, V3)
				3, 1, 2 // Bottom right triangle (V3, V1, 2)
		};
		
		RawModel model = loader.loadToVAO(verticies, indices);

		while (!Display.isCloseRequested())
		// this is the game loop - objects are updated every frame and rendering
		// happens
		// the close rule is just checking if the 'x' is clicked in the windows
		// box
		{
			
			renderer.prepare();
			shader.start();
			renderer.render(model);
			shader.stop();
			DisplayManager.updateDisplay();

		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}
}
