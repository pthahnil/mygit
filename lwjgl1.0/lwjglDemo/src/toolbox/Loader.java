package toolbox;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import models.RawModel;
import textures.TextureData;

public class Loader {

	private List<Integer> vaos = new ArrayList<>();
	private List<Integer> vbos = new ArrayList<>();
	private List<Integer> textures = new ArrayList<>();

	private int createVAO() {
		int vaoId = glGenVertexArrays();
		vaos.add(vaoId);
		GL30.glBindVertexArray(vaoId);
		return vaoId;
	}

	public RawModel storeDataToVAO(float[] data, float[] textureCoords, int[] indices) {
		int vaoId = createVAO();

		storeDataToAttributeList(0, 3, data);
		storeDataToAttributeList(1, 2, textureCoords);

		bindIndicesBuffer(indices);
		unbindVAO();

		return new RawModel(vaoId, indices.length);
	}

	public RawModel storeDataToVAO(float[] positions,int dimensions) {
		int vaoId = createVAO();

		storeDataToAttributeList(0, dimensions, positions);

		unbindVAO();

		return new RawModel(vaoId, positions.length / dimensions);
	}

	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataToAttributeList(0, 3, positions);
		storeDataToAttributeList(1, 2, textureCoords);
		storeDataToAttributeList(2, 3, normals);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}

	public int loadTexture(String fileName) {
		// Load Texture file
		PNGDecoder decoder = null;
		try {
			decoder = new PNGDecoder(Loader.class.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Load texture contents into a byte buffer
		ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
		try {
			decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
		} catch (IOException e) {
			e.printStackTrace();
		}
		buf.flip();

		// Create a new OpenGL texture
		int textureId = glGenTextures();
		textures.add(textureId);

		// Bind the texture
		glBindTexture(GL_TEXTURE_2D, textureId);

		// Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte
		// size
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		// Upload the texture data
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,
				buf);
		// Generate Mip Map
		GL30.glGenerateMipmap(GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -1);
		return textureId;
	}

	public void storeDataToAttributeList(int attrNumber, int size, float[] data) {
		int vboId = glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		vbos.add(vboId);

		FloatBuffer buffer = storeDataToFloatBuffer(data);

		GL15.glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		// Define structure of the data
		GL20.glVertexAttribPointer(attrNumber, size, GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private FloatBuffer storeDataToFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data).flip();
		return buffer;
	}

	private void bindIndicesBuffer(int[] indices) {
		int vboId = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
		vbos.add(vboId);

		IntBuffer buffer = storeDataToIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	private IntBuffer storeDataToIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data).flip();
		return buffer;
	}

	public int loadCubeMap(String[] files) {
		int texID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);

		for (int i = 0; i < files.length; i++) {
			String file = files[i];
			TextureData data = decodeTextureData(file);
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X+i, 0, GL11.GL_RGBA, data.getWidth(), data.getHeight(), 0, GL11.GL_RGBA,
					GL11.GL_UNSIGNED_BYTE, data.getBuffer());
		}
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		textures.add(texID);
		return texID;
	}

	private TextureData decodeTextureData(String file) {
		int width = 0;
		int height = 0;
		ByteBuffer buf = null;

		PNGDecoder decoder = null;
		try {
			decoder = new PNGDecoder(Loader.class.getResourceAsStream(file));
			width = decoder.getWidth();
			height = decoder.getHeight();
			buf = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buf, width * 4, Format.RGB);
			buf.flip();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new TextureData(width, height, buf);
	}

	public void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	public void cleanUp() {
		for (Integer i : vaos) {
			GL30.glDeleteVertexArrays(i);
		}
		for (Integer i : vbos) {
			GL15.glDeleteBuffers(i);
		}
		for (Integer i : textures) {
			GL11.glDeleteTextures(i);
		}
	}
}
