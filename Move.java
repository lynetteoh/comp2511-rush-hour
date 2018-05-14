public class Move {
    private Vehicle v;
    private int direction;
    public Board previousBoard;
    
    public Move (Vehicle v, int direction, Board board) {
        this.v = v;
        this.direction = direction;
        this.previousBoard = board;
    }
    public String toString() {
    	String str = "";
    	str += v.getId() + " " + direction;
    	return str;
    }
}