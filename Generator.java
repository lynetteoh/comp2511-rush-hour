import java.util.Arrays;

enum orient {
    HORIZONTAL, VERTICAL 
}// just let the horizontal be 1, vertical be 2, and unset be 0 
	
public class Generator {
	
	public Generator() { 
		//System.out.println("Generator Constructor");
	}

	// temorarily not randomised; 
	// but will give a distinct set of 
	// replace with randmoised generator later 

	// sets down matrix values for board 
	// sets down coordinates for each car corresponding to board 

	public void Generator1(int n) {
		Board b = new Board(n);
		
		// set down most important car 
		int[] position1 = new int[] {0,1};
		Vehicle v1 = new Vehicle(1, 2, position1); 
		b.placeVehicle(v1); // v1 is created; put it on the board 
		
		// set down subsequent cars onto the board 
		int[] position2 = new int[] {0,1}; 
		Vehicle v2 = new Vehicle(2, 3, position2); 
		b.placeVehicle(v2); 
		
		int[] position3 = new int[] {0,1}; 
		Vehicle v3 = new Vehicle(1, 0, position3); 
		b.placeVehicle(v3); 
		
		int[] position4 = new int[] {3,4}; 
		Vehicle v4 = new Vehicle(2, 1, position4); 
		b.placeVehicle(v4); 
		
		int[] position5 = new int[] {3,4,5}; 
		Vehicle v5 = new Vehicle(1, 5, position5); 
		b.placeVehicle(v5); 
		
		int[] position6 = new int[] {3,4}; 
		Vehicle v6 = new Vehicle(1, 4, position6); 
		b.placeVehicle(v6); 
		
		int[] position7 = new int[] {2,3,4}; 
		Vehicle v7 = new Vehicle(2, 5, position7); 
		b.placeVehicle(v7); 
		
		System.out.println("Board:");
		b.printBoard();
		
		System.out.println("can place vehicle at path: 1, pos: [3,4] = " + b.canPlaceVehicle(1, 1, new int[] {3, 4}));
		System.out.println("can place vehicle at path: 1, pos: [2,3] = " + b.canPlaceVehicle(1, 1, new int[] {2,3}));
		System.out.println("can place vehicle at path: 1, pos: [1,2] = " + b.canPlaceVehicle(1, 1, new int[] {1,2}));
		
		
		System.out.println("Move 5 backwards = " + b.moveBackward(v5));
		b.printBoard();
		
		System.out.println("Move 5 backwards = " + b.moveBackward(v5));
		b.printBoard();
		
		System.out.println("Move 5 backwards = " + b.moveBackward(v5));
		b.printBoard();
		
		System.out.println("Move 7 forwards = " + b.moveForward(v7));
		b.printBoard();
		
		System.out.println("Move 7 forwards = " + b.moveForward(v7));
		b.printBoard();
		
		System.out.println("Move 6 backwards = " + b.moveBackward(v6));
		b.printBoard();
		
		System.out.println("Move 6 backwards = " + b.moveBackward(v6));
		b.printBoard();
		
//		int i = v1.canMoveBackward(b.getMatrix());
//		System.out.println("Vehicle 1 canMoveBackward: " + i);
//		
//		System.out.println("Board: ");
//		b.printBoard();
//		
//		int j = v3.canMoveForward(b.getMatrix());
//		System.out.println("Vehicle 3 canMoveforward: " + j);
//		
//		System.out.println("this is board");
//		b.printBoard();
		getDoor(6);
		
	}
	
	public void Generator2(int n) {
		Board b = new Board(n);
		
		// set down most important car 
		int[] position1 = new int[] {2,3};
		Vehicle v1 = new Vehicle(1, 2, position1); 
		b.placeVehicle(v1); // v1 is created; put it on the board 
		
		// set down subsequent cars onto the board 
		int[] position2 = new int[] {0,1}; 
		Vehicle v2 = new Vehicle(1, 1, position2); 
		b.placeVehicle(v2); 
		
		int[] position3 = new int[] {0,1}; 
		Vehicle v3 = new Vehicle(2, 2, position3); 
		b.placeVehicle(v3); 
		
		int[] position4 = new int[] {3,4,5}; 
		Vehicle v4 = new Vehicle(1, 0, position4); 
		b.placeVehicle(v4); 
		
		int[] position5 = new int[] {3,4}; 
		Vehicle v5 = new Vehicle(1, 1, position5); 
		b.placeVehicle(v5); 
		
		int[] position6 = new int[] {1,2}; 
		Vehicle v6 = new Vehicle(2, 5, position6); 
		b.placeVehicle(v6); 
		
		int[] position7 = new int[] {2,3,4}; 
		Vehicle v7 = new Vehicle(2, 1, position7); 
		b.placeVehicle(v7); 

		int[] position8 = new int[] {0,1,2}; 
		Vehicle v8 = new Vehicle(1, 5, position8); 
		b.placeVehicle(v8); 
		
		int[] position9 = new int[] {3,4,5}; 
		Vehicle v9 = new Vehicle(2, 3, position9); 
		b.placeVehicle(v9); 
		
		int[] position10 = new int[] {4,5}; 
		Vehicle v10 = new Vehicle(1, 3, position10); 
		b.placeVehicle(v10); 
		
		int[] position11 = new int[] {4,5}; 
		Vehicle v11 = new Vehicle(2, 4, position11); 
		b.placeVehicle(v11); 
		
		System.out.println("Board:");
		b.printBoard();
		
	}
	
