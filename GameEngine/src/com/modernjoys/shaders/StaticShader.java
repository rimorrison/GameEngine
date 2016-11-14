package com.modernjoys.shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/com/modernjoys/shaders/vertexShader.txt";
	private static final String FRAGEMENT_FILE = "src/com/modernjoys/shaders/fragmentShader.txt";
	
	// this is to store the Uniformation locations that we lookup in getAllUniformLocations() below
	private int m_location_transformationMatrix;
	
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGEMENT_FILE);
	}

	@Override
	protected void bindAttributes() 
	// the 1st field is the VAO's first index while the 2nd attribute is the variable in the vertex shader
	{
		super.bindAttribute(0,  "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		m_location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		
	}
	
	// this method loads the transformation matrix to the uniform variable
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(m_location_transformationMatrix, matrix);
	}

}
