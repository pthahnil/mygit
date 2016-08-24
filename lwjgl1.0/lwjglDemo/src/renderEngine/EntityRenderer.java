package renderEngine;

import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import textures.Texture;
import toolbox.Transformation;

public class EntityRenderer {

	private StaticShader shader;

	public EntityRenderer(StaticShader shader,Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionformationMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Map<TexturedModel, List<Entity>> entities) {
		for (TexturedModel model : entities.keySet()) {
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for (Entity entity : batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(),
						GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}

	private void prepareTexturedModel(TexturedModel model) {
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		Texture texture = model.getTexture();
		if(texture.isTransParent()){
			MasterRenderer.disAbleCulling();
		}
		shader.loadRows(texture.getRows());
		shader.loadFackLightLing(texture.isUseFackLightLing());
		
		shader.loadShineAttrs(texture.getShineDumper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
	}

	private void unbindTexturedModel() {
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void prepareInstance(Entity entity) {
		Matrix4f matrix = Transformation.createWorldMatrix(entity.getPosition(), new Vector3f(entity.getRotX(),entity.getRotY(),entity.getRotZ()), entity.getScale());
		shader.loadTransformationMatrix(matrix);
		shader.loadOffset(entity.getTexXoffSet(), entity.getTexYoffSet());
	}

}
