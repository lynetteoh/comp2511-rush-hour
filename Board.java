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
		g.Generator1(6);
		
	}
	
	public void printBoard() {
		for(int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
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
		System.out.println("moves forward = " + movesForwards);
		if(movesForwards > 0)
		{
			if(v.getOrient() == 1)
			{
				int[] array = v.getArray(this.matrix);
				
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
				int[] position = v.getPosition();
				System.out.println(position);
				int path = v.getPath();
				int[][] matrix = this.getMatrix();

				matrix[position[0] + 1][path] = v.getId();
				matrix[position[1] + 1][path] = v.getId();
				if(v.getLength() == 3)
				{
					matrix[position[2] + 1][path] = v.getId();
				}
				matrix[position[0]][path] = 0;
				
				this.setMatrix(matrix);
				
				for(int j = 0; j < v.getLength(); j++)
				{
					position[j] = v.getPosition()[j] - 1;
				}
				v.setPosition(position);
				return true;
			}
			
		}
		return false;
	}
	
	public boolean moveBackward(Vehicle v)
	{
		int movesBackwards = v.canMoveBackward(this.matrix);
		System.out.print("moves backward = " + movesBackwards + " ");
		if(movesBackwards > 0)
		{
			if(v.getOrient() == 1)
			{
				int[] array = v.getArray(this.matrix);
				
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
						int[] pos = v.getPosition();
						for(int j = 0; j < v.getLength(); j++)
						{
							pos[j] = v.getPosition()[j] - 1;
						}
						v.setPosition(pos);
						
						return true;
					}
				}
			}
			else if(v.getOrient() == 2)
			{
				int[] position = v.getPosition();
				int path = v.getPath();
				int[][] matrix = this.getMatrix();

				matrix[position[0] - 1][path] = v.getId();
				matrix[position[1] - 1][path] = v.getId();
				if(v.getLength() == 3)
				{
					matrix[position[2] - 1][path] = v.getId();
					matrix[position[0] + 2][path] = 0;
				}
				else
					matrix[position[0] + 1][path] = 0;
				
				this.setMatrix(matrix);
				
				for(int j = 0; j < v.getLength(); j++)
				{
					position[j] = v.getPosition()[j] - 1;
				}
				v.setPosition(position);
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
}
