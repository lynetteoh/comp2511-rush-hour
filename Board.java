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
		Generator g = new Generator();
		g.Generator1(6);
	}
	
	public void printBoard() {
		for(int i=0;i<n;i++) {
			for (int j=0;j<n;j++) {
				System.out.print(this.matrix[i][j] + " \t");
			}
			System.out.println("");
		}
	}
	
	public void placeVehicle(Vehicle v) { 
		// set down the given car into matrix
		int p = v.getPath(); 
		int id = v.getId();
		int start = v.getPosition()[0];
		int end = v.getPosition()[v.getPosition().length-1];
		
		if (v.getOrient() == 1) { // horizontal; path represents row
			//System.out.println("end: " + end);
			for (int i = start; i <= end; i++) { 
				matrix[p][i] = id;
			}
		} else { // vertical; path represents column 
			for (int i = start; i <= end; i++) { 
				matrix[i][p] = id;
			}
			
		}
	}
	
}
