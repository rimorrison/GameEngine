package com.modernjoys.models;

import com.modernjoys.textures.ModelTexture;

public class TexturedModel 
// this is model data with Texture information combined
{

	private RawModel m_rawModel;
	private ModelTexture m_texture;
	
	public TexturedModel(RawModel model, ModelTexture texture) 
	{
		m_rawModel = model;
		m_texture = texture;
	}

	public RawModel getRawModel() {
		return m_rawModel;
	}

	public ModelTexture getTexture() {
		return m_texture;
	}
	
	
}
