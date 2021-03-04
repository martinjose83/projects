import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.stream.Stream;

import javax.naming.directory.InvalidAttributeValueException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ProjectMathTest {
	ProjectMath p;

	@BeforeEach
	public void initialize() throws Exception {
		p = new ProjectMath();
	}

	public static Collection addTestData() {
		return Arrays.asList(new Object[][] { { 3, 2, 1 }, { 0, 0, 0 }, { 2, 0, 2 }, { -3, -3, 0 }, { -1, 1, -2 },
				{ 3.6, -1.2, 4.8 }, { 212.81, 123.56, 89.25 } });
	}

	@ParameterizedTest(name = "{index}: testAdd({1},{2})= {0}")
	@MethodSource("addTestData")
	public void testAdd(double exp, double v1, double v2) {
		assertEquals(exp, p.Add(v1, v2));
	}

	public static Collection subTestData() {
		return Arrays.asList(new Object[][] { { 1, 3, 2 }, { 0, 0, 0 }, { 2, 0, -2 }, { -3, -3, 0 }, { 3, 1, -2 },
				{ -6, -1.2, 4.8 }, { 34.31, 123.56, 89.25 } });
	}

	@ParameterizedTest(name = "{index}: testSub({0},{1},{2}")
	@MethodSource("subTestData")
	public void testSub(double exp, double v1, double v2) {
		assertEquals(exp, p.Sub(v1, v2));
	}

	public static Collection multiplyTestData() {
		return Arrays.asList(new Object[][] { { 3, 2, 6 }, { 0, 0, 0 }, { 2, 0, 0 }, { -3, -3, 9 }, { -1, 4, -4 },
				{ 3.6, -1.2, -4.32 }, { 212.81, 123.56, 26294.80 } });
	}

	@ParameterizedTest(name = "{index}: testMultiply({0},{1})= {2}")
	@MethodSource("multiplyTestData")
	public void testMultiply(double v1, double v2, double exp) {
		assertEquals(exp, p.Multiply(v1, v2));
	}

	public static Collection divideTestData() {
		return Arrays.asList(new Object[][] { { 3, 2, 1.5 }, { 0, 5, 0 }, { -3, -3, 1 }, { -1, 4, -0.25 },
				{ 3.6, -1.2, -3 }, { 212.81, 123.56, 1.72 } });
	}

	@ParameterizedTest(name = "{index}: testDivide({0},{1})= {2}")
	@MethodSource("divideTestData")
	public void testDivide(double v1, double v2, double exp) throws InvalidAttributeValueException {
		assertEquals(exp, p.Divide(v1, v2));
	}

	public static Collection divide1TestData() {
		return Arrays.asList(new Object[][] { { 3, 0 }, { 0, 0 }});
	}

	@ParameterizedTest(name = "{index}: testDivide1({0},{1})")
	@MethodSource("divide1TestData")
	public void testDivide1(double v1, double v2) throws ArithmeticException {
		assertThrows(ArithmeticException.class, () -> p.Divide(v1, v2), () -> "Error: Cannot be divided by '0'");
	}

	static Stream<Arguments> vectorproTestData() {
		return Stream.of(Arguments.of(new int[] { 1, 2 }, new int[] { 1, 2 }, new int[] { 1, 4 }),
				Arguments.of(new int[] { -3, 4 }, new int[] { 3, 2 }, new int[] { -9, 8 }),
				Arguments.of(new int[] { 3, 4, 5, 6, 7 }, new int[] { 0, 1, 0, 3, 2 }, new int[] { 0, 4, 0, 18, 14 })

		);
	}

	@ParameterizedTest
	@MethodSource("vectorproTestData")
	public void vectorProTest(int[] a, int[] b, int[] exp) throws InputMismatchException, Exception {
		assertArrayEquals(exp, p.VectorProduct(a, b));

	}

	@Test
	void vectorProTest1() throws InputMismatchException {
		// exception if number of elements not equal.
		int[] c = { 1, 2, 3, 4 };
		int[] d = { 1, 2 };
		assertThrows(InputMismatchException.class, () -> p.VectorProduct(c, d),
				() -> " Error: cannot be multiplied matrix A rows should be equal to Matrix B couloumns");
	}

	@Test
	void vectorProTest2() throws InputMismatchException {
		// exception if number of elements not equal.
		int[] c = { 1, 2, 3, 4 };
		int[] d = null;
		assertThrows(Exception.class, () -> p.VectorProduct(c, d), () -> "Error Invalid Input");
	}

	@Test
	void testMatrixmulitplication() throws InputMismatchException, Exception {
		int[][] a = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 1, 2 }, { 3, 4, 5, 6 } };
		int[][] b = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 } };
		int[][] exp = { { 30, 40 }, { 70, 96 }, { 40, 62 }, { 50, 68 } };
		assertArrayEquals(exp, p.Matrixmulitplication(a, b));
	}

	@Test
	void testMatrixmulitplication1() throws InputMismatchException {
		// exception if columns of a not equal to rows of b.
		int[][] c = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 1, 2 }, { 3, 4, 5, 6 } };
		int[][] d = { { 1, 2 }, { 2, 3 }, { 3, 4 } };
		assertThrows(InputMismatchException.class, () -> p.Matrixmulitplication(c, d),
				() -> " Error: cannot be multiplied matrix A rows should be equal to Matrix B couloumns");
	}

	@Test
	void testMatrixmulitplication2() throws InputMismatchException, Exception {
		// invalid matrix a unequal columns
		int[][] a = { { 1, 2, 3, 4 }, { 5, 6, 7 }, { 9, 10, 1, 2 }, { 3, 4, 5, 6 } };
		int[][] b = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 } };
		assertThrows(Exception.class, () -> p.Matrixmulitplication(a, b), () -> "Error invalid input");
	}

	@Test
	void testMatrixmulitplication3() throws InputMismatchException, Exception {
		// null input;
		int[][] a = null;
		int[][] b = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 } };
		assertThrows(Exception.class, () -> p.Matrixmulitplication(a, b), () -> "Error invalid input");
	}

	@Test
	void testMatrixVecPro() throws Exception {
		int[] c = { 1, 2, 3, 4 };
		int[] d = { 30, 70, 40, 50 };
		int[][] a = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 1, 2 }, { 3, 4, 5, 6 } };
		assertArrayEquals(d, p.matrixVecPro(a, c));
	}

	@Test
	void testMatrixVecPro1() throws Exception {
		// exception if columns of a not equal to length of c.
		int[] c = { 1, 2, 3 };
		int[][] a = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 1, 2 }, { 3, 4, 5, 6 } };
		assertThrows(Exception.class, () -> p.matrixVecPro(a, c),
				() -> "Error Matrix a coloumns should be equal to length og vector b");
	}

	@Test
	void testMatrixVecPro2() throws Exception {
		// exception for invalid matrix unequal columns.
		int[] c = { 1, 2, 3, 4 };
		int[][] a = { { 1, 2, 3, 4 }, { 5, 6, 8 }, { 9, 10, 1, 2 }, { 3, 4, 5, 6 } };
		assertThrows(Exception.class, () -> p.matrixVecPro(a, c), () -> "Error invalid input");
	}

	@Test
	void testMatrixVecPro3() throws Exception {
		// exception for null input .
		int[] c = { 1, 2, 3, 4 };
		int[][] a = null;
		assertThrows(Exception.class, () -> p.matrixVecPro(a, c), () -> "Error invalid input");
	}

	@Test
	void tesIsIdentityMatrix() throws Exception {
		int[][] e = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		assertTrue(p.isIdentityMatrix(e));

	}

	static Stream<Arguments> identityMTestData() {
		return Stream.of(Arguments.of(new int[][] { { 1, 0, 0, 1 }, { 0, 1, 1, 0 }, { 0, 1, 1, 0 }, { 1, 0, 0, 1 } }),
				// false if other diagonal elements are -1s.
				Arguments.of(new int[][] { { 0, 0, 0, 1 }, { 0, 0, 1, 0 }, { 0, 1, 0, 0 }, { 1, 0, 0, 0 } }),
				// false if diagonal elements are -1s.
				Arguments.of(new int[][]  { { 0, 0, 0, -1 }, { 0, 0, -1, 0 }, { 0, -1, 0, 0 }, { -1, 0, 0, 0 } })
		);
	}
	@ParameterizedTest
	@MethodSource("identityMTestData")
	public void tesIsIdentityMatrix1 (int[][] a) throws  Exception {
		assertFalse(p.isIdentityMatrix(a));

	} 
	
	static Stream<Arguments> identityM1TestData() {
		return Stream.of(Arguments.of(new int[][] { { 0, 0, 0, -1 }, { 0, 0, -1, 0 }, { 0, -1, 0 }, { -1, 0, 0, 0 } }),
				// exception for improper matrix
				Arguments.of(new int[][] {null})
				// exception for null data
				
		);
	}
	@ParameterizedTest
	@MethodSource("identityM1TestData")
	public void testIsIdentityMatrix1 (int[][] a) throws  Exception {
		assertThrows(Exception.class, () -> p.isIdentityMatrix(a),"Error invalid input");

	} 
	
	static Stream<Arguments> MatrixTransposeTestData() {
		return Stream.of(Arguments.of(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 1, 2 }, { 3, 4, 5, 6 } },new int[][] { { 1, 5, 9, 3 }, { 2, 6, 10, 4 }, { 3, 7, 1, 5 }, { 4, 8, 2, 6 } } ),
				// square matrix
				Arguments.of(new int[][] { { 1, 2, 3 }, { 5, 6, 7 }, { 9, 10, 1 }, { 3, 4, 5 } },new int[][] { { 1, 5, 9, 3 }, { 2, 6, 10, 4 }, { 3, 7, 1, 5 } })
				// if not square matrix
				
		);
	}
	@ParameterizedTest
	@MethodSource("MatrixTransposeTestData")
	public void testMatrixTranspose (int[][] a, int[][] e) throws  NullPointerException, Exception {
		assertArrayEquals(e, (p.matrixTranspose(a)));
	} 
		
	

	@Test
	void testMatrixTranspose2() throws Exception {
		// exception for invalid input
		int[][] b = { { 1, 5, 9, 3 }, { 2, 6, 10 }, { 3, 7, 1, 5 }, { 4, 8, 2, 6 } };

		assertThrows(Exception.class, () -> p.matrixTranspose(b), () -> "Error invalid input");

	}

	@Test
	void testMatrixTranspose3() throws Exception {
		// exception for null input.
		int[][] c = null;
		assertThrows(Exception.class, () -> p.matrixTranspose(c), () -> "Error invalid input");
	}

	@Test
	void testProperMatrix() throws Exception {
		// exception if matrix rows are not proper.
		int[][] a = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 1, 2 }, { 3, 4, 5, 6 } };

		assertTrue(p.properMatrix(a));

	}

	@Test
	void testProperMatrix1() throws Exception {
		// exception if matrix rows are not proper.
		int[][] b = { { 1, 5, 9, 3 }, { 2, 6, 10 }, { 3, 7, 1, 5 }, { 4, 8, 2, 6 } };
		assertFalse(p.properMatrix(b));

	}

	@Test
	void testProperMatrix2() throws Exception {
		// exception if matrix rows are not proper.
		int[][] c = null;
		assertFalse(p.properMatrix(c));

	}

}
