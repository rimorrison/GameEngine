package com.modernjoys.entities;

import org.lwjgl.util.vector.Vector3f;

import com.modernjoys.models.TexturedModel;

public class Entity 
{
	
	private TexturedModel m_model;
	private Vector3f m_position;
	private float m_rotX, m_rotY, m_rotZ;
	private float m_scale;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		m_model = model;
		m_position = position;
		m_rotX = rotX;
		m_rotY = rotY;
		m_rotZ = rotZ;
		m_scale = scale;
	}
	
	
	public void increasePosition(float dx, float dy, float dz) 
	{
		m_position.x += dx;
		m_position.y += dy;
		m_position.z += dz;
		
	}
	
	public void increaseRotation(float dx, float dy, float dz)
	{
		m_rotX += dx;
		m_rotY += dy;
		m_rotZ += dz;
	}
	
	
	
	

	public TexturedModel getModel() {
		return m_model;
	}

	public void setModel(TexturedModel model) {
		m_model = model;
	}

	public Vector3f getPosition() {
		return m_position;
	}

	public void setPosition(Vector3f position) {
		m_position = position;
	}

	public float getRotX() {
		return m_rotX;
	}

	public void setRotX(float rotX) {
		m_rotX = rotX;
	}

	public float getRotY() {
		return m_rotY;
	}

	public void setRotY(float rotY) {
		m_rotY = rotY;
	}

	public float getRotZ() {
		return m_rotZ;
	}

	public void setRotZ(float rotZ) {
		m_rotZ = rotZ;
	}

	public float getScale() {
		return m_scale;
	}

	public void setScale(float scale) {
		m_scale = scale;
	}
	
	
	

}
