import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/***
 * The Board class is responsible for manipulating the matrix and any vehicles within it.
 * @author gretaritchard
 *
 */
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
	public static ArrayList<Board> boards = new ArrayList<Board>();
	private int level;
	private int[][] initialBoard;
	private boolean solvable;


	/***
	 * Board constructor in the Generator class
	 * @param size : int the size of the Board to be created (6 by default)
	 */
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
		this.level = boards.indexOf(this);
		setInitialBoard(this.matrix);
		boards.add(this);
		solvable = false;
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
		boards.add(this);
		this.level = boards.indexOf(this);
		setInitialBoard(this.matrix);
	}
	
	public boolean getSolvable() { 
		return solvable;
	}

	/***
	 * Resets the board to it's initial state
	 * @return matrix : int[][] 	the matrix representation of the initial board
	 */
	public int[][] resetBoard()
	{
		this.nMoves = 0;
		return this.getInitialBoard();
	}
	
	/***
	 * Creates a copy of the given matrix (used to overcome any pass by reference issues)
	 * @param matrix : int[][] the matrix to be copied
	 * @return int[][] 
	 */
	public int[][] copyMatrix(int[][] matrix)
	{
		int[][] newMatrix = new int[matrix.length][matrix.length];
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix.length; j++)
			{
				newMatrix[i][j] = matrix[i][j];
			}
		}
		
		return newMatrix;
	}

	/***
	 * Returns a hint for the next optimal move for the user
	 * @return Move
	 */
	public Move getHint()
	{
		Solver s = new Solver(this);
		ArrayList<Move> solution = s.solve();
		if (solution != null) {
			Move move = solution.get(0);
			System.out.println("Vehicle: " + move.getVehicle().getId() + " move: " + move.getDirection());
			return move;
		}
		return null;
		
	}
	
	/***
	 * Sets the current Board solution using the Solver, or returns false (unsolveable)
	 * @return boolean
	 */
	public boolean solve() {
		Solver s = new Solver(this);
		ArrayList<Move> solution = s.solve();
		if (solution != null) {
			this.solution = solution;
			return true;
		}
		return false;
	}
	

	/***
	 * 
	 */
	public void printBoard() {
		System.out.println("== Board ==");
		for(int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(this.matrix[i][j] + "\t");
			}
			System.out.println("");
		}
	}
	

	/***
	 * Clears the current Board vehicle list for the board
	 * @return boolean
	 */
	public boolean clearVehicles() {
		if (vehiclesList.isEmpty()) {
			return false;
		}
		vehiclesList.get(0).resetCount();
		return true;
	}

	/***
	 * Creates a new vehicle from the data passed in and places the vehicle on the current board
	 * @param orient : int
	 * @param path : int
	 * @param position : int[]
	 */
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
		setInitialBoard(matrix);
		
	}
	
	/**
	 * take back the last vehicle place
	 * @param orient
	 * @param path
	 * @param position
	 */
	public void unplaceVehicle(int orient, int path, int[] position) {

		// set down the given car into matrix
		int p = path;
		int start = position[0];
		int end = position[position.length-1];

		if (orient == 1) { // horizontal; path represents row
			for (int i = start; i <= end; i++) {
				matrix[p][i] = 0;
			}
		}
		else { // vertical; path represents column
			for (int i = start; i <= end; i++) {
				matrix[i][p] = 0;
			}
		}
		vehiclesList.remove(vehiclesList.size()-1);
		
	}
	
	/***
	 * Sets the initial board state
	 * @param matrix : int[][]
	 */
	public void setInitialBoard(int[][] matrix)
	{
		this.initialBoard = copyMatrix(this.matrix);
	}
	/***
	 * Returns the initial board state of the board
	 * @return int[][]
	 */
	public int[][] getInitialBoard()
	{
		return this.initialBoard;
	}

	/***
	 * Returns the Arraylist of Moves that comprises the solution to the board
	 * @return ArrayList<Move>
	 */
	public ArrayList<Move> getSolution() {
		return solution;
	}

	/***
	 * Returns the moves made on the Board
	 * @return Stack<Move>
	 */

	public Stack<Move> getMoves() {
		return moves;
	}

	/***
	 * Clears the stack of Moves stored in the Board
	 */
	public void clearMoves() {
		moves.clear();
	}
	/***
	 * Returns the Board Id
	 * @return int
	 */
	public static int getBoardId() {
		return boardId;
	}

	/***
	 * Sets the Board Id (static counter for the board's ids)
	 * @param boardId : int
	 */
	public static void setBoardId(int boardId) {
		Board.boardId = boardId;
	}

	/***
	 * Returns the id of the current Board
	 * @return int
	 */
	public int getId() {
		return id;
	}
	/***
	 * Returns the number of moves that have been made on the Board
	 * @return int
	 */
	public int getnMoves() {
		return nMoves;
	}

	/***
	 * Sets the id of the Board
	 * @param id : int
	 */
	public void setId(int id) {
		this.id = id;
	}

	/***
	 * Returns the Vehicle Id Counter (the number of vehicles placed so far/id for the next vehicle)
	 * @return int
	 */
	public int getVehicleIdCounter() {
		return vehicleIdCounter;
	}

	/***
	 * Sets the Vehicle Id Counter (the number of vehicles placed so far/id for the next vehicle)
	 * @param vehicleIdCounter : int
	 */
	public void setVehicleIdCounter(int vehicleIdCounter) {
		this.vehicleIdCounter = vehicleIdCounter;
	}

	/***
	 * Returns the Board matrix
	 * @return int[][]
	 */
	public int[][] getMatrix() {
		return matrix;
	}

	/***
	 * Sets the Board matrix
	 * @param matrix : int[][]
	 */
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	/***
	 * Returns the Size of the Board  (width/height)
	 * @return int
	 */
	public int getSize() {
		return size;
	}

	/***
	 * Sets the size of the Board
	 * @param size : int
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/***
	 * Returns the list of Vehicles on the Board
	 * @return ArrayList<Vehicle>
	 */
	public ArrayList<Vehicle> getVehiclesList() {
		return vehiclesList;
	}

	/***
	 * Returns the last placed vehicle on the Board
	 * @return Vehicle
	 */
	public Vehicle getLastVehicle() {
		return vehiclesList.get(vehiclesList.size()-1);
	}

	/***
	 * Sets the list of vehicles on the Board
	 * @param vehiclesList : ArrayList<Vehicle>
	 */
	public void setVehiclesList(ArrayList<Vehicle> vehiclesList) {
		this.vehiclesList = vehiclesList;
	}
	
	/***
	 * Undos the last move taken by the board 
	 * @return Move
	 */
	public Move undo () {
		if(moves.size() < 1) {
			return null;
		}
		Move lastMove = moves.pop();
		if (lastMove == null) {
			return null;
		}
		Vehicle v = lastMove.getVehicle();
		int direction = lastMove.getDirection();

		if (direction > 0) {   // it moved forward hence move backward
			moveNSpaces(v, -direction);
			moves.pop();
		} else if (direction < 0) { // it moved backward hence move forward
			moveNSpaces(v, -1*direction);
			moves.pop();
		} else {
			return null;
		}
		nMoves-= 2;
		return lastMove;
	}
	/***
	 * Moves a Vehicle N spaces forward or backward, returns the amount moved
	 * @param v : Vehicle 
	 * @param nSpaces : int 
	 * @return int 
	 */
	public int moveNSpaces(Vehicle v, int nSpaces) {
		if (nSpaces == 0) {
			return 0;
		} else if (nSpaces > 0) {
			int max = Math.min(this.canMoveForward(v),nSpaces);
			for (int i = 1; i <= max; i++) {
				moveForward(v);
			}
			nMoves++;
			moves.add(new Move(v,max));
			return nSpaces;
		} else {

			int max = Math.min(this.canMoveBackward(v),-nSpaces);
			for (int i = 1; i <= max; i++) {
				this.moveBackward(v);
			}
			nMoves++;
			moves.add(new Move(v,-max));
			return -nSpaces;
		}
	}
	/***
	 * Moves the Vehicle forward one space on the Board (vertical moves down one, and horizontal moves right one)
	 * @param v : Vehicle
	 * @return boolean
	 */
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

				return true;
			}

		}
		return false;
	}

	/***
	 * Moves the Vehicle backward one space on the Board (vertical moves up one, and horizontal moves left one)
	 * @param v : Vehicle
	 * @return boolean
	 */
	public boolean moveBackward(Vehicle v)
	{
		int movesBackwards = canMoveBackward(v);
		if(movesBackwards > 0)
		{
			if(v.getOrient() == 1)
			{ // horizontal
				int[] array = getArray(v);

				for(int i = array.length - 1; i > 0; i--)
				{
					if(array[i] == v.getId() && v.getLength() > 0)
					{
						array[i] = 0;
						array[i - 1] = v.getId();
						array[i - 2] = v.getId();
						if(v.getLength() == 3)
						{
							array[i - 3] = v.getId();
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

				return true;
			}

		}
		return false;
	}

	/***
	 * Checks if a vehicle can be placed in a specified position on the board
	 * @param orient : int
	 * @param path : int
	 * @param position : int[][]
	 * @return boolean
	 */
	public boolean canPlaceVehicle(int orient, int path, int[] position)
	{

		// initial check to make sure all parameters are correct
		if (!(orient == 1 || orient == 2)) {
			System.out.println("Invalid Orient");
			return false;
		} else if (!(0 <= path && path < size)) {
			//System.out.println("Path out of bounds");
			return false;
		} else {
			for (int i = 0; i < position.length; i++) {
				if (!(0 <= position[i] && position[i] < size)) {
					System.out.println("Position index out of bounds");
					return false;
				}
			}
		}

		int[][] matrix = this.matrix;

		if(orient == 1)
		{ // horizontal
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
	/***
	 * Returns the number of spaces a vehicle can move forward on the board
	 * @param v : Vehicle
	 * @return int
	 */
	public int canMoveForward(Vehicle v) {

		int counter = 0;
		int[] array = getArray(v);

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
		return counter;
		// returns max no. of steps the car can move forward
	}

	/***
	 * Returns the number of moves a vehicle can move backward on the board
	 * @param v : Vehicle 
	 * @return int
	 */
	public int canMoveBackward(Vehicle v) {

		int counter = 0;
		int[] array = getArray(v);

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

	/***
	 * Returns the array that the vehicle can move along (ie the path that the Vehicle travels on the Board)
	 * @param v : Vehicle
	 * @return int[]
	 */
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

	/***
	 * Returns the position (x, y) of the Door/Goal position on the board (depending on the Board size)
	 * @return int[]
	 */
	public int[] getGoalPosition() { // n is the side of the board
		int l = size-2; // left cell of goal position
		int r = size-1; // right cell of goal position (cell adjacent to exit)
		int[] coord = new int[] {l,r};
		return coord;
	}

	/***
	 * Checks whether the Vehicle has reached the Goal position on the Board (if the vehicle and the "door" are overlapping, the player has completed the game.
	 * @param v : Vehicle
	 * @return boolean
	 */
	public boolean fin(Vehicle v) {
		if (v.getOrient() == 1 && v.getId() == 1) { //horizontal car
			int[] goalPosition = getGoalPosition(); // the two cells the red car has to occupy (right next to the exit)
			int[][] matrix = this.matrix;
			System.out.println(goalPosition[0] + " , " + goalPosition[1] + " | y_coord: " + v.getPath());
			System.out.println("\t L: " + matrix[goalPosition[0]][v.getPath()] + " | R: " + matrix[goalPosition[1]][v.getPath()]); // it is other numbers for some reason?
			if (matrix[v.getPath()][goalPosition[0]] == 1 && matrix[v.getPath()][goalPosition[1]] == 1) {
				System.out.println("Car is touching the exit");
				return true;
			}
		}
		return false;
	}

}