	public void Generator3(int n) {
		Board b = new Board(n);
		
		// set down most important car 
		int[] position1 = new int[] {2,3};
		Vehicle v1 = new Vehicle(1, 2, position1); 
		b.placeVehicle(v1); // v1 is created; put it on the board 
		
		// set down subsequent cars onto the board 
		int[] position2 = new int[] {0,1,2}; 
		Vehicle v2 = new Vehicle(1, 0, position2); 
		b.placeVehicle(v2); 
		
		int[] position3 = new int[] {1,2}; 
		Vehicle v3 = new Vehicle(2, 0, position3); 
		b.placeVehicle(v3); 
		
		int[] position4 = new int[] {0,1}; 
		Vehicle v4 = new Vehicle(2, 3, position4); 
		b.placeVehicle(v4); 
		
		int[] position5 = new int[] {4,5}; 
		Vehicle v5 = new Vehicle(1, 1, position5); 
		b.placeVehicle(v5); 
		
		int[] position6 = new int[] {2,3}; 
		Vehicle v6 = new Vehicle(2, 4, position6); 
		b.placeVehicle(v6); 
		
		int[] position7 = new int[] {2,3}; 
		Vehicle v7 = new Vehicle(2, 5, position7); 
		b.placeVehicle(v7); 

		int[] position8 = new int[] {4,5}; 
		Vehicle v8 = new Vehicle(2, 4, position8); 
		b.placeVehicle(v8); 
		
		int[] position9 = new int[] {4,5}; 
		Vehicle v9 = new Vehicle(2, 5, position9); 
		b.placeVehicle(v9); 
		
		int[] position10 = new int[] {0,1}; 
		Vehicle v10 = new Vehicle(1, 5, position10); 
		b.placeVehicle(v10); 
		
		int[] position11 = new int[] {2,3}; 
		Vehicle v11 = new Vehicle(1, 5, position11); 
		b.placeVehicle(v11); 
		
		System.out.println("Board:");
		b.printBoard();
		
	}
	
	// Create a Board Backwards 
	// the Reverse Generator for Board 1 
	// Initially generates a solvable premise, then backtracks to 1. 
	public void GeneratorA1(int n, int m) {
		// n is the dimension of board 
		// m is how many moves backwards to move 
		Board b = new Board(n);

		// set down most important car 
		int[] door = getDoor(6);
		// we can say that the player has completed once the puzzle 
		// once a car's coordinate touches the door's coordinate 
		int[] position1 = new int[] {4,5};
		Vehicle v1 = new Vehicle(1, 2, position1); 
		b.placeVehicle(v1); // v1 is created; put it on the board 
		fin(v1, door);
		
		// set down subsequent cars onto the board 
		int[] position2 = new int[] {0,1}; 
		Vehicle v2 = new Vehicle(2, 3, position2); 
		b.placeVehicle(v2); 
		
		int[] position3 = new int[] {0,1}; 
		Vehicle v3 = new Vehicle(1, 0, position3); 
		b.placeVehicle(v3); 
		
		int[] position4 = new int[] {3,4}; 
		Vehicle v4 = new Vehicle(2, 1, position4); 
		b.placeVehicle(v4); 
		
		int[] position5 = new int[] {1,2,3}; 
		Vehicle v5 = new Vehicle(1, 5, position5); 
		b.placeVehicle(v5); 
		
		int[] position6 = new int[] {3,4}; 
		Vehicle v6 = new Vehicle(1, 4, position6); 
		b.placeVehicle(v6); 
		
		int[] position7 = new int[] {3,4,5}; 
		Vehicle v7 = new Vehicle(2, 5, position7); 
		b.placeVehicle(v7); 
		
		System.out.println("Board:");
		b.printBoard();
		
		
	}
	
	// gives the coordinates of the door in (x,y)
	public int[] getDoor(int n) { // n is the side of the board
		int x = n-1; 
		int y = (n-1)/2;
		System.out.println("Door: " + Integer.toString(x) + ", " +Integer.toString(y));
		int[] coord = new int[] {x,y}; 
		return coord;
	}
	
	// function receives a vehicle and a Board
	// if v and board's door are overlapping, then player has finished the game.
	public boolean fin(Vehicle v, int[] door) { 
		
		if (v.getOrient() == 1) { //horizontal car
			int[] x_coord = v.getPosition(); 
			int[] car_coords = {x_coord[1], v.getPath()};
			System.out.println("car coords: " + Arrays.toString(car_coords));
			if (car_coords[0] == door[0] && car_coords[1] == door[1] ) { 
				System.out.println("Car is touching the door");
				return true;
			}
			
		} else { 
			System.out.println("Error: Primary car must be vertical.");
		}
		return false;
	}


}
