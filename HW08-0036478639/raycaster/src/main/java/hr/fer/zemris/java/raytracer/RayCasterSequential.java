package hr.fer.zemris.java.raytracer;


import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;
import java.util.List;

import hr.fer.zemris.java.raytracer.model.*;


/**
 * Class representing ray-tracer for rendering of 3D scenes.
 * Using multithreading for calculations (parallel).
 * @author Teo Toplak
 *
 */
public class RayCasterSequential {

	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
				new Point3D(10,0,0),
				new Point3D(0,0,0),
				new Point3D(0,0,10),
				20, 20);
	}
	
	/**
	 * Factory method for class implementing {@link IRayTracerProducer}
	 * @return tracer producer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {
				
				System.out.println("Započinjem izračune...");
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D zAxis = view.sub(eye).modifyNormalize();
				viewUp = viewUp.modifyNormalize();
				Point3D yAxis = viewUp.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				Point3D screenCorner = zAxis.sub(xAxis.scalarMultiply(horizontal/2))
						.add(yAxis.scalarMultiply(vertical/2));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x*horizontal/(width-1)))
								.sub(yAxis.scalarMultiply(y*vertical/(height-1)));
								Ray ray = Ray.fromPoints(eye, screenPoint);
								
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			/**
			 * Fills rgb array with colors created with given ray in given scene.
			 * @param scene scene
			 * @param ray ray
			 * @param rgb array for red, blue and green colour
			 */
			private void tracer(Scene scene, Ray ray, short[] rgb) {
				List<GraphicalObject> objects = scene.getObjects();
				RayIntersection intersection = findClosestIntersection(objects, ray);
				
				if(intersection==null) {
					rgb[0]=0;
					rgb[1]=0;
					rgb[2]=0;
					return;
				} else {
					determineColor(rgb,intersection,scene,objects);
				}	
				
			}
			
			/**
			 * Method determines color for given intersection in scene for list
			 * of objects matching given scene.
			 * 
			 * @param rgb rgb array for red, blue and green colour
			 * @param intersection intersection
			 * @param scene scene
			 * @param objects objects matching given scene
			 */
			private void determineColor(short[] rgb,RayIntersection intersection,
					Scene scene, List<GraphicalObject> objects) {
				
				rgb[0]=15;
				rgb[1]=15;
				rgb[2]=15;
				
				List<LightSource> sources = scene.getLights();
				
				
				for(LightSource source : sources) {
					Ray ray = Ray.fromPoints(source.getPoint(), intersection.getPoint());
					RayIntersection lightIntersection = findClosestIntersection(objects, ray);
					if(lightIntersection == null) {
						continue;
					}
					double baseIntersectionNorm = source.getPoint().sub(intersection.getPoint()).norm();
					double lightIntersectionNorm = source.getPoint().sub(lightIntersection.getPoint()).norm();
					if(lightIntersectionNorm+0.01 < baseIntersectionNorm) {
						continue;
					}
					addReflectiveComponent(source,ray, rgb, intersection);
					addDiffuseComponent(source, rgb, intersection);

				}
				
			}

			/**
			 * Adds reflective component to rgb array of colors.
			 * @param source source of light
			 * @param ray ray
			 * @param rgb rgb array for red, blue and green colour
			 * @param inter intersection
			 */
			private void addReflectiveComponent(LightSource source, Ray ray,
					short[] rgb, RayIntersection inter) {
				Point3D norm = inter.getNormal();
				Point3D l = source.getPoint().sub(inter.getPoint());
				Point3D lProjectComp = norm.scalarMultiply(l.scalarProduct(norm));
				Point3D r = lProjectComp.add(lProjectComp.negate().add(l).scalarMultiply(-1));
				Point3D v = ray.start.sub(inter.getPoint());
				double cos = r.normalize().scalarProduct(v.normalize());

				if(Double.compare(cos, 0) >= 0) {
					cos = Math.pow(cos,  inter.getKrn());
					rgb[0] += source.getR() * inter.getKrr() * cos;
					rgb[1] += source.getG() * inter.getKrg() * cos;
					rgb[2] += source.getB() * inter.getKrb() * cos;
				}		

			}
			
			/**
			 * Adds reflective component to rgb array of colors.
			 * @param source source of light
			 * @param rgb rgb array for red, blue and green colour
			 * @param inter intersection
			 */
			private void addDiffuseComponent(LightSource source,
					short[] rgb, RayIntersection sStar) {
				Point3D norm = sStar.getNormal();
				Point3D l = source.getPoint().sub(sStar.getPoint()).normalize();
				double product = l.scalarProduct(norm);

				rgb[0] += source.getR() * sStar.getKdr() * Math.max(product, 0);
				rgb[1] += source.getG() * sStar.getKdg() * Math.max(product, 0);
				rgb[2] += source.getB() * sStar.getKdb() * Math.max(product, 0);

			}
			
			
			/**
			 * Finds closest RayIntersection for given objects and ray.
			 * Returns null if no intersections exist.
			 * @param objects
			 * @param ray
			 * @return
			 */
			private RayIntersection findClosestIntersection(List<GraphicalObject> objects,
					Ray ray) {
				
				double distanceMin=Double.MAX_VALUE;
				RayIntersection possibleIntersection = null;
				for(GraphicalObject object : objects) {
					RayIntersection inter = object.findClosestRayIntersection(ray);
					if(inter != null) {
						double distance = inter.getDistance();
						if(distanceMin > distance) {
							distanceMin = distance;
							possibleIntersection = inter;
						}
					}
				}

				return possibleIntersection;
				
			}
		};
	}
}
