package hr.fer.zemris.java.raytracer.model;

/**
 * Class representing sphere with set centre, radius and koeficients for
 * calculating diffuse and reflective component
 * 
 * @author Teo Toplak
 *
 */
public class Sphere extends GraphicalObject{

	/**
	 * center od sphere
	 */
	private Point3D center;
	/**
	 * radius of sphere
	 */
	private double radius;
	/**
	 * diffuse koeficient for red colour
	 */
	private double kdr;
	/**
	 * diffuse koeficient for green colour
	 */
	private double kdg;
	/**
	 * diffuse koeficient for blue colour
	 */
	private double kdb;
	/**
	 * reflective koeficient for red colour
	 */
	private double krr;
	/**
	 * reflective koeficient for green colour
	 */
	private double krg;
	/**
	 * reflective koeficient for blue colour
	 */
	private double krb;
	/**
	 * 'n' koeficient for calculating reflective component
	 */
	private double krn;
	
	/**
	 * Constructor for sphere.
	 * @param center center od sphere
	 * @param radius radius of sphere
	 * @param kdr diffuse koeficient for red colour
	 * @param kdg diffuse koeficient for green colour
	 * @param kdb diffuse koeficient for blue colour
	 * @param krr reflective koeficient for red colour
	 * @param krg reflective koeficient for green colour
	 * @param krb reflective koeficient for blue colour
	 * @param krn 'n' koeficient for calculating reflective component
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		
		Point3D l = ray.direction;
		Point3D originSubCenter = ray.start.sub(center);
		double numerator1= -1*(l.scalarProduct(originSubCenter));
		double numerator2= Math.sqrt(Math.pow(l.scalarProduct(originSubCenter),2)-
				originSubCenter.scalarProduct(originSubCenter)+radius*radius);
		
		Double distance = Math.min(numerator1+numerator2, numerator1-numerator2);
		if(distance.isNaN()) {
			return null;
		}
		
		return new RayIntersection(ray.start.add(ray.direction.scalarMultiply(distance)),distance,true) {
			
			@Override
			public Point3D getNormal() {
				return ray.start.add(ray.direction.scalarMultiply(distance)).sub(center).normalize();
			}
			
			@Override
			public double getKrr() {
				return krr;
			}
			
			@Override
			public double getKrn() {
				return krn;
			}
			
			@Override
			public double getKrg() {
				return krg;
			}
			
			@Override
			public double getKrb() {
				return krb;
			}
			
			@Override
			public double getKdr() {
				return kdr;
			}
			
			@Override
			public double getKdg() {
				return kdg;
			}
			
			@Override
			public double getKdb() {
				return kdb;
			}
		};
					
	}
	
	
}
