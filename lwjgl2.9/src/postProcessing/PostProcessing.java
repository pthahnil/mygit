package postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import gaussianBlur.HorizontalBlur;
import gaussianBlur.VerticalBlur;
import models.RawModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;

public class PostProcessing {
	
	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };	
	private static RawModel quad;
	
	private static ContrastChanger changer;
	private static HorizontalBlur hBlur;
	private static VerticalBlur vBlur;
	private static HorizontalBlur hBlur2;
	private static VerticalBlur vBlur2;
	
	public static void init(Loader loader){
		quad = loader.loadToVAO(POSITIONS, 2);
		changer = new ContrastChanger();
		hBlur = new HorizontalBlur(DisplayManager.getWidth()/8, DisplayManager.getHeight()/8);
		vBlur = new VerticalBlur(DisplayManager.getWidth()/8, DisplayManager.getHeight()/8);
		hBlur2 = new HorizontalBlur(DisplayManager.getWidth()/2, DisplayManager.getHeight()/2);
		vBlur2 = new VerticalBlur(DisplayManager.getWidth()/2, DisplayManager.getHeight()/2);
	}
	
	public static void doPostProcessing(int colourTexture){
		start();
//		hBlur2.render(colourTexture);
//		vBlur2.render(hBlur2.getOutputTexture());
//		hBlur.render(vBlur2.getOutputTexture());
//		vBlur.render(hBlur.getOutputTexture());
		changer.render(colourTexture);
		end();
	}
	
	public static void cleanUp(){
		changer.cleanUp();
		hBlur.cleanUp();
		vBlur.cleanUp();
		hBlur2.cleanUp();
		vBlur2.cleanUp();
	}
	
	private static void start(){
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	private static void end(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}


}
