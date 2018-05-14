import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


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
        if(matrix[2][5] == 1)       // position of door
        {
            return true;
        }
        if(matrix[2][4] == 1 && matrix[2][5] == 0)
        {
            return true;
        }
        if(matrix[2][3] == 1 && matrix[2][4] == 0 && matrix[2][5] == 0)
        {
            return true;
        }
        return false;
    }
    public String solve() {
        PriorityQueue<Board> q = new PriorityQueue<Board>(10, solver);
        HashMap<String, Move> previousMoves = new HashMap<String, Move>();
        q.add(board);
        previousMoves.put(board.toString(), new Move(null,0,board));
        Board b;
        while (!q.isEmpty()) {
            b = q.remove();


           Move m = previousMoves.get(b.toString());
            if (solved(b)) {
            	ArrayList<Move> moves = new ArrayList<Move>();
            	while (m.previousBoard != board) {
            		moves.add(m);
            		m = previousMoves.get(m.previousBoard.toString());
            	}
            	moves.add(m);
            	for (Move x : moves) {
            		print(x.previousBoard);
            	}
            	
            	b.toString();
            	return b.toString();
            }

            for (Vehicle v: b.getVehiclesList()) {
                if (b.moveForward(v)) {
                    if (previousMoves.containsKey(b.toString()) && board != b) {
                    	b.moveBackward(v);
                    } else {
                    	
	                    Board newBoard = new Board(b);
	                    b.moveBackward(v);
	                    Move newMoves = new Move(v,1, b);
	                    if (solver.compare(b,newBoard) >= 0) {
		                    q.add(newBoard);
		                    previousMoves.put(newBoard.toString(), newMoves);
	                    }
                    }
                } 
                if (b.moveBackward(v)) {
                    if (previousMoves.containsKey(b.toString()) && board != b) {
                    	b.moveForward(v);
                    	continue;
                    } 
                    Board newBoard = new Board(b);
                    b.moveForward(v);
                    Move newMoves = new Move(v,1, b);
                    if (solver.compare(b,newBoard) >= 0) {
	                    q.add(newBoard);
	                    previousMoves.put(newBoard.toString(), newMoves);
                    }
                }
                
            }
        }
        return null;
    }            

    private void print(Object x) {
    	System.out.println(x.toString());
    }


}