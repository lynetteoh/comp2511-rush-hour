import java.util.concurrent.atomic.AtomicInteger;

public class Vehicle {
	private int id;
	private int orient;  	// 1 is horizontal, 2 is vertical
	private static final AtomicInteger count = new AtomicInteger(0); 
	private int path; 		// the int corresponding to the index of col/row
	private int[] position; 
	private int length;

	// horizontal, starting block = far left block of the vehicle
	// vertical, starting block = most upward block of the vehicle
	
	public Vehicle(int orient, int path, int[] position) { 
		// pass in an integer array to tell which spaces they will occupy
		this.id = count.incrementAndGet(); 
		//this.id = id; 
		this.orient = orient; 
		this.path = path;
		this.position = position;
		this.length = position.length;
	}
	
	public int canMoveForward(int[][] matrix) {
		
		int counter = 0;
		int[] array = getArray(matrix);
		
		boolean startCounter = false;
		for(int i = 0; i < array.length - 1; i++)
		{
			if(array[i] == id)
			{
				startCounter = true;
			}
			
			if(startCounter && array[i] == 0)
			{
				counter++;
			}
		}
		return counter - 1; 
		// returns max no. of steps the car can move forward
	}
	
	public int canMoveBackward(int[][] matrix) {
		
		int counter = 0;
		int[] array = getArray(matrix);
		
		boolean startCounter = false;
		for(int i = array.length - 1; i > 0; i--)
		{
			if(array[i] == id)
			{
				startCounter = true;
			}
			
			if(startCounter && array[i] == 0)
			{
				counter++;
			}
		}
		
		return counter + 1;
		// returns max no. of steps the car can move backward
	}
	
	
	public int[] getArray(int[][] matrix)
	{
		int[] array = new int[matrix[0].length];
		
		if(orient == 1)		// horizontal
		{
			array = matrix[path];
		}
		else if(orient == 2)		// vertical
		{ 
		    for(int i = 0; i < array.length; i++){
		       array[i] = matrix[i][path];
		    }
		}
		for (int i = 0; i < array.length; i++) { 
			System.out.print(array[i] + " ");
		}
		System.out.println("");
		return array;
	}

	public int getId() {
		return id;
	}

	public int getOrient() {
		return orient;
	}

	public int getPath() {
		return path;
	}

	public int[] getPosition() {
		return position;
	}
	public int getLength()
	{
		return length;
	}
	
	
}

