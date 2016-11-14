package com.modernjoys.renderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.modernjoys.models.RawModel;

public class Loader {
	
	private List<Integer> m_vaos = new ArrayList<Integer>();
	private List<Integer> m_vbos = new ArrayList<Integer>();
	private List<Integer> m_textures = new ArrayList<Integer>();
	
	public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices) 
	{
		int vaoID = createVAO();
		m_vaos.add(vaoID);
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
	
		unbindVAO();
		
		// note it takes 3 points (x, y, z) to make 1 vertices so we divide by 3 to get the right vertex count when using positions but when using indicies this is not necessary
		return new RawModel(vaoID, indices.length);
		
	}
	
	
	public int loadTexture(String fileName)
	{
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" +fileName+ ".png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		m_textures.add(textureID);
		return textureID;
	}
	
	
	
	
	private int createVAO()
	{
		// this actually creates a VAO and stores its ID into the vaoID variable
		int vaoID = GL30.glGenVertexArrays();
		// Binding the VAO make the current VAO editable
		GL30.glBindVertexArray(vaoID);
		return vaoID;
		
	}
	
	private void bindIndicesBuffer(int[] indices)
	{
		// this actually creates a VBO and stores its ID into the vboID variable
		int vboID = GL15.glGenBuffers();
		m_vbos.add(vboID);
		// Binding the VBO makes the current VBO indicies buffer the currently active indices lookup 
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		// prepares an int buffer and stores an int array (the inices) into this intBuffer
		IntBuffer buffer = storeDataInIntBuffer(indices);
		// this actually stores intbuffer data (the indicies) 
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
	}
	
	
	private IntBuffer storeDataInIntBuffer(int [] data) 
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) 
	{
		// actually creates a VBO and stores its id in vboID
		int vboID = GL15.glGenBuffers();
		
		m_vbos.add(vboID);
		// Bind the active VBO for editing
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		// convert the float array above to a float buffer
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		// this actually stores the buffer contents into the 'VBO'
		// static draw means we will never want to go back and edit the data once it is put back into the VBO
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		// ****this actually links up the VBO to the VAO by telling the VAO zero pointer to point to the currently active VBO****
		// needs to know (where in the VAO index, diemsion of the buffer being stored - either 2 dim or 3 exct, type of data float[],  if it is normalized, distance between vertices called'stride', offset
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		//we are now unbinding the VBO since we are done with it
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) 
	// converting a float array to a float buffer
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	
	public void cleanUp(){
		for(Integer vao : m_vaos)
		{
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(Integer vbo : m_vbos)
		{
			GL15.glDeleteBuffers(vbo);
		}
		
		for(int texture : m_textures)
		{
			// deletes the texture from memory
			GL11.glDeleteTextures(texture);
		}
	}

}
