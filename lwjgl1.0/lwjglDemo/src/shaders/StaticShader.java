package shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import entities.Light;

public class StaticShader extends ShaderProgram {

	private static final String VTFILE = "/shaders/vertexShader"; 
	private static final String FGFILE = "/shaders/fragmentShader";
	
	public static final String TRANSFORMATION = "transformationMatrix";
	public static final String PROJECTION = "projectionMatrix";
	public static final String VIEW = "viewMatrix";
	public static final String LTPOSITION = "lightPosition";
	public static final String LTCOLOR = "lightColour";
	public static final String SHINEDUMPER = "shineDamper";
	public static final String REFLECTIVITY = "reflectivity";
	public static final String FACKLIGHTLING = "useFackLightLing";
	public static final String SKYCOLOUR = "skyColour";
	
	public StaticShader() {
		super(VTFILE, FGFILE);
	}

	@Override
	protected void bindAttrs() {
		super.bindAttr(0, "position");
		super.bindAttr(1, "textureCoordinates");
		super.bindAttr(2, "normal");
	}

	@Override
	protected void getAllUniforms() {
		try {
			super.createUniform(TRANSFORMATION);
			super.createUniform(PROJECTION);
			super.createUniform(VIEW);
			super.createUniform(LTPOSITION);
			super.createUniform(LTCOLOR);
			super.createUniform(SHINEDUMPER);
			super.createUniform(REFLECTIVITY);
			super.createUniform(FACKLIGHTLING);
			super.createUniform(SKYCOLOUR);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadSkyColor(Vector3f skyColor){
		super.loadVector(SKYCOLOUR, skyColor);
	}
	
	public void loadViewMatrix(Matrix4f matrix){
		super.setUniform(VIEW, matrix);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.setUniform(TRANSFORMATION, matrix);
	}
	
	public void loadProjectionformationMatrix(Matrix4f matrix){
		super.setUniform(PROJECTION, matrix);
	}
	
	public void loadLight(Light light){
		super.loadVector(LTPOSITION, light.getPosition());
		super.loadVector(LTCOLOR, light.getColour());
	}
	
	public void loadShineAttrs(float shineDumper,float reflectivity){
		super.loadFloat(SHINEDUMPER, shineDumper);
		super.loadFloat(REFLECTIVITY, reflectivity);
	}
	
	public void loadFackLightLing(boolean fack){
		super.loadBoolean(FACKLIGHTLING, fack);
	}
}
