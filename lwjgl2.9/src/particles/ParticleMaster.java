package particles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import renderEngine.Loader;

public class ParticleMaster {
	
	private static Map<ParticleTexture, List<Particle>> partilcles = new HashMap<ParticleTexture, List<Particle>>();
	private static ParticleRenderer renderer ;
	
	public static void init(Loader loader,Matrix4f projectionMatrix){
		renderer = new ParticleRenderer(loader, projectionMatrix);
	}
	
	public static void update(Camera camera){
		
		Iterator<Entry<ParticleTexture, List<Particle>>> mapIt = partilcles.entrySet().iterator();
		while(mapIt.hasNext()){
			List<Particle> list = mapIt.next().getValue();
			
			Iterator<Particle> it = list.iterator();
			while(it.hasNext()){
				Particle pt = it.next();
				boolean stillAlive = pt.update(camera);
				if(!stillAlive){
					it.remove();
					if(list.isEmpty()){
						mapIt.remove();
					}
				}
			}
			InsertionSort.sortHighToLow(list);
		}
		
	}
	
	public static void render(Camera camera){
		renderer.render(partilcles, camera);
	}
	
	public static void cleanUp(){
		renderer.cleanUp();
	}
	
	public static void addParticle(Particle particle){
		List<Particle> list = partilcles.get(particle.getTexture());
		if(list==null){
			list = new ArrayList<>();
			partilcles.put(particle.getTexture(), list);
		}
		list.add(particle);
	}
}
