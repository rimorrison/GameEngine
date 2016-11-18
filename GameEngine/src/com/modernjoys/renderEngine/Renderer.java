package com.modernjoys.renderEngine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import com.modernjoys.entities.Entity;
import com.modernjoys.models.RawModel;
import com.modernjoys.models.TexturedModel;
import com.modernjoys.shaders.StaticShader;
import com.modernjoys.toolbox.Maths;

public class Renderer 
{
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private Matrix4f m_projectionMatrix;
	
	public Renderer(StaticShader shader) 
	// not the projection matrix only has to be loaded up once right at the beginning and then never again since the values will never change - it will stay in the shader program code and work every time
	{
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(m_projectionMatrix);
		shader.stop();
		
	}
	
	public void prepare() 
	// this gets called once every frame and it tells openGL to render the game
	{
		// to prevent shapes from rendering in a random order 
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
	}
	
	public void render(Entity entity, StaticShader shader)
	{
		TexturedModel model = entity.getModel();
		RawModel rawModel = model.getRawModel();
		// makes the VAO editable
		GL30.glBindVertexArray(rawModel.getVaoID());
		// activate the attribute list where our data is stored
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		// this loads up the Entity changing positional data to the Transformation Matrix uniform variable in the vertexShader.tx that we can manipulate with what we setup in Maths
		// *********************** Key for moving Models ***************************************************
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(),  entity.getScale());
		// This loads the transformationMatrix to the shader.txt
		shader.loadTransformationMatrix(transformationMatrix);
		
		// this tells OpenGL which texture we are going to sample - Sampler2D in the frameMentShader.txt uses this location by default to store textures
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	
		// this command is guided by the ShaderProgram's 'start()' method so the verticies in the VAO are copied over to the ShaderProgram's vertex shader for manipulation
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		
		// once finished rendering we have to disable the VAO index and unbind from the VAO
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		
		
	}
	
	
	private void createProjectionMatrix() 
	{
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		m_projectionMatrix = new Matrix4f();
		m_projectionMatrix.m00 = x_scale;
		m_projectionMatrix.m11 = y_scale;
		m_projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		m_projectionMatrix.m23 = -1;
		m_projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		m_projectionMatrix.m33 = 0;	
	}

}
