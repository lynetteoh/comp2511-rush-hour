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
		Board b = new Board(n);
		b.printBoard();
		Generator g = new Generator();
		//g.Generator1(6);
	}
	
	public void printBoard() {
		for(int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(this.matrix[i][j] + " \t");
			}
			System.out.println("");
		}
	}
	
	// creates a new vehicle from the data passed in 
	// places the vehicle on current board 
	public void placeVehicle(int orient, int path, int[] position) { 
		// create the vehicle 
		Vehicle v = new Vehicle(orient, path, position); 
		
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

	public static int getBoardId() {
		return boardId;
	}

	public static void setBoardId(int boardId) {
		Board.boardId = boardId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVehicleIdCounter() {
		return vehicleIdCounter;
	}

	public void setVehicleIdCounter(int vehicleIdCounter) {
		this.vehicleIdCounter = vehicleIdCounter;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public ArrayList<Vehicle> getVehiclesList() {
		return vehiclesList;
	}

	public void setVehiclesList(ArrayList<Vehicle> vehiclesList) {
		this.vehiclesList = vehiclesList;
	}
	
	public boolean moveForward(Vehicle v)
	{
		int movesForwards = v.canMoveForward(this.matrix); 
		if(movesForwards > 0)
		{
			int[] array = v.getArray(this.matrix);
			
			for(int i = 0; i < array.length; i++)
			{
				if(array[i] == id && v.getLength() > 0)
				{
					array[i] = 0;
					for(int j = 1; j <= v.getLength(); j++)
					{
						array[i + j] = v.getId();
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean moveBackward(Vehicle v)
	{
		int movesBackwards = v.canMoveBackward(this.matrix); 
		if(movesBackwards > 0)
		{
			int[] array = v.getArray(this.matrix);
			
			for(int i = array.length; i > 0; i--)
			{
				if(array[i] == id && v.getLength() > 0)
				{
					array[i] = 0;
					for(int j = 1; j <= v.getLength(); j++)
					{
						array[i + j] = v.getId();
					}
					return true;
				}
			}
		}
		return false;
	}
}
