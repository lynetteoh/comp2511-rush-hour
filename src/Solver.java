import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/***
 * The Solver class solves the current board state using A* algorithm to find the optimal path
 * @author gretaritchard
 *
 */
public class Solver  {
    private Board board;
    private SolverMethod solver;

    /***
     * Contructs a new Solver object from a Board
     * @param b : Board
     */
    public Solver (Board b) {
        this.board = b;
        this.solver = new SolverEasy();
    }

    /***
     * Checks if a Board has been solved (ie the red car is in the win state)
     * @param b : Board
     * @return boolean
     */
    private boolean solved(Board b)
    {
    	int [][] matrix = b.getMatrix();
        if(matrix[2][b.getSize()-1] == 1)       // position of door
        {
            return true;
        }
        return false;
    }
    
    /***
     * Solves the Board using A* algorithm, and returns the sequence of moves needed in order to solve.
     * @return ArrayList<Move>
     */
    public ArrayList<Move> solve() {
        PriorityQueue<Board> q = new PriorityQueue<Board>(10, solver);
        HashMap<String, Board> previousMoves = new HashMap<String, Board>();
        q.add(board);
        previousMoves.put(board.toString(), board);

        Board b;
        while (!q.isEmpty()) {
            b = q.remove();
            if (solved(b)) {
            		return new ArrayList<Move>(b.getMoves());
            }

		    for (Vehicle v: b.getVehiclesList()) {
		        	for (int i = 1; i <= b.canMoveForward(v); i++) {
		        		if (b.moveNSpaces(v, i) > 0) {
			                if (!previousMoves.containsKey(b.toString())) {
			                    Board newBoard = new Board(b);
				                    if (solver.compare(b,newBoard) >= 0) {
					                    q.add(newBoard);
					                    previousMoves.put(newBoard.toString(), newBoard);
				                    }
			                }
		        		}
		        		b.undo();
		        		
		        	}
		        	for (int i = 1; i <= b.canMoveBackward(v); i++) {
		        		if (b.moveNSpaces(v, -i) > 0) {
		                if (!previousMoves.containsKey(b.toString())) {
			                    Board newBoard = new Board(b);
			                    if (solver.compare(b,newBoard) >= 0) {
				                    q.add(newBoard);
				                    previousMoves.put(newBoard.toString(), newBoard);
			                    }
		                }
		            }
		            b.undo();
		        	}
		    }
        }
        return null;
    }

}
