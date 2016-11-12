package com.modernjoys.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

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
		GL30.glBindVertexArray(model.getVaoID());
		// activate the attribute list where our data is stored
		GL20.glEnableVertexAttribArray(0);
	
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
		
		
		// once finished rendering we have to disable the VAO intex and unbind from the VAO
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		
	}

}
