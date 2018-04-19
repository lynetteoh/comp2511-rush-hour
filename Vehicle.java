public class Vehicle {
	private int id;
	private int orient;  	// 1 is horizontal, 2 is vertical
	private int path; 		// the int corresponding to the index of col/row
	private int[] position; 

	// horizontal, starting block = far left block of the vehicle
	// vertical, starting block = most upward block of the vehicle
	
	public Vehicle(int id, int orient, int path, int[] position) { // pass in an integer array to tell which spaces they will occupy
		this.id = id; 
		this.orient = orient; 
		this.path = path;
		this.position = position;
	}
	
	public int movesForward(int[][] matrix) {
		
		int counter = 0;
		int[] array = getArray(matrix);
		
		boolean startCounter = false;
		for(int i = 0; i < array.length; i++)
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
		return counter; 
		// returns max no. of steps the car can move forward
	}
	
	public int movesBackward(int[][] matrix) {
		
		int counter = 0;
		int[] array = getArray(matrix);
		
		boolean startCounter = false;
		for(int i = array.length; i > 0; i--)
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
		
		return counter;
		// returns max no. of steps the car can move backward
	}
	
	private int[] getArray(int[][] matrix)
	{
		int[] array = new int[matrix[0].length];
		
		if(orient == 1)		// horizontal
		{
			array = matrix[position[1]];
		}
		else if(orient == 2)		// vertical
		{ 
		    for(int i = 0; i < array.length; i++){
		       array[i] = matrix[i][position[0]];
		    }
		}
		
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
	
	
}

