package particles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import renderEngine.Loader;

public class ParticleMaster {
	
	private static List<Particle> partilcles = new ArrayList<>();
	private static ParticleRenderer renderer ;
	
	public static void init(Loader loader,Matrix4f projectionMatrix){
		renderer = new ParticleRenderer(loader, projectionMatrix);
	}
	
	public static void update(){
		Iterator<Particle> it = partilcles.iterator();
		while(it.hasNext()){
			Particle pt = it.next();
			boolean stillAlive = pt.update();
			if(!stillAlive){
				it.remove();
			}
		}
	}
	
	public static void render(Camera camera){
		renderer.render(partilcles, camera);
	}
	
	public static void cleanUp(){
		renderer.cleanUp();
	}
	
	public static void addParticle(Particle particle){
		partilcles.add(particle);
	}
}
