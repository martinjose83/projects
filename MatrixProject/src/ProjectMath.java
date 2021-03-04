import java.util.InputMismatchException;

public class ProjectMath {
	public ProjectMath() throws Exception {

	}

	public void printM(int[][] a) {
		for (int i = 0; i < a.length; i++) {

			for (int j = 0; j < a[0].length; j++) {
				System.out.print(a[i][j] + ", ");
			}
			System.out.println();
		}
	}

	/**
	 * 
	 * @param i double, input;
	 * @param j double, input;
	 * @return i+j with two decimal places
	 */
	public double Add(double i, double j) {
		return Math.round((i + j) * 100) / 100.0;

	}

	/**
	 * 
	 * @param i double, input;
	 * @param j double, input;
	 * @return i-j with two decimal places
	 */
	public double Sub(double i, double j) {
		return Math.round((i - j) * 100) / 100.0;
	}

	/**
	 * 
	 * @param i double, input;
	 * @param j double, input;
	 * @return i*j with two decimal places
	 */
	public double Multiply(double i, double j) {
		return Math.round((i * j) * 100) / 100.0;
	}

	/**
	 * 
	 * @param i double, input;
	 * @param j double, input;
	 * @return i/j with two decimal places
	 */
	public double Divide(double i, double j) throws ArithmeticException {

		if (j == 0) {
			throw new ArithmeticException("Error: Cannot be divided by '0'");
		} else if (i == 0)
			return i;

		else
			return Math.round((i / j) * 100) / 100.0;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return product of [a[i]*b[i]]
	 * @throws InputMismatchException if length of a not equal to length of b
	 * @throws Exception              if null input.
	 */
	public int[] VectorProduct(int[] a, int[] b) throws InputMismatchException, Exception {
		int[] res = new int[a.length];
		if (a.length != b.length) {

			throw new InputMismatchException(
					"Error: cannot be multiplied Vector A length should be equal to Vector B length");
		}
		if (a == null || b == null) {
			throw new Exception("Error Invalid Input");
		} else {
			for (int i = 0; i < a.length; i++) {
				res[i] = a[i] * b[i];
			}
		}
		return res;

	}

	/**
	 * 
	 * @param a int[][] integer matrix.
	 * @param b int[][] integer matrix.
	 * @return int[][] integer matrix product of a*b
	 * @throws InputMismatchException if cols of a not equal to rows of b
	 * @throws Exception              invalid matrix or null input;
	 */
	public int[][] Matrixmulitplication(int[][] a, int[][] b) throws InputMismatchException, Exception {
		if (!(properMatrix(a) && properMatrix(b))) {
			throw new Exception("Error not a proper Matrix");
		}

		int ac = a.length;
		int ar = a[0].length;
		int bc = b.length;
		int br = b[0].length;
		int[][] res = new int[ac][br];
		if (ar != bc) {
			throw new InputMismatchException(
					" Error: cannot be multiplied matrix A rows should be equal to Matrix B couloumns");
		}

		else {

			for (int i = 0; i < ac; i++) {
				for (int j = 0; j < br; j++) {
					res[i][j] = 0;
					for (int k = 0; k < ar; k++) {
						res[i][j] += a[i][k] * b[k][j];
					}
					// System.out.println(res[i][j]);
				}

			}
		}

		return res;

	}

	/**
	 * 
	 * @param a int[][] integer matrix
	 * @param b int[] integer vector
	 * @return int[] integer vector product of a and b
	 * @throws Exception if matrix is invalid or null entry, or cols of a not equal
	 *                   to length of b
	 */
	int[] matrixVecPro(int[][] a, int[] b) throws Exception {
		if (!properMatrix(a) || b == null) {
			throw new Exception("Error invalid input");
		}
		int ac = a.length;
		int ar = a[0].length;
		int bc = b.length;
		int[] res = new int[ac];

		if (ar != bc) {
			throw new Exception("Error Matrix a coloumns should be equal to length og vector b");
		} else {
			for (int i = 0; i < ac; i++) {
				for (int j = 0; j < bc; j++) {
					res[i] = res[i] + a[i][j] * b[j];

				}

			}
		}
		return res;
	}

	/**
	 * 
	 * @param a int[][] input matrix;
	 * @return true if a is an identity matrix or false if not.
	 * @throws Exception if invalid matrix or null input
	 */
	public boolean isIdentityMatrix(int[][] a) throws Exception {
		boolean res;

		if (!properMatrix(a)) {
			throw new Exception("Error invalid input");
		}
		if (a.length != a[0].length)
			return false;

		else {

		}
		for (int i = 0; i < a.length; i++) {

			for (int j = 0; j < a.length; j++) {
				if ((i == j) && (a[i][j] != 1))
					return false;
				if ((i != j) && (a[i][j] != 0))
					return false;
			}
		}
		return true;

	}

	/**
	 * 
	 * @param a int[][] input matrix;
	 * @return transpose of a;
	 * @throws NullPointerException if null input
	 * @throws Exception            if improper matrix;
	 */
	public int[][] matrixTranspose(int[][] a) throws NullPointerException, Exception {
		if (!properMatrix(a)) {
			throw new Exception("Error invalid input");
		}

		int[][] res = new int[a[0].length][a.length];
		for (int i = 0; i < a.length; i++) {

			for (int j = 0; j < a[0].length; j++) {
				res[j][i] = a[i][j];
			}
		}
		return res;
	}

	/**
	 * 
	 * @param a int[][] input matrix
	 * @return true if valid matrix with no elements missing for a matrix
	 * @throws Exception            if in equal rows;
	 * @throws NullPointerException if empty set or null input.
	 */
	public boolean properMatrix(int[][] a) throws Exception, NullPointerException {

		if (a == null) {
			return false;
		}
		int rows = a.length;
		int cols = a[0].length;
		for (int i = 1; i < rows; i++) {
			if (a[i].length != cols) {
				return false;
			}
		}

		return true;

	}

	public static void main(String[] args) throws Exception {
		new ProjectMath();
	}
}