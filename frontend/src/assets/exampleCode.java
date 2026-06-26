import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MyMatrix {
	private static int offset;
	private int[][] data;

	public MyMatrix(int[][] a){
		this.data = a;
	}

	public int[][] getData() {
		return data;
	}

	public MyMatrix plus(MyMatrix B){
		int[][] b = B.getData();
		if (this.data == null || b == null)
			return new MyMatrix(new int[0][0]);
		int width = b[0].length;
		int height = b.length;
		int[][] ans = new int[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				ans[i][j] = this.data[i][j] + b[i][j];
		return new MyMatrix(ans);
	}


	public MyMatrix times(MyMatrix B){
		int[][] A = this.data;
		int[][] b = B.getData();
		if (A == null || b == null)
			return new MyMatrix(new int[0][0]);
		int m = A.length, n = A[0].length, p = b.length, q = b[0].length;
		int[][] ans = new int[m][q];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < q; j++) {
				int tmp = 0;
				for (int k = 0; k < n; k++)
					tmp += A[i][k] * b[k][j];
				ans[i][j] = tmp;
			}
		return new MyMatrix(ans);
	}

	public MyMatrix times(int b){
		int[][] A = this.getData();
		int row = A.length;
		int col = A[0].length;
		for (int i = 0;i<row;i++)
			for (int j = 0;j<col;j++)
				A[i][j] *= b;
		return new MyMatrix(A);
	}

	public MyMatrix transpose(){
		int[][] A = this.getData();
		int row = A.length;
		int col = A[0].length;
		int[][] ans = new int[col][row];
		for (int i = 0;i<row;i++)
			for (int j = 0;j<col;j++)
				 ans[j][i] = A[i][j];
		return new MyMatrix(ans);
	}

	private static MyMatrix readMatrix() {
		int m, n;
		int[][] result = new int[0][];
		String[] numbers;
		try {
			System.in.reset();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			for (int f = 0; f < offset; f++)
				br.readLine();
			String line = br.readLine();
			numbers = line.split("\\s+");
			m = Integer.parseInt(numbers[0]);
			if (m == 0)
				return null;
			n = Integer.parseInt(numbers[1]);
			result = new int[m][n];
			for (int i = 0; i < m; i++) {
				line = br.readLine();
				numbers = line.split("\\s+");
				for (int j = 0; j < n; j++)
					result[i][j] = Integer.parseInt(numbers[j]);
			}
			offset = m+1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new MyMatrix(result);
	}

	public MyMatrix plusFromConsole(){
		offset = 0;
		return plus(readMatrix());
	}

	public MyMatrix timesFromConsole(){
		offset = 0;
		return times(readMatrix());
	}

	public void print(){
		int[][] A = this.getData();
		System.out.println();
		for (int[] ints : A) {
			String arrayAsString = Arrays.toString(ints).substring(1, Arrays.toString(ints).length() - 1).replace(",", "");
			System.out.println(arrayAsString);
		}
		System.out.println();
	}
}
