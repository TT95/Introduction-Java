package hr.fer.zemris.linearna;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testiranje klase Matrix i AbstractMatrix
 * @author Teo Toplak
 *
 */
public class MatrixTest {

	public Matrix m1;
	public Matrix m2;
	public Matrix m3;
	public Matrix m4;
	public Matrix m5;
	public Matrix m6;
	public Matrix m7;
	
	public MatrixTest() {
		double[][] mat1 = {{1,2,3},{3,1,2},{2,2,7},{2,3,3}};
		m1 = new Matrix(4, 3, mat1, false);
		m1 = new Matrix(4, 3, mat1, true);
		double[][] mat2={{5,3,1},{4,2,1},{4,1,2},{6,3,1}};
		m2 = new Matrix(4, 3, mat2, true);
		double[][] mat3={{5,3,1},{4,2,1}};
		m3 = new Matrix(2, 3, mat3, true);
		double[][] mat4={{5,2},{1,3},{5,2}};
		m4 = new Matrix(3, 2, mat4, true);
		double[][] mat5={{2,4,7},{1,6,2},{7,4,2}};
		m5= new Matrix(3,3,mat5,true);
		double[][] mat6={{1,2,4}};
		m6= new Matrix(1,3,mat6,true);
		double[][] mat7={{2},{1},{7}};
		m7= new Matrix(3,1,mat7,true);
	}
	
	@Test
	public void addTest() {
		Assert.assertTrue(m1.add(m2).get(0, 1)==5);
	}
	
	@Test
	(expected=IncompatibleOperandException.class)
	public void addFalseTest() {
		m1.add(m3).get(0, 1);
	}
	
	@Test
	public void nAddTest() {
		Assert.assertTrue(m1.nAdd(m2).get(0, 1)==5);
	}
	
	@Test
	public void subTest() {
		Assert.assertTrue(m1.sub(m2).get(0, 1)==-1);
	}
	
	@Test
	(expected=IncompatibleOperandException.class)
	public void subFailTest() {
		Assert.assertTrue(m1.nSub(m3).get(0, 1)==-1);
	}
	
	@Test
	public void nMultiplyTest() {
		//provjeravam preko ispisa
		System.out.println(m3.nMultiply(m4));
	}
	
	@Test
	(expected=IncompatibleOperandException.class)
	public void nMultiplyFailTest() {
		//provjeravam preko ispisa
		System.out.println(m3.nMultiply(m1));
	}
	
	@Test
	public void determinantTest() {
		Assert.assertTrue(m5.determinant()==-210);
	}
	
	@Test
	public void invertTest() {
		Assert.assertTrue(Math.abs(m5.nInvert().get(0, 0)+0.0190476)<1e-06);
	}
	
	
	@Test
	public void transposeTest() {
		//provjera preko prikaza
		m4.nTranspose(false).set(0, 0, 666);
		System.out.println(m4.nTranspose(true));
	}
	
	@Test
	public void subMatrixTest() {
		//provjera preko prikaza
		System.out.println(m1.subMatrix(2, 1, true));
	}
	
	@Test
	(expected=IncompatibleOperandException.class)
	public void FalseSubMatrixTest() {
		m1.subMatrix(10, 1, true);
	}
	
	@Test
	(expected=IncompatibleOperandException.class)
	public void subMatrixMoreTimesTest() {
		System.out.println(m5.subMatrix(2, 2, true)
				.subMatrix(0, 0, false).subMatrix(0,0,true));
	}
	
	@Test
	(expected=IncompatibleOperandException.class)
	public void toVectorFailTest() {
		m1.toVector(true);
	}
	
	@Test
	public void subMatrixLiveTest() {
		IMatrix mat=m1.subMatrix(3, 1, false);
		Assert.assertTrue(mat.get(0, 0)==1);
	}
	
	@Test
	public void toVec1Test() {
		Assert.assertTrue(m6.toVector(true).get(0)==1);
	}
	@Test
	public void toVec2Test() {
		Assert.assertTrue(m7.toVector(false).get(0)==2);
	}
	@Test
	public void scalaMulTest() {
		Assert.assertTrue(m1.scalarMultiply(3).get(1, 1)==3);
	}
	
	@Test
	public void nScalaMulTest() {
		Assert.assertTrue(m1.nScalarMultiply(3).get(1, 1)==3);
	}
	
	@Test
	public void makeIdentityTest() {
		//provjera preko ispisa
		System.out.println(m5.makeIdentity());
	}
	
	@Test
	public void otherTests() {
		IMatrix mat=new Matrix(1, 1);
		Assert.assertTrue(mat.newInstance(1, 1).get(0, 0)==0);
		IVector vec = m7.toVector(true);
		vec.newInstance(1);
		
	}
	
}
