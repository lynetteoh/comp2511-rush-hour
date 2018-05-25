/***
 * The Move class represents a move on the Board, it stores the vehicle that moved and the direction it moved in.
 * @author gretaritchard
 *
 */
public class Move {
    private Vehicle vehicle;
    private int direction;

    /***
     * Constructs a move object from a vehicle and the direction of the move (positive for forward, negative for backward)
     * @param vehicle : Vehicle
     * @param direction : int
     */
    public Move (Vehicle vehicle, int direction) {
        this.vehicle = vehicle;
        this.direction = direction;
    }
    /***
     * 
     */
    public String toString() {
	    	String str = "";
	    	str += vehicle.getId() + " " + direction;
	    	return str;
    }
    /***
     * Returns the Vehicle object in the Move 
     * @return Vehicle
     */
    public Vehicle getVehicle() {
    		return this.vehicle;
    }
    /***
     * Returns the direction of the Move
     * @return int
     */
    public int getDirection() {
    		return direction;
    }
}
