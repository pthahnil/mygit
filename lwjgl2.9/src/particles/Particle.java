package particles;

import org.lwjgl.util.vector.Vector3f;

import entities.Player;
import renderEngine.DisplayManager;

public class Particle {

	private Vector3f position;
	private Vector3f velocity;

	private float gravityEffect;
	private float lifeLength;
	private float rolation;
	private float scale;

	private float elapsedTime = 0;

	private ParticleTexture texture;

	public Particle(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect,
			float lifeLength, float rolation, float scale) {
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rolation = rolation;
		this.scale = scale;
		this.texture = texture;
		ParticleMaster.addParticle(this);
	}

	public ParticleTexture getTexture() {
		return texture;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRolation() {
		return rolation;
	}

	public float getScale() {
		return scale;
	}

	protected boolean update() {
		velocity.y += Player.GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds();
		Vector3f change = new Vector3f(velocity);
		change.scale(DisplayManager.getFrameTimeSeconds());
		Vector3f.add(change, position, position);
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
	}
}
