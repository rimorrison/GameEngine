package com.modernjoys.engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.modernjoys.entities.Camera;
import com.modernjoys.entities.Entity;
import com.modernjoys.models.RawModel;
import com.modernjoys.models.TexturedModel;
import com.modernjoys.renderEngine.DisplayManager;
import com.modernjoys.renderEngine.Loader;
import com.modernjoys.renderEngine.OBJLoader;
import com.modernjoys.renderEngine.Renderer;
import com.modernjoys.shaders.StaticShader;
import com.modernjoys.textures.ModelTexture;

public class MainGameLoop 
{
	
	

	public static void main(String[] args) 
	{
		
		

		// open a display
		DisplayManager.createDisplay();		
		
		// Loader is used to load objects into a VAO - it also once returned keeps an indices buffer binded (as a current lookup)
		Loader loader = new Loader();
		
		// this build a shader program object that stores the vertex and fragment shader codes to apply to vertices this program overrides default draw calls when active
		StaticShader shader = new StaticShader();
		
		// Render actually displays the object on the display using OpenGL - has to be created after the shader is created because of the projection matrix setup
		Renderer renderer = new Renderer(shader);
		
		/*
		float[] vertices = {            
                -0.5f,0.5f,0,   
                -0.5f,-0.5f,0,  
                0.5f,-0.5f,0,   
                0.5f,0.5f,0,        
                 
                -0.5f,0.5f,1,   
                -0.5f,-0.5f,1,  
                0.5f,-0.5f,1,   
                0.5f,0.5f,1,
                 
                0.5f,0.5f,0,    
                0.5f,-0.5f,0,   
                0.5f,-0.5f,1,   
                0.5f,0.5f,1,
                 
                -0.5f,0.5f,0,   
                -0.5f,-0.5f,0,  
                -0.5f,-0.5f,1,  
                -0.5f,0.5f,1,
                 
                -0.5f,0.5f,1,
                -0.5f,0.5f,0,
                0.5f,0.5f,0,
                0.5f,0.5f,1,
                 
                -0.5f,-0.5f,1,
                -0.5f,-0.5f,0,
                0.5f,-0.5f,0,
                0.5f,-0.5f,1
                 
        };
         
        float[] textureCoords = {
                 
                0,0,
                0,1,
                1,1,
                1,0,            
                0,0,
                0,1,
                1,1,
                1,0,            
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0
 
                 
        };
         
        int[] indices = {
                0,1,3,  
                3,1,2,  
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,   
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22
 
        };
		*/
		
		
		/*
		float[] vertices = 
		{

				-0.5f, 0.5f, 0f, // V0
				-0.5f, -0.5f, 0f, // V1
				0.5f, -0.5f, 0f, // V2
				0.5f, 0.5f, 0f, // V3

		};
		
		float[] textureCoords = {
				0f, 0f, // V0
				0f, 1f, // V1
				1f, 1f, // V2
				1f, 0f // V3
		};
		
		int[] indices = 
		{
				0, 1, 3, // Top left triangle (V0, V1, V3)
				3, 1, 2 // Bottom right triangle (V3, V1, V2)
		};
		
		*/
		
		//RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		// ModelTexture texture = new ModelTexture(loader.loadTexture("cathedral")); // loader.loadTexture("image") - returns the ID of the texture that is loaded to memory. So all ModelTexture stores is the ID of the texture
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
		// entity constructor takes a (TexturedModel, Vec3 position vector where we want to render entity, Entity RotationX, Entity RotationY, Entity RotationZ, Entity scale)
		Entity entity = new Entity(staticModel, new Vector3f(0, -5f, -50), 0, 180, 0, 1 );
		
		Camera camera = new Camera();
		
		//entity.setScale(0.2f);

		while (!Display.isCloseRequested())
		{
		// this is the game loop - objects are updated every frame and rendering
		// happens
		// the close rule is just checking if the 'x' is clicked in the windows
		// box
	
			entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.prepare();
			// this tells the video card to use the OpenGL shader program while drawing to the screen to override default actions
			shader.start();
			
			// every frame we want to load up the viewMatrix to the camera. this will move the whole world
			shader.loadViewMatrix(camera);
			// this tells the video card to draw the model to the screen - parts of its action is being over ridden by shader.start()
			renderer.render(entity, shader);
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
