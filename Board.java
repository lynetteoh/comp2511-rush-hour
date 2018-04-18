import java.util.ArrayList;

public class Board {
	// int[][] b = new int[6][6]
	public static int boardId = 1;
	private int id;
	private int vehicleIdCounter; 
	private int[][] matrix;
	private int n;
	private ArrayList<Vehicle> vehiclesList = new ArrayList<Vehicle>();
	
	public Board(int n) {
		
		this.n = n;
		this.matrix = new int[n][n];
		for(int i=0;i<n;i++) {
			for (int j=0;j<n;j++) {
				this.matrix[i][j] = 0;
			}
		}
		this.id = boardId;
		boardId += 1;
	}
	
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		Board b = new Board(6);
		b.printBoard();
	}
	
	public void printBoard() {
		for(int i=0;i<n;i++) {
			for (int j=0;j<n;j++) {
				System.out.print(this.matrix[i][j] + " \t");
			}
			System.out.println("");
		}
	}
}
