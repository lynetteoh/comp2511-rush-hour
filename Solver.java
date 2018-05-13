import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Solver {

	private Board startingBoard;
	private ArrayList<Move> moves;
	
	public Solver(Board starting)
	{
		this.startingBoard = starting;
		this.moves = new ArrayList<Move>();
	}
	
	public boolean Solve()
	{
		Queue<BoardState> queue = new LinkedList<BoardState>();
		
		ArrayList<int[][]> closedSet = new ArrayList<int[][]>();
		
		int moves = 0;
		
		BoardState start = new BoardState(startingBoard, moves, null);
		
		queue.add(start);
		
		while(queue.size() > 0)
		{
			System.out.println("Dequeue");
			BoardState current = queue.poll();
			for(Vehicle v : current.getBoard().getVehiclesList())
				System.out.println(v.getId());
			for(Vehicle v : current.getBoard().getVehiclesList())
			{
				System.out.println("Vehicle: " + v.getId());
				if(current.getBoard().canMoveForward(v) > 0)
				{
					current.getBoard().moveForward(v);
					Board b = current.getBoard();
					BoardState bs = new BoardState(b, moves++, current);
					if(!closedSet.contains(b.getMatrix()))
					{
						closedSet.add(b.getMatrix());
						b.printBoard();
						queue.add(bs);
					}
					current.getBoard().moveBackward(v);
					if(Solved(b.getMatrix()))
						return true;
				}
				if(current.getBoard().canMoveBackward(v) > 0)
				{
					current.getBoard().moveForward(v);
					Board a = current.getBoard();
					BoardState as = new BoardState(a, moves++, current);
					if(!closedSet.contains(a.getMatrix()))
					{
						closedSet.add(a.getMatrix());
						a.printBoard();
						queue.add(as);
					}
					current.getBoard().moveForward(v);
					if(Solved(a.getMatrix()))
						return true;
				}

				
			}
			
		}
		return false;
	}
	
	private boolean Solved(int[][] matrix)
	{
		if(matrix[2][5] == 1)		// position of door
		{
			return true;
		}
		return false;
	}
}
