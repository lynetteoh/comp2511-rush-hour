import java.util.ArrayList;

public class BoardState {

//	private Board board;
	private int nMoves;
	private BoardState previousState;
	private Move move;
	//private ArrayList<Vehicle> vehicles;

	public BoardState(int[][] matrix, ArrayList<Vehicle> vehicles, int nMoves, Move move, BoardState prev)//, ArrayList<Vehicle> vehicles)
	{
//		this.board = new Board(matrix, vehicles, 6);
		this.nMoves = nMoves;
		this.move = move;
		this.previousState = prev;
		//this.vehicles = vehicles;
	}

//	public Board getBoard() {
//		return board;
//	}

	public int getnMoves() {
		return nMoves;
	}
	public Move getMoves() {
		return move;
	}

	public BoardState getPreviousState() {
		return previousState;
	}
	
}
