package terrains;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.Vector3f;

import models.RawModel;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.Loader;

public class Terrain {

	private static final float SIZE = 800;
//	private static final int VERTEX_COUNT = 128;
	private float MAX_HEIGHT = 40;
	private float MAX_PIXEL_COLOR = 256 * 256 * 256;

	// private TexturedModel model;
	// private Vector3f position;
	private RawModel model;
	private Vector3f position;

	private TerrainTexturePack pack;
	private TerrainTexture blendMap;

	public Terrain(Vector3f position, Loader loader, TerrainTexturePack pack, TerrainTexture blendMap, String heihtMap) {
		// this.model = new TexturedModel(generateTerrain(loader), texture);
		this.pack = pack;
		this.blendMap = blendMap;
		this.model = generateTerrain(loader,heihtMap);
		this.position = new Vector3f(position.x * SIZE, 0, position.z * SIZE);
	}

	public Vector3f getPosition() {
		return this.position;
	}

	public TerrainTexturePack getPack() {
		return pack;
	}

	public TerrainTexture getBlendMap() {
		return blendMap;
	}

	public RawModel getModel() {
		return model;
	}

	private RawModel generateTerrain(Loader loader,String path) {
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int VERTEX_COUNT = image.getHeight();
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT * 1)];
		int vertexPointer = 0;
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				vertices[vertexPointer * 3] = -(float) j / ((float) VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer * 3 + 1] = getHeight(i, j, image);
				vertices[vertexPointer * 3 + 2] = -(float) i / ((float) VERTEX_COUNT - 1) * SIZE;
				
				Vector3f normal = calNormals(i, j, image);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				
				textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
	
	private float getHeight(int x,int z,BufferedImage image){
		
		if(x<=0 || x >=image.getHeight() || z<=0 || z>= image.getHeight()){
			return 0;
		}
		
		float height = image.getRGB(x, z);
		height += MAX_PIXEL_COLOR/2;
		height /= MAX_PIXEL_COLOR/2;
		height *= MAX_HEIGHT;
		
		return height;
	}
	
	private Vector3f calNormals(int x,int z,BufferedImage image){
		float heightL= getHeight(x+1, z, image);
		float heightR= getHeight(x-1, z, image);
		float heightD= getHeight(x, z+1, image);
		float heightU= getHeight(x, z-1, image);
		
		Vector3f normal = new Vector3f(heightL - heightR,2f,heightD - heightU);
		normal.normalize();
		
		return normal;
	}
	
}
