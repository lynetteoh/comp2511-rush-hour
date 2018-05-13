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

	public int getId() {
		return id;
	}

	public int getOrient() {
		return orient;
	}

	public int getPath() {
		return path;
	}

	public void setPosition(int[] pos)
	{
		this.position = pos;
	}
	
	public int[] getPosition() {
		return position;
	}
	public int getLength()
	{
		return length;
	}
	
	
}

