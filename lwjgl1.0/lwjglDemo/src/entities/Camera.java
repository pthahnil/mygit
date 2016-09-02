package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0, 5, 0);
	private float pitch = 10;
	private float yaw;
	private float roll;
	
	private Player player;

	public Camera(Player player) {
		this.player = player;
	}

	public void move() {
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		
		float hDistance = calculateHdistance();
		float vDistance = calculateVdistance();
		
		calculateCameraPosition(vDistance, hDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}
	
	public void calculateCameraPosition(float v, float h){
		float theta = angleAroundPlayer + player.getRotY();
		
		float offsetX = (float) (h * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (h * Math.cos(Math.toRadians(theta)));
		
		this.position.x = player.getPosition().x - offsetX;
		this.position.y = player.getPosition().y + v;
		this.position.z = player.getPosition().z - offsetZ;
	}
	
	private float calculateHdistance(){
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVdistance(){
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom(){
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch(){
		if(Mouse.isButtonDown(1)){
			float pitchcg = Mouse.getDY() * 0.1f;
			pitch -=pitchcg;
		}
	}
	
	private void calculateAngleAroundPlayer(){
		if(Mouse.isButtonDown(0)){
			float anglecg = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= anglecg;
		}
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

}
