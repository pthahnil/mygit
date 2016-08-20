package toolbox;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import entities.Camera;

public class Transformation {
    
    public static Matrix4f createWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
		Matrix4f worldMatrix = new Matrix4f();
		worldMatrix.identity().translate(offset).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).
                scale(scale);
        return worldMatrix;
    }
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		// First do the rotation so camera rotates over its position
		matrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0))
				.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0));
		// Then do the translation
		matrix.translate(-(camera.getPosition().x), -(camera.getPosition().y), -(camera.getPosition().z));
		return matrix;
	}
}
