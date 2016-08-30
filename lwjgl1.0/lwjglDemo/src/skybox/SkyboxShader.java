package skybox;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import entities.Camera;
import shaders.ShaderProgram;
import toolbox.Maths;

public class SkyboxShader extends ShaderProgram{

	private static final String VERTEX_FILE = "/skybox/skyboxVertexShader.txt";
	private static final String FRAGMENT_FILE = "/skybox/skyboxFragmentShader.txt";
	
	private static final float ROTATE_SPEED = 1f;
	private float rotation = 0f;
	
	private final String PROJECTIONMATRIX = "projectionMatrix";
	private final String VIEWMATRIX = "viewMatrix";
	private final String FOGCOLOUR = "fogColour";
	private final String CUBEMAP = "cubeMap";
	private final String CUBEMAP2 = "cubeMap2";
	private final String BLENDFACTOR = "blendFactor";

	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttrs() {
		super.bindAttr(0, "position");
	}

	@Override
	protected void getAllUniforms() {
		try {
			super.createUniform(PROJECTIONMATRIX);
			super.createUniform(VIEWMATRIX);
			super.createUniform(FOGCOLOUR);
			super.createUniform(CUBEMAP);
			super.createUniform(CUBEMAP2);
			super.createUniform(BLENDFACTOR);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void connectTextureUnits(){
		super.loadInt(CUBEMAP, 0);
		super.loadInt(CUBEMAP2, 1);
	}
	
	public void loadBlendFactor(float factor){
		super.loadFloat(BLENDFACTOR, factor);
	}
	
	public void loadFogColour(Vector3f fogColour){
		super.loadVector(FOGCOLOUR, fogColour);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.setUniform(PROJECTIONMATRIX, matrix);
	}

	public void loadViewMatrix(Camera camera){
		Matrix4f matrix = Maths.createViewMatrix(camera);
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		rotation += ROTATE_SPEED * 0.01f;
		matrix.rotate((float)Math.toRadians(rotation), new Vector3f(0, 1, 0));
		super.setUniform(VIEWMATRIX, matrix);
	}
	
}
