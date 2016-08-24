package entities;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import org.joml.Vector3f;

import models.TexturedModel;
import renderEngine.Window;
import terrains.Terrain;

public class Player extends Entity {

	private Window window;

	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private static final float JUMPPOWER = 30;
	
	private static final float TERRAINHEIGHT = 0;
	private boolean inTheAir = false;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upspeed = 0;

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed*window.getDelta(), 0);
		float distance = currentSpeed * window.getDelta();
		float dx = (float) (distance*Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance*Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		
		upspeed += GRAVITY*window.getDelta();
		super.increasePosition(0, upspeed*window.getDelta(), 0);
		
		float terrainHeight = terrain.getTerrainHeight(this.getPosition().x, this.getPosition().z);
		if(super.getPosition().y <= terrainHeight){
			this.upspeed = 0;
			inTheAir = false;
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void jump(){
		if(!inTheAir){
			this.upspeed = JUMPPOWER;
			inTheAir = true;
		}
	}

	private void checkInputs() {
		if (window.isKeyPressed(GLFW_KEY_W)) {
			if(window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)){
				this.currentSpeed = RUN_SPEED*2f;
			}else{
				this.currentSpeed = RUN_SPEED;
			}
		} else if (window.isKeyPressed(GLFW_KEY_S)) {
			if(window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)){
				this.currentSpeed = -RUN_SPEED*2f;
			}else{
				this.currentSpeed = -RUN_SPEED;
			}
		} else {
			this.currentSpeed = 0;
		}
		
		if (window.isKeyPressed(GLFW_KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		} else if (window.isKeyPressed(GLFW_KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		} else {
			this.currentTurnSpeed = 0;
		}
		if (window.isKeyPressed(GLFW_KEY_SPACE)) {
			jump();
		}
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

}
