package com.modernjoys.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera 
{
	private Vector3f m_position = new Vector3f(0, 0, 0);
	
	// how high a camera is aimed - facing the sky is 90 degrees
	private float m_pitch;
	
	// how much turned left or right the camera is aiming - backwards is 180 degress
	private float m_yaw;
	
	// how much the camera is twisted - upside down is 180 degress
	private float m_roll;
	
	public Camera() 
	{
		
	}
	
	public void move()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			m_position.z -=0.1f;			
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			m_position.z +=0.1f;			
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			m_position.x +=0.1f;			
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			m_position.x -=0.1f;			
		}
		
	}

	public Vector3f getPosition() {
		return m_position;
	}

	public float getPitch() {
		return m_pitch;
	}

	public float getYaw() {
		return m_yaw;
	}

	public float getRoll() {
		return m_roll;
	}
	
	

}
