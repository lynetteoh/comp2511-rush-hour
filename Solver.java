
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Solver  {
    private Board board;
    private SolverMethod solver;
    
    public Solver (Board b) {
        this.board = b;
        this.solver = new SolverEasy();
    }

    private boolean solved(Board b)
    {
    	int [][] matrix = b.getMatrix();
        if(matrix[2][b.getSize()-1] == 1)       // position of door
        {
            return true;
        }
        return false;
    }
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
                if (b.moveForward(v)) {
                    if (!previousMoves.containsKey(b.toString())) {
	                    Board newBoard = new Board(b);
	                    newBoard.setnMoves(b.getnMoves() + 1);
	                    if (solver.compare(b,newBoard) >= 0) {
		                    q.add(newBoard);
		                    previousMoves.put(newBoard.toString(), newBoard);
	                    }
                    }
                    b.undo();
                } 
                if (b.moveBackward(v)) {
                    if (!previousMoves.containsKey(b.toString())) {
	                    Board newBoard = new Board(b);
	                    newBoard.setnMoves(b.getnMoves() + 1);
	                    if (solver.compare(b,newBoard) >= 0) {
		                    q.add(newBoard);
		                    previousMoves.put(newBoard.toString(), newBoard);
	                    }
                    }
                    b.undo();
                }
                
            }
        }
        return null;
    }            

    private void print(Object x) {
    	System.out.println(x.toString());
    }


}
