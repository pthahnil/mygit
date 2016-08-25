package guis;

import org.joml.Matrix4f;

import shaders.ShaderProgram;

public class GuiShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "/guis/guiVertexShader.txt";
	private static final String FRAGMENT_FILE = "/guis/guiFragmentShader.txt";
	
	private static final String TRANSFORMATION = "transformationMatrix";
	
	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttrs() {
		super.bindAttr(0, "position");
	}

	@Override
	protected void getAllUniforms() {
		try {
			super.createUniform(TRANSFORMATION);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(TRANSFORMATION, matrix);
	}
}
