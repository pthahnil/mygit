package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity {

	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private static final float JUMPPOWER = 30;
	
	private boolean inTheAir = false;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upspeed = 0;

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getDelta(), 0);
		float distance = currentSpeed * DisplayManager.getDelta();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		
		upspeed += GRAVITY*DisplayManager.getDelta();
		super.increasePosition(0, upspeed*DisplayManager.getDelta(), 0);
		
		float terrainHeight = terrain.getTerrainHeight(this.getPosition().x, this.getPosition().z);
//		float terrainHeight = 0;
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
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
				this.currentSpeed = RUN_SPEED*2;
			}else{
				this.currentSpeed = RUN_SPEED;
			}
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
				this.currentSpeed = -RUN_SPEED*2;
			}else{
				this.currentSpeed = -RUN_SPEED;
			}
		} else {
			this.currentSpeed = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		} else {
			this.currentTurnSpeed = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
	}

}
