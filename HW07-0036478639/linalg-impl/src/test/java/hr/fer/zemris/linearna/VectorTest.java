package hr.fer.zemris.linearna;

import org.junit.Assert;
import org.junit.Test;


public class VectorTest {

	public IVector vec1;
	public IVector vec2;
	public IVector vec3;
	public IVector vec4;
	public IVector vec5;
	
	public VectorTest() {
		double[] arr={1,2,3,4,5};
		vec1= new Vector(arr);
		double[] arr2={2,5,2,1,5};
		vec2=new Vector(arr2);
		double[] arr3={1};
		vec3=new Vector(arr3);
		double[] arr4={1,2,3};
		vec4=new Vector(false,true,arr4);
		double[] arr5={3,1,2};
		vec5=new Vector(true,false,arr5);
	}
	
	@Test
	public void addTest() {
		Assert.assertTrue(vec1.add(vec2).get(2)==5);
	}
	
	@Test
	(expected=IncompatibleOperandException.class)
	public void addTestFailed() {
		vec1.add(vec3);
	}
	
	@Test
	public void nAddTest(){
		Assert.assertTrue(vec1.nAdd(vec2).get(2)==5);
	}
	
	@Test
	(expected=IncompatibleOperandException.class)
	public void nAddFailTest(){
		vec1.nAdd(vec3);
	}
	
	@Test
	public void subTest(){
		Assert.assertTrue(vec1.sub(vec2).get(2)==1);
	}
	
	@Test
	(expected=IncompatibleOperandException.class)
	public void subFailTest(){
		vec1.sub(vec3);
	}
	
	@Test
	public void nSubTest(){
		Assert.assertTrue(vec1.nSub(vec2).get(2)==1);
	}
	
	@Test
	(expected=IncompatibleOperandException.class)
	public void  nSubTestFail(){
		vec1.nSub(vec3);
	}
	
	@Test
	public void scalarMultiplyTest(){
		Assert.assertTrue(vec1.scalarMultiply(2).get(4)==10);
	}
	
	@Test
	public void nScalarMultiplyTest(){
		Assert.assertTrue(vec1.nScalarMultiply(2).get(4)==10);
	}
	
	@Test
	public void normalizeTest(){
		Assert.assertTrue(Math.abs(vec4.normalize().get(0)-0.26726124)<1E-06);
		
	}
	
	@Test
	public void nNormalizeTest(){
		Assert.assertTrue(Math.abs(vec4.nNormalize().get(0)-0.26726124)<1E-06);
		
	}
	
	@Test
	public void normTest(){
		Assert.assertTrue(Math.abs(vec4.norm()-3.741657)<1E-06);
	}
	
	@Test
	public void normWithZeroTest(){
		double[] arr = {0};
		Assert.assertTrue(Math.abs((new Vector(arr)).norm()-1)<1E-06);
	}
	
	@Test
	public void cosineTest(){
		Assert.assertTrue(Math.abs(vec4.cosine(vec5)-0.785714)<1E-06);
	}
	
	@Test
	(expected= IncompatibleOperandException.class)
	public void cosineFailTest(){
		Assert.assertTrue(Math.abs(vec4.cosine(vec1)-0.785714)<1E-06);
	}
	
	@Test
	public void scalarProductTest(){
		Assert.assertTrue(Math.abs(vec4.scalarProduct(vec5)-11)<1E-06);
	}
	
	@Test
	public void nVectorProductTest(){
		Assert.assertTrue(vec4.nVectorProduct(vec5).get(2)==-5);
	}
	
	
	
	@Test
	(expected=IncompatibleOperandException.class)
	public void nVectorProductFailTest(){
		vec1.nVectorProduct(vec5);
	}
	
	@Test
	public void nFromHomogeneusTest(){
		Assert.assertTrue(vec1.nFromHomogeneus().get(1)==0.4);
	}
	
	@Test
	(expected=UnsupportedOperationException.class)
	public void nFromHomogeneusFailTest(){
		vec3.nFromHomogeneus();
	}
	
	@Test
	public void toRowMatrixTest() {
		System.out.println(vec1.toRowMatrix(false));
	}
	
	@Test
	public void toRowMatrixTestLive() {
		System.out.println(vec1.toRowMatrix(true));
	}
	
	@Test
	public void toColMatrixTestLive() {
		System.out.println(vec1.toColumnMatrix(true).toString());
		
	}
	
	@Test
	public void toColMatrixTest() {
		System.out.println(vec1.toColumnMatrix(false).toString());
		
	}
	
	@Test
	public void toStringTest() {
		System.out.println(vec4);
	}
	
	@Test
	(expected=UnmodifiableObjectException.class)
	public void setFailTests() {
		vec5.set(0, 1);
	}
	
	@Test
	public void otherTests() {
		vec2.set(1, 3);
		Assert.assertTrue(vec1.copy().get(0)==1);
		Assert.assertTrue(vec1.newInstance(vec1.getDimension()).get(0)==0);
		
	}
	
}
