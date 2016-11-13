package com.modernjoys.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.modernjoys.models.RawModel;

public class Renderer 
{
	
	public void prepare() 
	// this gets called once every frame and it tells openGL to render the game
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(1, 0, 0, 1);
	}
	
	public void render(RawModel model)
	{
		// makes the VAO editable
		GL30.glBindVertexArray(model.getVaoID());
		// activate the attribute list where our data is stored
		GL20.glEnableVertexAttribArray(0);
	
		// this command is guided by the ShaderProgram's 'start()' method so the verticies in the VAO are copied over to the ShaderProgram's vertex shader for manipulation
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		
		// once finished rendering we have to disable the VAO index and unbind from the VAO
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		
	}

}
