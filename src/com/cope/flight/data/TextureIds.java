package com.cope.flight.data;

import com.badlogic.gdx.graphics.Texture;

public class TextureIds {
	
	public static final int plane = 1;
	public static final int background = 2;
	public static final int building = 3;
	public static final int pipeEnd = 4;
	public static final int pipeTube = 5;
	
	private static Texture planeTexture;
	private static Texture backgroundTexture;
	private static Texture buildingTexture;
	private static Texture pipeEndTexture;
	private static Texture pipeTubeTexture;
	
	public TextureIds(){
		planeTexture = new Texture("paperAeroplane.png");
		backgroundTexture = new Texture("background.png");
		buildingTexture = new Texture("building.png");
		pipeEndTexture = new Texture("pipeEnd.png");
		pipeTubeTexture = new Texture("pipeTube.png");
	}
	
	public static Texture getTexture(int id){
		switch (id){
		
		case plane:
			return planeTexture;
		case background:
			return backgroundTexture;
		case building:
			return buildingTexture;
		case pipeEnd:
			return pipeEndTexture;
		case pipeTube:
			return pipeTubeTexture;
			
		default: return null;
		}
	}
}
