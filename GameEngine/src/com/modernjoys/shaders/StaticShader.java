package com.modernjoys.shaders;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/com/modernjoys/shaders/vertexShader.txt";
	private static final String FRAGEMENT_FILE = "src/com/modernjoys/shaders/fragmentShader.txt";
	
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGEMENT_FILE);
	}

	@Override
	protected void bindAttributes() 
	// the 1st field is the VAO's first index while the 2nd attribute is the variable in the vertex shader
	{
		super.bindAttribute(0,  "position");
	}

}
