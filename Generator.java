import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

enum orient {
    HORIZONTAL, VERTICAL
}// just let the horizontal be 1, vertical be 2, and unset be 0

public class Generator {
	
	// The minumum number of moves required to solve is 3. 
	public final int MIN = 2; 
	// an easy puzzle is solvable from 3 - 5 steps inclusive
	public final int EASY = 5;  
	//a medium puzzle is solvable from 6 steps to 15 steps inclusive
	public final int MEDIUM = 10;  
	// a hard puzzle is solvable from 16 steps up 
	
	private int n;

	private ArrayList<Board> easyBoards = new ArrayList<Board>();
	private ArrayList<Board> mediumBoards = new ArrayList<Board>();
	private ArrayList<Board> hardBoards = new ArrayList<Board>();
	private int currentEasyBoardIndex;
	private int currentMediumBoardIndex;
	private int currentHardBoardIndex;
	
	public Board GetPreviousEasyBoard() {
		if (easyBoards.size()>currentEasyBoardIndex) {
			Board current = easyBoards.get(currentEasyBoardIndex);
			current.resetBoard();			
		}
		if (easyBoards.isEmpty()) {
			return null;
		}
		if (currentEasyBoardIndex > easyBoards.size()) {
			currentEasyBoardIndex = easyBoards.size();
		}
		currentEasyBoardIndex--;
		if (currentEasyBoardIndex < 0) {
			currentEasyBoardIndex = 0;
		} 
		System.out.println("asdfjkal\n\n" + currentEasyBoardIndex);
		return easyBoards.get(currentEasyBoardIndex);
	}
	public Board GetNextEasyBoard() {
		if (easyBoards.size()>currentEasyBoardIndex) {
			Board current = easyBoards.get(currentEasyBoardIndex);
			current.resetBoard();			
		}
		currentEasyBoardIndex++;
		if (currentEasyBoardIndex < easyBoards.size()) {
			return easyBoards.get(currentEasyBoardIndex);
		} 
		if (currentEasyBoardIndex >= easyBoards.size()) {
			currentEasyBoardIndex = easyBoards.size();
			easyBoards.add(RandomEasyGenerator());
		}
		return easyBoards.get(currentEasyBoardIndex);
	}
	public Board GetPreviousMediumBoard() {
		if (mediumBoards.size()>currentMediumBoardIndex) {
			Board current = mediumBoards.get(currentMediumBoardIndex);
			current.resetBoard();			
		}
		if (mediumBoards.isEmpty()) {
			return null;
		}
		if (currentMediumBoardIndex > mediumBoards.size()) {
			currentMediumBoardIndex = mediumBoards.size();
		}
		currentMediumBoardIndex--;
		if (currentMediumBoardIndex < 0) {
			currentMediumBoardIndex = 0;
		} 
		return mediumBoards.get(currentMediumBoardIndex);
	}
	public Board GetNextMediumBoard() {
		if (mediumBoards.size()>currentMediumBoardIndex) {
			Board current = mediumBoards.get(currentMediumBoardIndex);
			current.resetBoard();			
		}
		currentMediumBoardIndex++;
		if (currentMediumBoardIndex < mediumBoards.size()) {
			return mediumBoards.get(currentMediumBoardIndex);
		} 
		if (currentMediumBoardIndex >= mediumBoards.size()) {
			currentMediumBoardIndex = mediumBoards.size();
			mediumBoards.add(RandomMediumGenerator());
		}	
		return mediumBoards.get(currentMediumBoardIndex);
	}
	public Board GetPreviousHardBoard() {
		if (hardBoards.size()>currentHardBoardIndex) {
			Board current = hardBoards.get(currentHardBoardIndex);
			current.resetBoard();			
		}
		if (hardBoards.isEmpty()) {
			return null;
		}
		if (currentHardBoardIndex > hardBoards.size()) {
			currentHardBoardIndex = hardBoards.size();
		}
		currentHardBoardIndex--;
		if (currentHardBoardIndex <= 0) {
			currentHardBoardIndex = 0;
		} 
		return hardBoards.get(currentHardBoardIndex);
	}
	public Board GetNextHardBoard() {
		if (hardBoards.size()>currentHardBoardIndex) {
			Board current = hardBoards.get(currentHardBoardIndex);
			current.resetBoard();			
		}
		currentHardBoardIndex++;
		if (currentHardBoardIndex < hardBoards.size()) {
			return hardBoards.get(currentHardBoardIndex);
		} 
		if (currentHardBoardIndex >= hardBoards.size()) {
			currentHardBoardIndex = hardBoards.size();
			hardBoards.add(RandomHardGenerator());
		}
		return hardBoards.get(currentHardBoardIndex);
	}
	
	
	public Generator(int size) {
		this.n = size;
		this.currentEasyBoardIndex = 0;
		this.currentHardBoardIndex = 0;
		this.currentMediumBoardIndex = 0;
	}	
	// Ok generator : can produce a board in 1-5 Attempts
	public Board RandomEasyGenerator() { 
		int AttemptNo = 0; 
		Board b = new Board(n); // create new board with dimension n
		int nMoves = 0;
		do {
			System.out.println("Easy Generator 4");
			int k = randNoCarsEasy();
			System.out.println("The number of cars to TRY to set down: " +k);
			b.clearVehicles();
			b = new Board(n);
			// set down most important car
			placeFirstVehicle(b);
			int set = 0; // the number of cars set down
			while (set < k) {
				placeRandCar(b);
				set++;
			}
			b.printBoard();
			System.out.println("Random Board:");
			b.printBoard();
			
			b.solve();
			nMoves = b.getSolution().size();
			System.out.println("This puzzle is solvable in" + b.getSolution().size() + "Steps");
			AttemptNo++;
		} while (!b.solve() || !(MIN < nMoves && nMoves <= EASY));
		System.out.println("AttemptNo " + AttemptNo);
		return b;
	}

