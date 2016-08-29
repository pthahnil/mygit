package skybox;

import org.joml.Matrix4f;

import entities.Camera;
import shaders.ShaderProgram;
import toolbox.Maths;

public class SkyboxShader extends ShaderProgram{

	private static final String VERTEX_FILE = "/skybox/skyboxVertexShader.txt";
	private static final String FRAGMENT_FILE = "/skybox/skyboxFragmentShader.txt";
	
	private final String PROJECTIONMATRIX = "projectionMatrix";
	private final String VIEWMATRIX = "viewMatrix";
	
	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.setUniform(PROJECTIONMATRIX, matrix);
	}

	public void loadViewMatrix(Camera camera){
		Matrix4f matrix = Maths.createViewMatrix(camera);
		super.setUniform(VIEWMATRIX, matrix);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
