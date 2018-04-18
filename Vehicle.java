
public class Vehicle {
	public static int idCounter;
	private int id;
	private enum Orientation {
	    HORIZONTAL, VERTICAL 
	}
	// horizontal, starting block = far left block of the vehicle
	// vertical, starting block = most upward block of the vehicle
	
	public Vehicle() {
		
	}
	
	int forward(int n) {
		// returns max no. of steps the car can move forward
	}
	
	int backward(int n) {
		// returns max no. of steps the car can move backward
	}
}
