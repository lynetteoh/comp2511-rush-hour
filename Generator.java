enum orient {
    HORIZONTAL, VERTICAL 
}// just let the horizontal be 1, vertical be 2, and unset be 0 
	
public class Generator {

	// temorarily not randomised; 
	// but will give a distinct set of 
	// replace with randmoised generator later 

	// sets down matrix values for board 
	// sets down coordinates for each car corresponding to board 

	public void Generator1(int n) {
		Board b = new Board(n);
		// set down most important car 
		int[] position1 = new int[] {4,5};
		Vehicle v1 = new Vehicle(1, 1, 2, position1, 2); 
		b.placeVehicle(v1); // v1 is created; put it on the board 
		
		// set down subsequent cars onto the board 
		
		int[] position2 = new int[] {0,1}; 
		Vehicle v2 = new Vehicle(2, 2, 3, position2, 2); 
		b.placeVehicle(v2); 
		
		
		
		int[] position3 = new int[] {0,1}; 
		Vehicle v3 = new Vehicle(3, 1, 0, position3, 2); 
		b.placeVehicle(v3); 
		
		int[] position4 = new int[] {3,4}; 
		Vehicle v4 = new Vehicle(4, 2, 1, position4, 2); 
		b.placeVehicle(v4); 
		
		int[] position5 = new int[] {1,2,3}; 
		Vehicle v5 = new Vehicle(5, 1, 5, position5, 2); 
		b.placeVehicle(v5); 
		
		int[] position6 = new int[] {3,4}; 
		Vehicle v6 = new Vehicle(6, 1, 4, position6, 2); 
		b.placeVehicle(v6); 
		
		System.out.println("this is board");
		b.printBoard();
		
		int i = v1.canMoveBackward(b.getMatrix());
		System.out.println("Moves backward: " + i);
		
		
		System.out.println("this is board");
		b.printBoard();
		
		int j = v3.canMoveForward(b.getMatrix());
		System.out.println("Moves forward: " + j);
		
		System.out.println("this is board");
		b.printBoard();
		
		
	}


}
