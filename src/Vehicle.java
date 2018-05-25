import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * The Vehicle class represents a Vehicle on the Board, stores it's unique id, position and orientation.
 * @author gretaritchard
 *
 */
public class Vehicle {
	private int id;
	private int orient;  	// 1 is horizontal, 2 is vertical
	private static AtomicInteger count = new AtomicInteger(0);
	private int path; 		// the int corresponding to the index of col/row
	private int[] position;
	private int length;

	// horizontal, starting block = far left block of the vehicle
	// vertical, starting block = most upward block of the vehicle

	/***
	 * Constructs a new Vehicle object, takes in the orientation, path and position on the board, the id is incremented separately.
	 * @param orient : int (1 == horizontal, 2 == vertical)
	 * @param path : int (the index of the row/column in the matrix it can move on)
	 * @param position : int[] (the position on the path)
	 */
	public Vehicle(int orient, int path, int[] position) {
		// pass in an integer array to tell which spaces they will occupy
		this.id = count.incrementAndGet();
		//this.id = id;
		this.orient = orient;
		this.path = path;
		this.position = position;
		this.length = position.length;
	}
	
	/***
	 * This is the to String method to print out the vehicle id, orientation and length (used for testing)
	 */
	@Override 
	public String toString() { 
		String s = "Id: " + id + "\n";
		s += "Orient " + orient +"| Path:  " + path + "| Position: " + Arrays.toString(position) + "| Length: " + length + "\n";
		return s;
	}
	
	/***
	 * Resets the counter for the ids for the Vehicles (reset for each new Board)
	 * @return boolean
	 */
	public boolean resetCount() {
		count = new AtomicInteger(0);
		return true;
	}
	
	/***
	 * Returns the id of the vehicle
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/***
	 * Returns the orientation of the Vehicle
	 * @return int
	 */
	public int getOrient() {
		return orient;
	}

	/***
	 * Returns the path of the Vehicle
	 * @return int
	 */
	public int getPath() {
		return path;
	}

	/***
	 * Sets the position of the Vehicle
	 * @param pos : int[]
	 */
	public void setPosition(int[] pos)
	{
		this.position = pos;
	}

	/***
	 * Returns the position of the vehicle
	 * @return int[]
	 */
	public int[] getPosition() {
		return position;
	}
	/***
	 * Returns the length of the vehicle
	 * @return int
	 */
	public int getLength()
	{
		return length;
	}
	/***
	 * Returns the adjacent paths of the vehicle, used in generation
	 * @return int[]
	 */
	public int[] getAdjPath() { // get adjacent paths 
		int[] AdjPaths = { position[0] - 1, position[length - 1] + 1 };
		return AdjPaths;
	}


}
