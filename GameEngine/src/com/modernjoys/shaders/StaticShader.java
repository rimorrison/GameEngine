package com.modernjoys.shaders;

import org.lwjgl.util.vector.Matrix4f;

import com.modernjoys.entities.Camera;
import com.modernjoys.toolbox.Maths;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/com/modernjoys/shaders/vertexShader.txt";
	private static final String FRAGEMENT_FILE = "src/com/modernjoys/shaders/fragmentShader.txt";
	
	// this is to store the Uniformation locations that we lookup in getAllUniformLocations() below
	private int m_location_transformationMatrix;
	private int m_location_projectionMatrix;
	private int m_location_viewMatrix;
	
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGEMENT_FILE);
	}

	@Override
	protected void bindAttributes() 
	// the 1st field is the VAO's first index while the 2nd attribute is the variable in the vertex shader
	{
		super.bindAttribute(0,  "position");
		super.bindAttribute(1, "textureCoordinates");
	}

	@Override
	protected void getAllUniformLocations() {
		// this goes out and finds and stores the ID of Uniform variables from the shader.txt files
		m_location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		m_location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		m_location_viewMatrix = super.getUniformLocation("viewMatrix");
		
	}
	
	// this method loads the transformation matrix to the uniform variable
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(m_location_transformationMatrix, matrix);
	}
	
	// this method loads the viewMatrix into the uniform varilable
		public void loadViewMatrix(Camera camera)
		{
			Matrix4f viewMatrix = Maths.createViewMatrix(camera);
			super.loadMatrix(m_location_viewMatrix, viewMatrix);
		}
	
	// this method loads the projection Matrix info to the uniform variable
	// the render class will setup the projection matrix
	public void loadProjectionMatrix(Matrix4f projection)
	{
		super.loadMatrix(m_location_projectionMatrix, projection);
	}
	
	

}
