package textures;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.nio.ByteBuffer;
import java.text.Format;

import org.lwjgl.examples.spaceinvaders.Texture;
import org.newdawn.slick.opengl.PNGDecoder;

public class TerrainTexture {

	int textureID;

	public TerrainTexture(int textureID) {
		this.textureID = textureID;
	}

	public int getTextureID() {
		return textureID;
	}

}
