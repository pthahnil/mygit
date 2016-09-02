package textures;

public class ModelTexture {

	private int textureID;

	private float shineDamper = 1;
	private float reflectivity = 0;

	private boolean hasTransparency = false;
	private boolean useFakeLighting = false;
	
	private int rows = 1;

	public ModelTexture(int texture) {
		this.textureID = texture;
	}

	public boolean hasTransparency() {
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}

	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}

	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}

	public int getID() {
		return textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
