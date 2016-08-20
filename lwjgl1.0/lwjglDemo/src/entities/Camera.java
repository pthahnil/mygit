package entities;

import org.joml.Vector3f;

import renderEngine.MouseInput;
import renderEngine.Window;

public class Camera {

	private Vector3f position ;
	private Vector3f rotation = new Vector3f(20,0,0);//pitch = x,yall = y,row = z;

	private static final float MOUSE_SENSITIVITY = 0.2f;

	private float distance2Player = 50;
	private float vAngle = 0;

	private Player player;
	private Window window;
	private MouseInput mouse;

	public Camera(Player player, Window window) {
		this.player = player;
		this.window = window;
		player.setWindow(window);
		mouse = new MouseInput();
		mouse.init(window);
		calculateCameraPosition();
	}

	public void move() {
		player.move();
		calculateCameraPosition();
	}
	
	public void calculateCameraPosition(){
		
		float yOff = (float) (distance2Player * Math.sin(Math.toRadians(this.rotation.x)));
		float vDistance = (float) (distance2Player * Math.cos(Math.toRadians(this.rotation.x)));
		float xOff = (float) (vDistance * Math.sin(Math.toRadians(player.getRotY()))) ;
		float zOff = (float) (vDistance * Math.cos(Math.toRadians(player.getRotY()))) ;
		
		this.position = new Vector3f(player.getPosition().x+xOff,player.getPosition().y+yOff,player.getPosition().z+zOff);
		this.rotation = new Vector3f(rotation.x,-player.getRotY(),-player.getRotZ());
	}

	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
}
