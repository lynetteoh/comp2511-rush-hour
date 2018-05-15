
public class Move {
    private Vehicle vehicle;
    private int direction;
    public Board previousBoard;

    public Move (Vehicle vehicle, int direction) {
        this.vehicle = vehicle;
        this.direction = direction;
    }
    public String toString() {
    	String str = "";
    	str += vehicle.getId() + " " + direction;
    	return str;
    }
    public Vehicle getVehicle() {
    	return this.vehicle;
    }
    public int getDirection() {
    	return direction;
    }
}
