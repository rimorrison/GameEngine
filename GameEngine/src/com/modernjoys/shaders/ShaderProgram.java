package com.modernjoys.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram {
	
	private int m_programID;
	private int m_vertexShaderID;
	private int m_fragmentShaderID;
	
	// we need a float buffer to load matrices - since we are using a 4x4 matrices we need 16 floats (16 remember is array.length)
	private static FloatBuffer m_matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public ShaderProgram(String vertexFile, String fragmentFile)
	{
		m_vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		m_fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		m_programID = GL20.glCreateProgram();
		GL20.glAttachShader(m_programID,  m_vertexShaderID);
		GL20.glAttachShader(m_programID, m_fragmentShaderID);
		
		// connects a VAO to the shader program variable
		bindAttributes();
		
		GL20.glLinkProgram(m_programID);
		GL20.glValidateProgram(m_programID);
		// right when we load up the game we need to get the location of all the uniform variables in the shader code and then store the locations
		// then we need variables we can call at any time to allow us to modify the uniform variables in the shader.txt file from our Java program on the fly
		getAllUniformLocations();
	}
	

	
	
	protected abstract void getAllUniformLocations();
	
	// setting up Uniform variables so we can manipulate our shaders on the fly
	// we capture the 'uniformName"  of the uniform variable in the sharder.txt file
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(m_programID, uniformName);
		
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
			System.exit(-1);
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
	
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String variableName)
	// attribute is the index location of the VAO variable name is the 'IN' field of the vertex shader example - "Position" variable
	{
		GL20.glBindAttribLocation(m_programID, attribute, variableName);
	}
	
	
	// methods to load values to a uniform Location variable in the shader.txt file
	// takes location of uniform location variable and the value we want to load to the uniform
	protected void loadFloat(int location, float value) 
	{
		GL20.glUniform1f(location, value);
	}
	
	// this loads a vector into a uniform variable in the shader.txt file
	protected void loadVector(int location, Vector3f vector) 
	{
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	// this loads a boolean into a uniform variable in the shader.txt file - note that shader does not really have a boolean so we are passing 1 = 'true' & 0 = 'false'
	protected void loadBoolean(int location, boolean value) 
	{
		float toLoad = 0;
		if(value) {
			toLoad = 1;
		}
		GL20.glUniform1f(location, toLoad);
	}
	
	// this loads a matrix into a uniform variable in the shader.txt
	protected void loadMatrix(int location, Matrix4f matrix) 
	{
		matrix.store(m_matrixBuffer);
		m_matrixBuffer.flip();
		// location in the shader.txt whether it needs to be Transposed or not, values (i.e. matrixBuffer data);
		GL20.glUniformMatrix4(location, false, m_matrixBuffer);
		
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

}
