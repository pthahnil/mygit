package textures;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import java.nio.ByteBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

	private final int id;

	private float shineDumper = 1.0f;
	private float reflectivity = 0.0f;

	private boolean isTransParent = false;
	private boolean useFackLightLing = false;

	public Texture(String fileName) throws Exception {
		this(loadTexture(fileName));
	}

	public Texture(int id) {
		this.id = id;
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public boolean isTransParent() {
		return isTransParent;
	}

	public void setTransParent(boolean isTransParent) {
		this.isTransParent = isTransParent;
	}

	public boolean isUseFackLightLing() {
		return useFackLightLing;
	}

	public void setUseFackLightLing(boolean useFackLightLing) {
		this.useFackLightLing = useFackLightLing;
	}

	public int getId() {
		return id;
	}

	private static int loadTexture(String fileName) throws Exception {
		// Load Texture file
		PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream(fileName));

		// Load texture contents into a byte buffer
		ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
		decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
		buf.flip();

		// Create a new OpenGL texture
		int textureId = glGenTextures();
		// Bind the texture
		glBindTexture(GL_TEXTURE_2D, textureId);

		// Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte
		// size
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		// glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		// glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		// Upload the texture data
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,
				buf);
		// Generate Mip Map
		glGenerateMipmap(GL_TEXTURE_2D);
		return textureId;
	}

	public void cleanup() {
		glDeleteTextures(id);
	}

	public float getShineDumper() {
		return shineDumper;
	}

	public void setShineDumper(float shineDumper) {
		this.shineDumper = shineDumper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

}
