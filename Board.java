import java.util.ArrayList;

public class Board {
	// int[][] b = new int[6][6]
	public static int boardId = 1;
	private int id;
	private int vehicleIdCounter; 
	private int[][] matrix;
	private int n;
	private ArrayList<Vehicle> vehiclesList = new ArrayList<Vehicle>();
	
	public Board(int n) {
		
		this.n = n;
		this.matrix = new int[n][n];
		for(int i=0;i<n;i++) {
			for (int j=0;j<n;j++) {
				this.matrix[i][j] = 0;
			}
		}
		this.id = boardId;
		boardId += 1;
	}
	
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		Board b = new Board(6);
		b.printBoard();
	}
	
	public void printBoard() {
		for(int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(this.matrix[i][j] + " \t");
			}
			System.out.println("");
		}
	}
	
	public boolean moveForward(Vehicle v)
	{
		int movesForwards = v.canMoveForward(this.matrix); 
		if(movesForwards > 0)
		{
			int[] array = v.getArray(this.matrix);
			
			for(int i = 0; i < array.length; i++)
			{
				if(array[i] == id && v.getLength() > 0)
				{
					array[i] = 0;
					for(int j = 1; j <= v.getLength(); j++)
					{
						array[i + j] = v.getId();
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean moveBackward(Vehicle v)
	{
		int movesBackwards = v.canMoveBackward(this.matrix); 
		if(movesBackwards > 0)
		{
			int[] array = v.getArray(this.matrix);
			
			for(int i = array.length; i > 0; i--)
			{
				if(array[i] == id && v.getLength() > 0)
				{
					array[i] = 0;
					for(int j = 1; j <= v.getLength(); j++)
					{
						array[i + j] = v.getId();
					}
					return true;
				}
			}
		}
		return false;
	}

}
