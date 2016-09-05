package particles;

public class ParticleTexture {

	private int textureID;
	private int numberOfRows;

	private boolean additive = true;// ����������Ч����GL_ONE_MINUS_SRC_ALPHA ���� GL_ONE

	public ParticleTexture(int textureID, int numberOfRows) {
		this.textureID = textureID;
		this.numberOfRows = numberOfRows;
	}

	public int getTextureID() {
		return textureID;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public boolean isAdditive() {
		return additive;
	}

	public void setAdditive(boolean additive) {
		this.additive = additive;
	}

}
