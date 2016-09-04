package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		/**************************************************************************************/
		RawModel model = OBJLoader.loadObjModel("tree", loader);
		TexturedModel tree = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		
		RawModel model2 = OBJLoader.loadObjModel("lowPolyTree", loader);
		TexturedModel polyTree = new TexturedModel(model2,new ModelTexture(loader.loadTexture("lowPolyTree")));
		
		RawModel model3 = OBJLoader.loadObjModel("grassModel", loader);
		TexturedModel grass = new TexturedModel(model3,new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		
		RawModel model4 = OBJLoader.loadObjModel("fern", loader);
		TexturedModel fern = new TexturedModel(model4,new ModelTexture(loader.loadTexture("fern")));
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setRows(2);
		/**************************************************************************************/
		TerrainTexture bgtex = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rtex = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gtex = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture btex = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack pack = new TerrainTexturePack(bgtex, rtex, gtex, btex);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain = new Terrain(0,0,loader,pack,blendMap,"heightmap");
		Terrain terrain2 = new Terrain(1,0,loader,pack,blendMap,"heightmap");
		/**************************************************************************************/
		List<GuiTexture> guis = new ArrayList<>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.7f,0.7f), new Vector2f(0.25f,0.25f));
		guis.add(gui);
		
		GuiRenderer grender = new GuiRenderer(loader);
		
		/**************************************************************************************/
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i=0;i<200;i++){
			if(i % 20 ==0){
				float x = random.nextFloat() * 800 - 400;
				float z = random.nextFloat() * -600;
				float y = terrain.getTerrainHeight(x, z);
				entities.add(new Entity(random.nextInt(4),fern, new Vector3f(x,y,z),0,0,0,1f));
				
			}
			if(i % 5 ==0){
				float x = random.nextFloat() * 500 - 300;
				float z = random.nextFloat() * -600;
				float y = terrain.getTerrainHeight(x, z);
				entities.add(new Entity(tree, new Vector3f(x,y,z),0,0,0,5f));
				
				x = random.nextFloat() * 600 - 200;
				z = random.nextFloat() * -600;
				y = terrain.getTerrainHeight(x, z);
				entities.add(new Entity(polyTree, new Vector3f(x,y,z),0,0,0,0.5f));
				
			}
		}
		/**************************************************************************************/
		List<Light>lights = new ArrayList<>();
		lights.add(new Light(new Vector3f(0,10000,-7000),new Vector3f(0.4f,0.4f,0.4f)));
		lights.add(new Light(new Vector3f(185,10,-293),new Vector3f(2,0,0),new Vector3f(1.0f,0.01f,0.002f)));
		lights.add(new Light(new Vector3f(370,17,-300),new Vector3f(0,2,2),new Vector3f(1.0f,0.01f,0.002f)));
		lights.add(new Light(new Vector3f(293,7,-305),new Vector3f(2,2,0),new Vector3f(1.0f,0.01f,0.002f)));
		
		
		/**************************************************************************************/
		TexturedModel pm = new TexturedModel(OBJLoader.loadObjModel("person", loader), 
				new ModelTexture(loader.loadTexture("playerTexture")));
		Player player = new Player(pm, new Vector3f(400,0,-400), 0, 0, 0, 0.5f);
		
		/**************************************************************************************/
		RawModel lampmod = OBJLoader.loadObjModel("lamp", loader);
		TexturedModel lamp = new TexturedModel(lampmod,new ModelTexture(loader.loadTexture("lamp")));
		
		entities.add(new Entity(lamp, new Vector3f(185,0f,-293), 0, 0, 0, 1)) ;
		entities.add(new Entity(lamp, new Vector3f(370,0f,-300), 0, 0, 0, 1)) ;
		entities.add(new Entity(lamp, new Vector3f(293,0f,-305), 0, 0, 0, 1)) ;
		
		/**************************************************************************************/
		Camera camera = new Camera(player);
		MasterRenderer renderer = new MasterRenderer(loader);
		/**************************************************************************************/
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMarix(), terrain);
		/**************************************************************************************/
		while(!Display.isCloseRequested()){
			camera.move();
			player.move(terrain);
			
			picker.update();
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			
			renderer.processEntity(player);
			
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			renderer.render(lights, camera);
			grender.render(guis);
			DisplayManager.updateDisplay();
		}
		
		grender.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