	// ok Medium Generator : can produce a board in 1 - 20 Attempts
	public Board RandomMediumGenerator() { 
		int AttemptNo = 0; 
		Board b = new Board(n); // create new board with dimension n
		int nMoves = 0;
		do {
			System.out.println("Medium Generator 4");
			int k = randNoCarsMedium();
			System.out.println("The number of cars to TRY to set down: " + k);
			b.clearVehicles();
			b = new Board(n);
			// set down most important car
			placeFirstVehicle(b);
			int set = 0; // the number of cars set down
			while (set < k) { // try k times 
				placeRandCar2(b);
				set++;
			}
			b.printBoard();
			System.out.println("Random Board:");
			b.printBoard();
			
			b.solve();
			nMoves = b.getSolution().size();
			System.out.println("This puzzle is solvable in" + b.getSolution().size() + "Steps");
			AttemptNo++;
		} while (!b.solve() || !(EASY < nMoves && nMoves <= MEDIUM));
		System.out.println("AttemptNo " + AttemptNo);
		return b;
	}

	// Crappy Hard Generator: may take over 100 steps
	public Board RandomHardGenerator() { 
		int AttemptNo = 0; 
		Board b = new Board(n); // create new board with dimension n
		int nMoves = 0;
		do {
			System.out.println("Hard Generator 4");
			int k = randNoCarsHard();
			System.out.println("The number of cars to TRY to set down: " + k);
			b.clearVehicles();
			b = new Board(n);
			// set down most important car
			placeFirstVehicle(b);
			int set = 0; // the number of cars set down
			while (set < k) { // try k times 
				placeRandCar2(b);
				set++;
			}
			b.printBoard();
			System.out.println("Random Board:");
			b.printBoard();
			
			b.solve();
			nMoves = b.getSolution().size();
			System.out.println("This puzzle is solvable in" + b.getSolution().size() + "Steps");
			AttemptNo++;
		} while (!b.solve() || !(MEDIUM < nMoves));
		System.out.println("AttemptNo " + AttemptNo);
		return b;
	}

	
	public int randNoCarsEasy() { // return a random number of cars to set down
		int k = ThreadLocalRandom.current().nextInt(10, 15);
		return k;
	}
	
	// also used for hard
	public int randNoCarsMedium() { // return a random number of cars to set down
		int k = ThreadLocalRandom.current().nextInt(12, 17);
		return k;
	}
	
	public int randNoCarsHard() { // return a random number of cars to set down
		int k = ThreadLocalRandom.current().nextInt(17, 25);
		return k;
	}
	
