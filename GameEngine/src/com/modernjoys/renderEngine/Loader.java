package com.modernjoys.renderEngine;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	
	public RawModel loadToVAO(float[] positions) 
	{
		int vaoID = createVAO();
		vaos.add(vaoID);
		storeDataInAttributeList(0, positions);
		unbindVAO();
		
		// not it take 3 points (x, y, z) to make 1 vertices so we divide by 3 to get the right vertex count
		return new RawModel(vaoID, positions.length / 3);
		
	}
	
	private int createVAO()
	{
		// this actually creates a VAO and stores its ID into the vaoID
		int vaoID = GL30.glGenVertexArrays();
		// Bind the VAO
		GL30.glBindVertexArray(vaoID);
		return vaoID;
		
	}
	
	private void storeDataInAttributeList(int attributeNumber, float[] data) 
	{
		// actually creates a VBO and stores its id in vboID
		int vboID = GL15.glGenBuffers();
		
		vbos.add(vboID);
		// Bind the active VBO for editing
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		// convert the float array above to a float buffer
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		// this actually stores the buffer contents into the 'VBO'
		// static draw means we will never want to go back and edit the data once it is put back into the VBO
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		// ****this actually links up the VBO to the VAO by telling the VAO zero pointer to point to the currently active VBO****
		// needs to know (where in the VAO index, diemsion of the buffer being stored - either 2 dim or 3 exct, type of data float[],  if it is normalized, distance between vertices called'stride', offset
		GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0);
		//we are now unbinding the VBO since we are done with it
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) 
	// converting a float array to a float buffer
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public void cleanUp(){
		for(Integer vao : vaos)
		{
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(Integer vbo : vbos)
		{
			GL15.glDeleteBuffers(vbo);
		}
	}

}
