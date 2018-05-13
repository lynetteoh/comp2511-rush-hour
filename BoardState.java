import java.util.ArrayList;

public class BoardState {

	private Board board;
	private int moves;
	private BoardState previousState;
	//private ArrayList<Vehicle> vehicles;

	public BoardState(int[][] matrix, ArrayList<Vehicle> vehicles, int moves, BoardState prev)//, ArrayList<Vehicle> vehicles)
	{
		this.board = new Board(matrix, vehicles, 6);
		this.moves = moves;
		this.previousState = prev;
		//this.vehicles = vehicles;
	}

	public Board getBoard() {
		return board;
	}

	public int getMoves() {
		return moves;
	}

	public BoardState getPreviousState() {
		return previousState;
	}
	
}
