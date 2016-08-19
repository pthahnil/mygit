package shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import toolbox.Utils;


public abstract class ShaderProgram {

	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;
	
	FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16); 
	private final Map<String, Integer> uniforms;
	
	public ShaderProgram(String vtFile,String fgFile) {
		uniforms = new HashMap<>();
		try {
			vertexShaderId = loadShader(vtFile, GL20.GL_VERTEX_SHADER);
			fragmentShaderId = loadShader(fgFile, GL20.GL_FRAGMENT_SHADER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		programId = GL20.glCreateProgram();
		GL20.glAttachShader(programId, vertexShaderId);
		GL20.glAttachShader(programId, fragmentShaderId);
		
		bindAttrs();
		
		GL20.glLinkProgram(programId);
		GL20.glValidateProgram(programId);
		
		getAllUniforms();
	}
	
	protected abstract void bindAttrs();
	
	protected void bindAttr(int attribute,String variableName){
		GL20.glBindAttribLocation(programId, attribute, variableName);
	}
	//--------------------------------------------------------------------------
	protected abstract void getAllUniforms();
	
	protected void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            throw new Exception ("Could not find uniform:" + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }
    
    public void setUniform(String uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        value.get(fb);
        glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
    }
    
	//--------------------------------------------------------------------------
	 public void start() {
        glUseProgram(programId);
	 }

    public void stop() {
        glUseProgram(0);
    }

    public void cleanup() {
        stop();
        if (programId != 0) {
            if (vertexShaderId != 0) {
                GL20.glDetachShader(programId, vertexShaderId);
                GL20.glDeleteShader(vertexShaderId);
            }
            if (fragmentShaderId != 0) {
            	GL20.glDetachShader(programId, fragmentShaderId);
            	GL20.glDeleteShader(fragmentShaderId);
            }
            GL20.glDeleteProgram(programId);
        }
    }
	    
	private static int loadShader(String path, int shaderType) throws Exception{
		
		int shaderId = GL20.glCreateShader(shaderType);
		if(shaderId<0){
			throw new Exception("unable to create shader");
		}
		
		String sourceCode = Utils.loadResource(path);
		
		GL20.glShaderSource(shaderId, sourceCode);
		GL20.glCompileShader(shaderId);
		
		if(GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE){
			throw new Exception("compile shader error ");
		}
		
		return shaderId;
	}
	//---------------------上传数据----------------------------
    protected void loadFloat(String loname,float f){
    	Integer location = uniforms.get(loname);
    	GL20.glUniform1f(location, f);
    }
    
    protected void loadInt(String loname,int i){
    	Integer location = uniforms.get(loname);
    	GL20.glUniform1i(location, i);
    }
    
    protected void loadVector(String loname, Vector3f vect){
    	Integer location = uniforms.get(loname);
    	GL20.glUniform3f(location, vect.x, vect.y, vect.z);
    }
    
    protected void loadBoolean(int location,boolean b){
    	float f = 0;
    	if(b){
    		f = 1f;
    	}
    	GL20.glUniform1f(location, f);
    }
    
    protected void loadBoolean(String loname,boolean b){
    	float f = 0;
    	if(b){
    		f = 1f;
    	}
    	Integer location = uniforms.get(loname);
    	GL20.glUniform1f(location, f);
    }
    
    protected void loadMatrix(String loname,Matrix4f value){
    	value.get(matrixBuffer);
    	matrixBuffer.flip();
    	Integer location = uniforms.get(loname);
    	GL20.glUniformMatrix4fv(location, false, matrixBuffer);
    }
}
