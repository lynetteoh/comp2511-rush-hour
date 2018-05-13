import java.util.ArrayList;
import java.util.Arrays;
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
		
		BoardState start = new BoardState(startingBoard.getMatrix(), startingBoard.getVehiclesList(), moves, null);
		
		queue.add(start);
		
		while(queue.size() > 0)
		{
			//System.out.println("Dequeue");
			BoardState current = queue.poll();
			//current.getBoard().printBoard();
			
			for(Vehicle v : current.getBoard().getVehiclesList())
			{
				//System.out.println("Vehicle: " + v.getId());
				if(current.getBoard().canMoveForward(v) > 0)
				{
					//System.out.println("Can move forward");
					current.getBoard().moveForward(v);
					Board b = current.getBoard();
					
					//System.out.println("\tPrint board: ");
					//b.printBoard();
					//System.out.println();
					
					int[][] B = matrixCopy(b.getMatrix());
					BoardState bs = new BoardState(B, b.getVehiclesList(), moves++, current);
					
					if(!seenAlready(bs.getBoard().getMatrix(), closedSet))
					{
						closedSet.add(bs.getBoard().getMatrix());
						
						//System.out.println("\t\t\t\tAdd to Queue, Print board: ");
						//b.printBoard();
						
						queue.add(bs);
					}
					current.getBoard().moveBackward(v);
					if(isSolved(b.getMatrix()))
					{
						b.printBoard();
						return true;
					}
						
				}
				if(current.getBoard().canMoveBackward(v) > 0)
				{
					//System.out.println("Can move backward");
					current.getBoard().moveForward(v);
					Board a = current.getBoard();
					
					//System.out.println("\tPrint board: ");
					//a.printBoard();
					//System.out.println();
					int[][] A = matrixCopy(a.getMatrix());
					BoardState as = new BoardState(A, a.getVehiclesList(), moves++, current);
					
					if(!seenAlready(as.getBoard().getMatrix(), closedSet))
					{
						closedSet.add(as.getBoard().getMatrix());
						
						//System.out.println("\t\t\t\tAdd to Queue, Print board: ");
						//a.printBoard();
						
						queue.add(as);
					}
					current.getBoard().moveBackward(v);
					if(isSolved(a.getMatrix()))
					{
						a.printBoard();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean seenAlready(int[][] matrix, ArrayList<int[][]> matrices)
	{
		if(matrices.size() > 0)
		{
			for(int i = 0; i < matrices.size(); i++) {
				for(int j = 0; j < matrices.get(i).length; j++)
				{
					//System.out.println("Closed set size: " + matrices.size());
					if(!Arrays.equals(matrices.get(i)[j], matrix[j]))
					{
						//System.out.println("no match");
						return false;
					}
				}
			}
			return true;
		}
		
		return false;
	}
	
	private int[][] matrixCopy(int[][] original)
	{
		int[][] copy = new int[original.length][original.length];
		for(int i = 0; i < original.length; i++)
		{
			for(int j = 0; j < original.length; j++)
			{
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}
	
	private boolean isSolved(int[][] matrix)
	{
		if(matrix[2][5] == 1)		// position of door
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
}
