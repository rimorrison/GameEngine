package com.modernjoys.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.modernjoys.models.RawModel;
import com.modernjoys.models.TexturedModel;

public class Renderer 
{
	
	public void prepare() 
	// this gets called once every frame and it tells openGL to render the game
	{
		GL11.glClearColor(1, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public void render(TexturedModel texturedModel)
	{
		RawModel model = texturedModel.getRawModel();
		// makes the VAO editable
		GL30.glBindVertexArray(model.getVaoID());
		// activate the attribute list where our data is stored
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		// this tells OpenGL which texture we are going to sample - Sampler2D in the frameMentShader.txt uses this location by default to store textures
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
	
		// this command is guided by the ShaderProgram's 'start()' method so the verticies in the VAO are copied over to the ShaderProgram's vertex shader for manipulation
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		
		// once finished rendering we have to disable the VAO index and unbind from the VAO
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		
		
	}

}
