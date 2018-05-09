import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class Solver {
	private Board board;
	
	public Solver (Board b) {
		this.board = b;
	}
	public class Move {
		private Vehicle v;
		private int direction;
		
		public Move (Vehicle v, int direction) {
			this.v = v;
			this.direction = direction;
		}
	}
	public boolean solution() {

		/*if (car_coords[0] == door[0] && car_coords[1] == door[1] ) { 
			System.out.println("Car is touching the door");
			return true;
		}*/
	}
	public void solve() {
		HashMap<Board, ArrayList<Move>> previousMoves = new HashMap<Board, ArrayList<Move>>();
		Queue<Board> q = new LinkedList<Board>();
		q.add(board);
		ArrayList<Move> moves = new ArrayList<Move>();
		previousMoves.put(board, moves);
		LinkedList<Board> visited = new LinkedList<Board>();
		Board b;
		while (!q.isEmpty()) {
			b = q.remove();
			if (previousMoves.containsKey(b));
				continue;
			}
			
			for (Vehicle v: b.getVehiclesList()) {
				
				if (b.moveForward(v)) {
					ArrayList<Move> m = previousMoves.get(b);
					m.add(new Move(v,1));
					previousMoves.put(b, m);
				}
			}
		}
	}

}
