package com.modernjoys.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class ShaderProgram {
	
	private int m_programID;
	private int m_vertexShaderID;
	private int m_fragmentShaderID;
	
	public ShaderProgram(String vertexFile, String fragmentFile)
	{
		m_vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		m_fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		m_programID = GL20.glCreateProgram();
		GL20.glAttachShader(m_programID,  m_vertexShaderID);;
		GL20.glAttachShader(m_programID, m_fragmentShaderID);
		
		// connects a VAO to the shader program variable
		bindAttributes();
		
		GL20.glLinkProgram(m_programID);
		GL20.glValidateProgram(m_programID);
	}
	
	public void start()
	{
		GL20.glUseProgram(m_programID);
	}
	
	public void stop()
	{
		GL20.glUseProgram(0);		
	}
	
	public void cleanUp()
	{
		stop();
		GL20.glDetachShader(m_programID, m_vertexShaderID);
		GL20.glDetachShader(m_programID, m_fragmentShaderID);
		GL20.glDeleteShader(m_vertexShaderID);
		GL20.glDeleteShader(m_fragmentShaderID);
		GL20.glDeleteProgram(m_programID);
	}
	
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String variableName)
	// attribute is the index location of the VAO variable name is the 'IN' field of the vertex shader example - "Position" variable
	{
		GL20.glBindAttribLocation(m_programID, attribute, variableName);
	}
	
	private static int loadShader(String file, int type) 
	// this opens the .txt shader source code files copies it to video memory, complies it and then returns a reference id to the shader
	{
		StringBuilder shaderSource = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null)
			{
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch(IOException e)
		{
			System.err.println("Could not read file!");
			e.printStackTrace();
			System.exit(-1);;
		}
		
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}
		
		return shaderID;
	}

}
