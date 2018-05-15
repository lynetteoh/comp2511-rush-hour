import java.util.ArrayList;
import java.util.Stack;

public class Board {
	// int[][] b = new int[6][6]
	public static int boardId = 1;
	private int id;
	private int vehicleIdCounter;
	private int nMoves;
	private int[][] matrix;
	private int size;
	private ArrayList<Vehicle> vehiclesList = new ArrayList<Vehicle>();
	private ArrayList<Move> solution = new ArrayList<Move>();
	private Stack<Move> moves = new Stack<Move>();


	public Board(int size) {

		this.size = size;
		this.matrix = new int[size][size];
		for(int i=0;i<size;i++) {
			for (int j=0;j<size;j++) {
				this.matrix[i][j] = 0;
			}
		}
		this.id = boardId;
		boardId += 1;
	}

	public Board(int[][] matrix, ArrayList<Vehicle> vehicles, int size)
	{
		this.matrix = matrix;
		this.size = size;
		this.vehiclesList = vehicles;
		this.nMoves = 0;
	}
	public Board(Board b)
	{
		this.size = b.getSize();
		int [][] matrix = b.getMatrix();
		this.matrix = new int[size][size];
		for(int i=0;i<size;i++) {
			for (int j=0;j<size;j++) {
				this.matrix[i][j] = matrix[i][j];
			}
		}
		this.id = b.getId();
		this.vehicleIdCounter = b.getVehicleIdCounter();
		this.nMoves = b.getnMoves();
		this.vehiclesList = b.getVehiclesList();
		this.moves = (Stack<Move>) b.getMoves().clone();
	}

	public static void main(String[] args) {
		int size = Integer.parseInt(args[0]);
		Board b = new Board(size);
//		b.printBoard();

		Generator g = new Generator();
		b = g.RandomGenerator1(size);

		b.solve();
		System.out.println("Solved: \n" + b.getSolution());
	}

	public boolean solve() {
		Solver s = new Solver(this);
		ArrayList<Move> solution = s.solve();
		if (solution != null) {
			this.solution = solution;
			return true;
		}
		return false;
	}
	public void printBoard() {
		System.out.println("Board:");
		for(int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(this.matrix[i][j] + " \t");
			}
			System.out.println("");
		}
	}
	public boolean clearVehicles() {
		if (vehiclesList.isEmpty()) {
			return false;
		}
		vehiclesList.get(0).resetCount();
		return true;
	}
	// undo the last move taken by the board
	public boolean undo () {
		Move lastMove = moves.pop();
		if (lastMove == null) {
			return false;
		}
		Vehicle v = lastMove.getVehicle();
		int direction = lastMove.getDirection();
		if (direction > 0) {   // it moved forward hence move backward
			moveNSpaces(v, -1*direction);
			moves.pop();
		} else if (direction < 0) { // it moved backward hence move forward
			moveNSpaces(v, -1*direction);
			moves.pop();
		} else {
			return false;
		}
		nMoves-= 2;
		return true;
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

	public ArrayList<Move> getSolution() {
		return solution;
	}
	public Stack<Move> getMoves() {
		return moves;
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
	public int getnMoves() {
		return nMoves;
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
		return size;
	}
	public int getSize() {
		return size;
	}

	public void setN(int size) {
		this.size = size;
	}

	public ArrayList<Vehicle> getVehiclesList() {
		return vehiclesList;
	}

	public void setVehiclesList(ArrayList<Vehicle> vehiclesList) {
		this.vehiclesList = vehiclesList;
	}
	public int moveNSpaces(Vehicle v, int nSpaces) {
		if (nSpaces > 0) {
			for (int i = 0; i < nSpaces; i++) {
				if (!moveForward(v)) {
					nMoves++;
					moves.add(new Move(v,i+1));
					return i+1;
				}
			}
			return nSpaces;
		} else if (nSpaces < 0) {
			for (int i = 0; i < -1*nSpaces; i++) {
				if (!moveBackward(v)) {
					nMoves++;
					moves.add(new Move(v,-(i+1)));
					return -(i+1);
				}
			}
			return nSpaces;
		} else {
			return 0;
		}
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
						nMoves++;
						moves.add(new Move(v,1));
						return true;
					}
				}
			}
			else if(v.getOrient() == 2)
			{
				int id = v.getId();
				int path = v.getPath();
				int[][] matrix = this.getMatrix();

				for(int i = 0; i < this.size; i++)
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

				nMoves++;
				moves.add(new Move(v,1));
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
						nMoves++;
						moves.add(new Move(v,-1));
						return true;
					}
				}
			}
			else if(v.getOrient() == 2)
			{
				int id = v.getId();

				int path = v.getPath();
				int[][] matrix = this.getMatrix();

				for(int i = 0; i < this.size; i++)
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

				moves.add(new Move(v,-1));
				nMoves++;
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
	@Override
	public String toString() {
		String str = "";
		for(int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				str += this.matrix[i][j];
			}
			str += "\n";
		}
		return str;
	}

	public void setnMoves(int i) {
		// TODO Auto-generated method stub

	}

	// gives the coordinates of the door in (x,y)
	public int[] getDoor(int n) { // n is the side of the board
		int x = n-1;
		int y = (n-1)/2;
//		System.out.println("Door: " + Integer.toString(x) + ", " +Integer.toString(y));
		int[] coord = new int[] {x,y};
		return coord;
	}

	// function receives a vehicle and a Board
	// if v and board's door are overlapping, then player has finished the game.
	public boolean fin(Vehicle v, int[] door) {

		if (v.getOrient() == 1) { //horizontal car
			int[] x_coord = v.getPosition();
			int[] car_coords = {x_coord[1], v.getPath()};
//			System.out.println("car coords: " + Arrays.toString(car_coords));
			if (car_coords[0] == door[0] && car_coords[1] == door[1] ) {
//				System.out.println("Car is touching the door");
				return true;
			}

		} else {
			System.out.println("Error: Primary car must be vertical.");
		}
		return false;
	}

}
