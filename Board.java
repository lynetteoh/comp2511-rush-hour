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
	
	public Board(int[][] matrix, ArrayList<Vehicle> vehicles, int n)
	{
		this.matrix = matrix;
		this.n = n;
		this.vehiclesList = vehicles;
	}
	
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		Board b = new Board(n);
		b.printBoard();
		
		Generator g = new Generator();
		b = g.RandomGenerator1(6);
		
		Solver s = new Solver(b);
		System.out.println("Solved: " + s.Solve());
	}
	
	public void printBoard() {
		System.out.println("Board:");
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
		} 
		else { // vertical; path represents column 
			for (int i = start; i <= end; i++) { 
				matrix[i][p] = id;
			}	
		}
		vehiclesList.add(v);
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
		int movesForwards = canMoveForward(v); 
		//System.out.println("moves forward = " + movesForwards);
		if(movesForwards > 0)
		{
			if(v.getOrient() == 1)
			{
				int[] array = getArray(v);
				
				for(int i = 0; i < array.length; i++)
				{
					
					if(array[i] == v.getId() && v.getLength() > 0)
					{
						array[i] = 0;
						array[i + 1] = v.getId();
						array[i + 2] = v.getId();
						if(v.getLength() == 3)
						{
							array[i + 3] = v.getId();
						}
						return true;
					}
				}
			} 
			else if(v.getOrient() == 2)
			{
				int id = v.getId();
				int path = v.getPath();
				int[][] matrix = this.getMatrix();
				
				for(int i = 0; i < this.n; i++)
				{
					if(matrix[i][path] == id)
					{
						matrix[i][path] = 0;
						matrix[i + 1][path] = id;
						matrix[i + 2][path] = id;
						if(v.getLength() == 3)
						{
							matrix[i + 3][path] = id;
						}
						break;
					}
				}
				this.setMatrix(matrix);
				
				return true;
			}
			
		}
		return false;
	}
	
	public boolean moveBackward(Vehicle v)
	{
		int movesBackwards = canMoveBackward(v);
		//System.out.print("moves backward = " + movesBackwards + " ");
		if(movesBackwards > 0)
		{
			if(v.getOrient() == 1)
			{
				int[] array = getArray(v);
				
				for(int i = array.length - 1; i > 0; i--)
				{
					if(array[i] == v.getId() && v.getLength() > 0)
					{
						//System.out.println("array[i]")
						array[i] = 0;
						array[i - 1] = v.getId();
						array[i - 2] = v.getId();
						if(v.getLength() == 3)
						{
							array[i - 3] = v.getId();
						}
						/*int[] pos = v.getPosition();
						for(int j = 0; j < v.getLength(); j++)
						{
							pos[j] = v.getPosition()[j] - 1;
						}
						v.setPosition(pos); */
						
						return true;
					}
				}
			}
			else if(v.getOrient() == 2)
			{
				int id = v.getId();
				
				int path = v.getPath();
				int[][] matrix = this.getMatrix();
				
				for(int i = 0; i < this.n; i++)
				{
					if(matrix[i][path] == id)
					{
						matrix[i - 1][path] = id;
						if(v.getLength() == 3)
						{
							matrix[i + 2][path] = 0;
						}
						else if (v.getLength() == 2)
						{
							matrix[i + 1][path] = 0;
						}
						break;
					}
				}
				this.setMatrix(matrix);
				
				return true;
			}
			
		}
		return false;
	}
	
	public boolean canPlaceVehicle(int orient, int path, int[] position)
	{
		int[][] matrix = this.matrix;
		
		if(orient == 1)
		{
			for(int i : matrix[path])
			{
				System.out.print(i + " ");
			}
			if(matrix[path][position[0]] == 0 && matrix[path][position[1]] == 0)
			{
				if(position.length == 3)
				{
					if(matrix[path][position[2]] == 0)
					{
						return true;
					}
					return false;
				}
				return true;
			}
		}
		else if(orient == 2)
		{
			if(matrix[position[0]][path] == 0 && matrix[position[1]][path] == 0)
			{
				if(position.length == 3)
				{
					if(matrix[position[2]][path] == 0)
					{
						return true;
					}
					return false;
				}
				return true;
			}
		}
		
		return false;
	}
	
	public int canMoveForward(Vehicle v) {
		
		int counter = 0;
		int[] array = getArray(v);
		
		//System.out.print("\tForward: ");
		//for(int i : array)
		//	System.out.print(i + ", ");
		//System.out.println();
		
		boolean startCounter = false;
		for(int i = 0; i < array.length; i++)
		{
			if(array[i] == v.getId() && !startCounter)
			{
				startCounter = true;
			}
			if(startCounter && array[i] == 0)
			{
				counter++;
			}
			if(startCounter && array[i] != 0 && array[i] != v.getId())
			{
				startCounter = false;
			}
		}
		//System.out.println("\t\t\tSteps forward: " + counter);
		return counter; 
		// returns max no. of steps the car can move forward
	}
	
	public int canMoveBackward(Vehicle v) {
		
		int counter = 0;
		int[] array = getArray(v);
		
		//System.out.print("\tBackward: ");
		//for(int i : array)
		//	System.out.print(i + ", ");
		//System.out.println();
		
		boolean startCounter = false;
		for(int i = array.length - 1; i >= 0; i--)
		{
			if(array[i] == v.getId() && !startCounter)
			{
				startCounter = true;
			}
			
			if(startCounter && array[i] == 0)
			{
				counter++;
			}
			if(startCounter && array[i] != 0 && array[i] != v.getId())
			{
				startCounter = false;
			}
		}
		
		return counter;
		// returns max no. of steps the car can move backward
	}
	
	public int[] getArray(Vehicle v )
	{
		int[] array = new int[this.matrix[0].length];
		
		if(v.getOrient() == 1)		// horizontal
		{
			array = this.matrix[v.getPath()];
		}
		else if(v.getOrient() == 2)		// vertical
		{ 
		    for(int i = 0; i < array.length; i++){
		       array[i] = this.matrix[i][v.getPath()];
		    }
		}
		//for (int i = 0; i < array.length; i++) { 
		//	System.out.print(array[i] + " ");
		//}
		//System.out.println("");
		return array;
	}
	
}
