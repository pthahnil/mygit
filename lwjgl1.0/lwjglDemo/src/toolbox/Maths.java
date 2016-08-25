package toolbox;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import entities.Camera;
import entities.Entity;

public class Maths {
    
    public static Matrix4f createWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
		Matrix4f worldMatrix = new Matrix4f();
		worldMatrix.identity().translate(offset).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).
                scale(scale);
        return worldMatrix;
    }
    
    public final Matrix4f createOrthoProjectionMatrix(float left, float right, float bottom, float top) {
    	Matrix4f orthoMatrix = new Matrix4f();
    	orthoMatrix.identity();
        orthoMatrix.setOrtho2D(left, right, bottom, top);
        return orthoMatrix;
    }
    
    public final Matrix4f createModelViewMatrix(Entity entity, Matrix4f viewMatrix) {
    	return null;
    }
    
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		Vector3f cameraPos = camera.getPosition();
        Vector3f rotation = camera.getRotation();
        
        viewMatrix.identity();
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
	}
	
	public static float getLocation(Vector3f p1,Vector3f p2,Vector3f p3,Vector2f pos){
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = (p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z) / det;
		float l2 = (p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z) / det;
		float l3 = 1- l1 - l2;
		
		return l1*p1.y + l2*p2.y + l3*p3.y;
	}
}
