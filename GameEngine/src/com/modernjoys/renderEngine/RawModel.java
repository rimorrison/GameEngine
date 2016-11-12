package com.modernjoys.renderEngine;

public class RawModel {
	
	private int m_vaoID;
	private int m_vertexCount;
	
	public RawModel (int vaoID, int vertexCount) 
	{
		m_vaoID = vaoID;
		m_vertexCount = vertexCount;
	}

	public int getVaoID() {
		return m_vaoID;
	}

	public int getVertexCount() {
		return m_vertexCount;
	}
	
	

}
