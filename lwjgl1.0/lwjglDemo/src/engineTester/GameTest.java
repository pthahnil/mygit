package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.Window;
import terrains.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import textures.Texture;

public class GameTest {

	public static void main(String[] args) {

		Window window = new Window("aa", 1280, 720, true);
		Loader loader = new Loader();
		
		//***********************************************//
		TerrainTexture bgTexture = new TerrainTexture("/res/grassy.png");
		TerrainTexture rTexture = new TerrainTexture("/res/dirt.png");
		TerrainTexture gTexture = new TerrainTexture("/res/pinkFlowers.png");
		TerrainTexture bTexture = new TerrainTexture("/res/path.png");
		
		TerrainTexturePack pack = new TerrainTexturePack(bgTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture("/res/blendMap.png");
		//***********************************************//
		MasterRenderer msrender = new MasterRenderer(window);
		
		OBJLoader objLoader = new OBJLoader();
		
		TexturedModel txmodel = null;
		TexturedModel txmodel2 = null;
		TexturedModel txmodel3 = null;
		TexturedModel txmodel4 = null;
		Entity entity = null;
		
		List<Entity> entities = new ArrayList<>();
		Random rand = new Random();
		
		List<Terrain> terrains = new ArrayList<>();
		Terrain terrain = null;
		try {
			
			txmodel = new TexturedModel(objLoader.loadFile("/res/tree.obj"), new Texture("/res/tree.png"));
			txmodel2 = new TexturedModel(objLoader.loadFile("/res/fern.obj"), new Texture("/res/fern.png"));
			txmodel3 = new TexturedModel(objLoader.loadFile("/res/grassModel.obj"), new Texture("/res/grassTexture.png"));
			txmodel4 = new TexturedModel(objLoader.loadFile("/res/lowPolyTree.obj"), new Texture("/res/lowPolyTree.png"));
			for (int i = 0; i < 100; i++) {
				entity = new Entity(txmodel, new Vector3f(rand.nextFloat()*1000-600,0,rand.nextFloat()*1000-600), 0,0,0, 4);
				entities.add(entity);
				
				entity = new Entity(txmodel2, new Vector3f(rand.nextFloat()*1000-300,0,rand.nextFloat()*1000-400), 0,0,0, 1);
				entity.getModel().getTexture().setTransParent(true);
				entities.add(entity);
				
				entity = new Entity(txmodel3, new Vector3f(rand.nextFloat()*1000-400,0,rand.nextFloat()*1000-500), 0,0,0, 1);
				entity.getModel().getTexture().setTransParent(true);
				entity.getModel().getTexture().setUseFackLightLing(true);
				entities.add(entity);
				
				entity = new Entity(txmodel4, new Vector3f(rand.nextFloat()*1000-300,0,rand.nextFloat()*1000-400), 0,0,0, 1);
				entity.getModel().getTexture().setTransParent(true);
				entities.add(entity);
				
			}
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					terrain = new Terrain(new Vector3f(i, 0,j), loader, pack, blendMap);
					terrains.add(terrain);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//***********************************************//
		TexturedModel bunny = null;
		try {
			bunny = new TexturedModel(objLoader.loadFile("/res/person.obj"), new Texture("/res/playerTexture.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Player player = new Player(bunny, new Vector3f(0,0,-10), 0, 1, 0, 0.4f);
		entities.add(player);
		//***********************************************//
		
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		Camera camera = new Camera(player,window);
		
		while(!window.windowShouldClose()){
			
			if ( window.isResized() ) {
	            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
	            window.setResized(false);
	        }
			
			for (Terrain terrain2 : terrains) {
				msrender.processTerrain(terrain2);
			}
			for (Entity entity2 : entities) {
				msrender.processEntity(entity2);
			}
			camera.move();
			
			msrender.render(light, camera);
			window.update();
		}
		msrender.cleanUp();
		loader.cleanUp();
	}

}