	// Calls the random-number functions below to generate a random car
	public boolean placeRandCar(Board b) {
		if (b.getSize() != this.n) {
			System.out.println("Generator board size does not match board size");
			return false;
		}
		int randOrient = randomCarOrient();
		int randPath = randomCarPath(randOrient);
		int randLen = randomCarLength();
		int position[] = randomPosition(randLen);

		if (b.canPlaceVehicle(randOrient, randPath, position)) { 
			// check if putting down car is going to be allowed
			b.placeVehicle(randOrient, randPath, position);
			return true;
		}
		return false;
	}
	
	
	public void placeFirstVehicle(Board b) { 
		int orient = 1; // horizontal
		int path = (b.getSize()-1)/2;// (6-1)/2 = 2; (7-1)/2 = 3 --> all good 
		// we need to generate a randomposition - an int array[]
		// if we have n=6 squares, the indices may range from {0,1} to {3,4}
		// but not {4,5}. Hence, we want the start to be 3, or n-3.
		// int index = ThreadLocalRandom.current().nextInt(0, b.getN()-2);
		// but maybe lets make it safe and go from {0,1} to {2,3}
		int index = ThreadLocalRandom.current().nextInt(0, b.getSize()-3);
		int[] position = {index, index+1};
		b.placeVehicle(orient, path, position);
	}
	
	// note that we should only be able to place the red car has a horizontal on the door path. 
	// hence if 

	// randomly generates 1 or 2 as ints (horizontal or vertical)
	public int randomCarOrient() {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 3);
//		System.out.println("The random Orient is: " + Integer.toString(randomNum));
		return randomNum;
	}

	// returns the index of the random row/column number for the car to be aligned on
	public int randomCarPath(int orient) {
		int randomNum = 0; 
		if (orient == 1) { // horizontal, cannot place on the centerline
			do { 
				randomNum = ThreadLocalRandom.current().nextInt(0, n);
			} while (randomNum == (n-1)/2);
			
		} else { // h
			randomNum = ThreadLocalRandom.current().nextInt(0, n);
		}
		
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
	public int[] randomPosition(int len) {
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
	public int[] getDoor() { // n is the side of the board
		int x = n-1;
		int y = (n-1)/2;
//		System.out.println("Door: " + Integer.toString(x) + ", " +Integer.toString(y));
		int[] coord = new int[] {x,y};
		return coord;
	}

	// function receives a vehicle and a Board
	// if v and board's door are overlapping, then player has finished the game.
	
	
	public int getRandomArray(int[] array) {
	    int rnd = new Random().nextInt(array.length);
	    return array[rnd];
	}
	
	// Based on the previous car, places vehicles.
	public boolean placeRandCar2(Board b) {

		if (b.getSize() != this.n) {
			System.out.println("Generator board size does not match board size");
			return false;
		}
		Vehicle v = b.getLastVehicle(); 
		System.out.println("The previous car: " + v);
		// based on the last vehicle placed on the board, place the next vechicle
		System.out.println("Initial Board: ");
		b.printBoard();
		
		int randOrient = (v.getOrient() == 2) ? 1 : 2; // switch the Orientation
		// we want to path to directly block v. 
		// e.g. if v is horizontal, path=2, position = {0,1}: 
		// we want w to be vertical, path = 2
		// this 2 is one more than {0,1}. 
		// hence get the position array 
		// be one of less/more 
		int[] possiblePaths = v.getAdjPath();
		System.out.println("Possible Path " + Arrays.toString(possiblePaths));
		if (randOrient == 1) { // we cannot be on a possible path of (n-1)/2
			if (possiblePaths[0] == (b.getSize()-1)/2) possiblePaths[0] = -1;
			if (possiblePaths[1] == (b.getSize()-1)/2) possiblePaths[1] = -1;
		}
		int randLen = randomCarLength();
		int position[] = randomPosition(randLen);
		
		if (b.canPlaceVehicle(randOrient, possiblePaths[0], position)) { // place in first paht
			b.placeVehicle(randOrient, possiblePaths[0], position);
			return true;
		}
		else if (b.canPlaceVehicle(randOrient, possiblePaths[1], position)){ 
			b.placeVehicle(randOrient, possiblePaths[1], position);
			return true;
		}
		else { // try anywhere else 
			int randPath = randomCarPath(randOrient);
			if (b.canPlaceVehicle(randOrient, randPath, position)) { 
				b.placeVehicle(randOrient, randPath, position);
				return true;
			}
			return false;
		}
	}
	
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
