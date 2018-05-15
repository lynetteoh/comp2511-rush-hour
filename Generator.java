import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

enum orient {
    HORIZONTAL, VERTICAL
}// just let the horizontal be 1, vertical be 2, and unset be 0

public class Generator {

	public Generator() {
	}


	// Generates a board that will place cars down randomly.
	// The red car will always be on the correct path

	public Board RandomGenerator1(int n) {

		Board b = new Board(n);
		do {
			b.clearVehicles();
			b = new Board(n);
			// set down most important car
			b.placeVehicle(1,2, new int[] {0,1}); // v1 is created; put it on the board
			int k = 4; // number of random cars to set down
			int set = 0; // the number of cars set down
			while (set < k) {
				if (placeRandCar(b)) {
					set++;
				}
			}
			b.printBoard();
			System.out.println("Random Board:");

			b.printBoard();
		} while (!b.solve());


		return b;
	}
	
	public Board DetGenerator1(int n) { 
		Board b = new Board (n);
		do {
			b.clearVehicles();
			b = new Board(n);
			// set down most important car
			
			b.placeVehicle(1, 2, new int[] {2,3}); // v1 is created; put it on the board 
			b.placeVehicle(2, 3, new int[] {0,1}); // v2
			b.placeVehicle(1, 0, new int[] {0,1});  // v3
			b.placeVehicle(2, 1, new int[] {3,4}); // v4 
			b.placeVehicle(1, 5, new int[] {1,2,3});  //v5
			b.placeVehicle(1, 4, new int[] {3,4}); // v6
			b.placeVehicle(2, 5, new int[] {0,1,2});  //v7
			
			System.out.println("Deterministic Generator 1:");
			b.printBoard();

			b.printBoard();
		} while (!b.solve());
		return b;
	}

	// calls the functoins below to generate a random car
	public boolean placeRandCar(Board b) {
		int randOrient = randomCarOrient();
		int randPath = randomCarPath(b.getN());
		int randLen = randomCarLength();
		int position[] = randomPosition(b.getN(), randLen);

		if (b.canPlaceVehicle(randOrient, randPath, position)) {
			b.placeVehicle(randOrient, randPath, position);
			return true;
		}
		// check if putting down car is going to be allowed
		return false;
	}

	// randomly generates 1 or 2 as ints (horizontal or vertical)
	public int randomCarOrient() {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 3);
//		System.out.println("The random Orient is: " + Integer.toString(randomNum));
		return randomNum;
	}

	// returns the index of the random row/column number for the car to be aligned on
	public int randomCarPath(int n) {
		int randomNum = ThreadLocalRandom.current().nextInt(0, n);
//		System.out.println("The random Path is: " + Integer.toString(randomNum));
		return randomNum;
	}

	// randomly generates 2 or 3 as ints.
	public int randomCarLength() {
		int randomNum = ThreadLocalRandom.current().nextInt(2, 4);
//		System.out.println("The random Length is: " + Integer.toString(randomNum));
		return randomNum;
	}

	// generates a random array of integers for car to lie on.
	// takes in arguments len = length of car; n = dimension of board
	public int[] randomPosition(int n, int len) {
		int[] position = new int[len];
		int i = 0;
		int rangeIndex = n-len;
//		System.out.println("beginIndex: " + Integer.toString(rangeIndex));
		// e.g. n = 6; len = 2, so you are allowed to have beginIndex from 0..4
		int beginIndex = ThreadLocalRandom.current().nextInt(0, rangeIndex+1);
		for (i = 0; i < len; i ++) {
			position[i] = beginIndex;
			beginIndex++;
		}
//		System.out.println("position: " + Arrays.toString(position));
		return position;
	}

	// gives the coordinates of the door in (x,y)
	public int[] getDoor(int n) { // n is the side of the board
		int x = n-1;
		int y = (n-1)/2;
//		System.out.println("Door: " + Integer.toString(x) + ", " +Integer.toString(y));
		int[] coord = new int[] {x,y};
		return coord;
	}

	// function receives a vehicle and a Board
	// if v and board's door are overlapping, then player has finished the game.
	public boolean fin(Vehicle v, int[] door) {

		if (v.getOrient() == 1) { //horizontal car
			int[] x_coord = v.getPosition();
			int[] car_coords = {x_coord[1], v.getPath()};
//			System.out.println("car coords: " + Arrays.toString(car_coords));
			if (car_coords[0] == door[0] && car_coords[1] == door[1] ) {
//				System.out.println("Car is touching the door");
				return true;
			}

		} else {
			System.out.println("Error: Primary car must be vertical.");
		}
		return false;
	}
}
