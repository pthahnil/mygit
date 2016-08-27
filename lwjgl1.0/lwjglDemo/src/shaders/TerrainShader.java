package shaders;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import entities.Light;

public class TerrainShader extends ShaderProgram {

	private static final String VTFILE = "/shaders/terrainVertexShader";
	private static final String FGFILE = "/shaders/terrainFragmentShader";

	public static final String TRANSFORMATION = "transformationMatrix";
	public static final String PROJECTION = "projectionMatrix";
	public static final String VIEW = "viewMatrix";
	public static final String LTPOSITION = "lightPosition";
	public static final String LTCOLOR = "lightColour";
	public static final String SHINEDUMPER = "shineDamper";
	public static final String REFLECTIVITY = "reflectivity";
	public static final String SKYCOLOUR = "skyColour";
	public static final String BACKGROUNDSAMPLE = "bgTexture";
	public static final String RSAMPLE = "rTexture";
	public static final String GSAMPLE = "gTexture";
	public static final String BSAMPLE = "bTexture";
	public static final String BLENDMAPSAMPLE = "blendMap";
	public static final String ATTENUATION = "attenuation";

	public TerrainShader() {
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
			
			for (int i = 0; i < 4; i++) {
				super.createUniform(LTPOSITION+"["+i+"]");
				super.createUniform(LTCOLOR+"["+i+"]");
				super.createUniform(ATTENUATION+"["+i+"]");
			}
			
			super.createUniform(SHINEDUMPER);
			super.createUniform(REFLECTIVITY);
			super.createUniform(SKYCOLOUR);
			super.createUniform(BACKGROUNDSAMPLE);
			super.createUniform(RSAMPLE);
			super.createUniform(GSAMPLE);
			super.createUniform(BSAMPLE);
			super.createUniform(BLENDMAPSAMPLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connectTextureUnits() {
		super.loadInt(BACKGROUNDSAMPLE, 0);
		super.loadInt(RSAMPLE, 1);
		super.loadInt(GSAMPLE, 2);
		super.loadInt(BSAMPLE, 3);
		super.loadInt(BLENDMAPSAMPLE, 4);
	}

	public void loadSkyColor(Vector3f skyColor) {
		super.loadVector(SKYCOLOUR, skyColor);
	}

	public void loadViewMatrix(Matrix4f matrix) {
		super.setUniform(VIEW, matrix);
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.setUniform(TRANSFORMATION, matrix);
	}

	public void loadProjectionformationMatrix(Matrix4f matrix) {
		super.setUniform(PROJECTION, matrix);
	}

	public void loadLights(List<Light> lights){
		for (int i = 0; i < 4; i++) {
			if(i<lights.size()){
				super.loadVector(LTPOSITION+"["+i+"]", lights.get(i).getPosition());
				super.loadVector(LTCOLOR+"["+i+"]", lights.get(i).getColour());
				super.loadVector(ATTENUATION+"["+i+"]", lights.get(i).getAttenuation());
			}else{
				super.loadVector(LTPOSITION+"["+i+"]", new Vector3f(0,0,0));
				super.loadVector(LTCOLOR+"["+i+"]",  new Vector3f(0,0,0));
				super.loadVector(ATTENUATION+"["+i+"]",  new Vector3f(1,0,0));
			}
		}
	}

	public void loadShineAttrs(float shineDumper, float reflectivity) {
		super.loadFloat(SHINEDUMPER, shineDumper);
		super.loadFloat(REFLECTIVITY, reflectivity);
	}
}
