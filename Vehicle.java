
public class Vehicle {
	private int id;
	private int orient; 
	private int path; // the int corresponding to the index of col/row
	private int[] position; 

	// horizontal, starting block = far left block of the vehicle
	// vertical, starting block = most upward block of the vehicle
	
	public Vehicle(int id, int orient, int[] occupy) { // pass in an integer array to tell which spaces they will occupy
		this.id = id; 
		this.orient = orient; 
		this.position = position;
	}
	
	int forward(int n) {
		return 0; 
		// returns max no. of steps the car can move forward
	}
	
	int backward(int n) {
		return 0; 
		// returns max no. of steps the car can move backward
	}
}
