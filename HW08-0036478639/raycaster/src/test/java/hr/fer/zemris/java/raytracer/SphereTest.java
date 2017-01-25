package hr.fer.zemris.java.raytracer;

import static org.junit.Assert.*;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Sphere;
 
import org.junit.Test;
 
/**
 * Testing class for testing utility methods for finding intersection of ray
 * with spheres.
 * 
 * @author Teo Toplak
 *
 */
public class SphereTest {
	
	Sphere sphere1;
	Sphere sphere2;
	Sphere sphere3;
	Sphere sphere4;
	Sphere sphere5;
	
	public SphereTest() {
		sphere1 = new Sphere(new Point3D(0, 0, 0), 10, 0, 0, 0, 0, 0, 0, 0);
		sphere2 = new Sphere(new Point3D(0, 3, 0), 10, 0, 0, 0, 0, 0, 0, 0);
		sphere3 = new Sphere(new Point3D(5, 5, 0), 2, 0, 0, 0, 0, 0, 0, 0);
		sphere4 = new Sphere(new Point3D(5, 5, 5), 2, 0, 0, 0, 0, 0, 0, 0);
		sphere5 = new Sphere(new Point3D(5, 5, 5), 2, 1, 2, 3, 4, 5, 6, 7);
	}
	
	/**
	 * Calcutales if two {@link Point3D} are equal with little offset(1e-06)
	 * @param point1 first point
	 * @param point2 second point
	 * @return
	 */
	private static boolean equalPoints(Point3D point1, Point3D point2) {
		return doubleEqual(point1.x, point2.x, 1e-06)
				&& doubleEqual(point1.y, point2.y, 1e-06)
				&& doubleEqual(point1.z, point2.z, 1e-06);
	}

	/**
	 * Returns true if modul difference between two double values is smaller
	 * then distance set.
	 * 
	 * @param arg1 first double value
	 * @param arg2 second double value
	 * @param distance distance set
	 * @return true if modul difference between two double values is smaller
	 *         then distance set
	 */
	private static boolean doubleEqual(double arg1, double arg2, double distance) {
		return Math.abs(arg1 - arg2) < distance;
	}
       
	@Test
	public void SphereRayIntersectionTest1() {
		Ray ray = new Ray(new Point3D(20, 0, 0), new Point3D(-1, 0, 0));
		RayIntersection intersection = sphere1.findClosestRayIntersection(ray);
		assertTrue(equalPoints(intersection.getPoint(), new Point3D(10, 0, 0)));
		assertTrue(intersection.isOuter());
	}

	@Test
	public void SphereRayIntersectionTest2() {
		Ray ray = new Ray(new Point3D(0, -5, 10), new Point3D(0, 1, 0));
		RayIntersection intersection = sphere2.findClosestRayIntersection(ray);
		assertTrue(equalPoints(intersection.getPoint(), new Point3D(0, 3, 10)));
		assertTrue(intersection.isOuter());
	}

	@Test
	public void SphereRayIntersectionTest3() {
		Ray ray = new Ray(new Point3D(0, 0, 0), new Point3D(2, 2, 0));
		RayIntersection intersection = sphere3.findClosestRayIntersection(ray);
		assertFalse(equalPoints(intersection.getPoint(), new Point3D(3, 3, 0)));
	}

	@Test
	public void SphereRayIntersectionTest4() {
		Ray ray = new Ray(new Point3D(0, 0, 0), new Point3D(0, 0, 1));
		RayIntersection intersection = sphere4.findClosestRayIntersection(ray);
		assertTrue(intersection == null);
	}
	
	@Test
	public void SphereRayIntersectionTest5() {
		Ray ray = Ray.fromPoints(new Point3D(0, 0, 0), new Point3D(1, 1, 1));
		RayIntersection intersection = sphere4.findClosestRayIntersection(ray);
		assertTrue(equalPoints(intersection.getPoint(), new Point3D(3.845299462,
				3.845299462, 3.845299462)));
		assertTrue(intersection.isOuter());
	}

	@Test
	public void checkSphereDefaultCoefficients() {
		Ray ray = new Ray(new Point3D(5, 5, 3.5), new Point3D(0, 1, 0));
		RayIntersection intersection = sphere5.findClosestRayIntersection(ray);

		assertTrue(intersection.getKdr() == 1);
		assertTrue(intersection.getKdg() == 2);
		assertTrue(intersection.getKdb() == 3);
		assertTrue(intersection.getKrr() == 4);
		assertTrue(intersection.getKrg() == 5);
		assertTrue(intersection.getKrb() == 6);
		assertTrue(intersection.getKrn() == 7);
		}

	
	

}
