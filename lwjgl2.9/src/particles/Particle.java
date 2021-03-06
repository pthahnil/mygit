package particles;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
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

	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	private float blend;

	private float distance;

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

	public Vector2f getTexOffset1() {
		return texOffset1;
	}

	public Vector2f getTexOffset2() {
		return texOffset2;
	}

	public float getBlend() {
		return blend;
	}

	public ParticleTexture getTexture() {
		return texture;
	}

	public float getDistance() {
		return distance;
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

	protected boolean update(Camera camera) {
		velocity.y += Player.GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds();
		Vector3f change = new Vector3f(velocity);
		change.scale(DisplayManager.getFrameTimeSeconds());
		Vector3f.add(change, position, position);

		distance = Vector3f.sub(camera.getPosition(), position, null).lengthSquared();

		updateTextureCoordInfo();
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
	}

	private void updateTextureCoordInfo() {
		float lifeFactor = elapsedTime / lifeLength;
		int lifeStages = texture.getNumberOfRows() * texture.getNumberOfRows();
		float atalasProgression = lifeFactor * lifeStages;

		int index1 = (int) Math.floor(atalasProgression);
		int index2 = index1 < lifeStages - 1 ? index1 + 1 : index1;
		this.blend = atalasProgression % 1;

		setTextureOffset(texOffset1, index1);
		setTextureOffset(texOffset2, index2);
	}

	private void setTextureOffset(Vector2f offset, int index) {
		int column = index % texture.getNumberOfRows();
		int row = index / texture.getNumberOfRows();

		offset.x = (float) column / texture.getNumberOfRows();
		offset.y = (float) row / texture.getNumberOfRows();
	}

}
