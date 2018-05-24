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
	//a medium puzzle is solvable from 6 steps to 9 steps inclusive
	public final int MEDIUM = 9;  
	// a hard puzzle is solvable from 9 steps up 
	
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
		Board b = new Board(n); // create new board with dimension n
		int nMoves = 0;
		do { 
			int k = randNoCarsEasy();
			b.clearVehicles();
			b = new Board(n);
			// set down most important car
			placeFirstVehicle(b);
			int set = 0; // the number of cars to try to set down
			while (set < k) {
				placeRandCar(b); set++;
			}
			nMoves = b.getSolution().size();
		} while (!(MIN < nMoves && nMoves <= EASY));
		return b;
	}

	public Board RandomMediumGenerator() { 
		long startTime = System.currentTimeMillis();

		Board b = new Board(n); // create new board with dimension n
		int nMoves = 0; // the number of moves required to solve b
		do {
			b.clearVehicles(); b = new Board(n);
			placeFirstVehicle(b); // set down most important car
			Vehicle First = b.getLastVehicle(); 
			int leftBorder = First.getPosition()[1];
			
			int j = 0; //number of vertical cars set down
			while (leftBorder+1+j < n) { 
				int orient = 2; // vertical
				int path = leftBorder+1+j;
				int length = randomCarLength();
				int[] position = blockPosition(length);
				if (b.canPlaceVehicle(orient, path, position)) { 
					b.placeVehicle(orient, path, position);
				}
				j++;
			}

			int set = 0; // no. of car-setting attempts
			while (set < 9) { // try 10 times 
				placeRandCar(b); set++;
			}
			nMoves = b.getSolution().size();
			if (EASY < nMoves && nMoves <= MEDIUM) break;
			
			rowCrawler(b);
			nMoves = b.getSolution().size();
			if (EASY < nMoves && nMoves <= MEDIUM) break;
			
			colCrawler(b);
			nMoves = b.getSolution().size();
			if (EASY < nMoves && nMoves <= MEDIUM) break;
			
			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime);
			if (duration > 1000) { 
				//System.out.println("takes too long: " + duration);
				break;
			}
		} while (!(EASY < nMoves && nMoves <= MEDIUM));
		//System.out.println("AttemptNo " + AttemptNo);
		//b.printBoard();
		//System.out.println("Solution: " + b.getSolution());
		return b;
	}
	
	// May take over 9000 milliseconds
	/**
	 * New method of placing cars. 
	 * Abandone board early if unsolvable.
	 * @return
	 */
	public Board RandomHardGenerator() { 
		long startTime = System.currentTimeMillis();
		Board b = new Board(n); // create new board with dimension n
		int nMoves = 0; // the number of moves required to solve b
		do {
			b.clearVehicles(); b = new Board(n);
			placeFirstVehicleEnd(b); // set down most important car
			
			int j = 0; //number of vertical cars set down
			while (j+2 < n) { 
				int orient = 2; // vertical
				int path = j+2;
				int length = randomCarLength();
				int[] position = blockPosition(length);
				b.placeVehicle(orient, path, position); // no need to check; board is blank
				j++;
			}
			// b is solvable 
			
			int set = 0; // no. of car-setting attempts
			while (set < 14) {
				placeRandCar(b); set++;
			}
			// b is solvable 

			rowCrawler(b);
			nMoves = b.getSolution().size();
			//System.out.println("AfterCrawlRow " + b.getSolution().size() + " Steps");
			if (MEDIUM < nMoves) break;

			colCrawler(b);
			nMoves = b.getSolution().size();
			//System.out.println("AfterCrawlCol " + b.getSolution().size() + " Steps");
			if (MEDIUM < nMoves) break;
			
			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime);
			if (duration > 1000) break;

		} while (!(MEDIUM < nMoves));
		b.printBoard();
		System.out.println("Solution: " + b.getSolution());
		return b;
	}
	
	

	/**
	 * Systematically goes row by row and places a single car down.
	 * @param b
	 */
	public void rowCrawler(Board b) { 

		boolean bIsSolvable = true;
		
		for (int r = 0; r < b.getN(); r++) { // start from first column
			for (int c = 0; c < b.getN()-2; c++) { 
				
				if (b.getMatrix()[r][c] == 0 && b.getMatrix()[r][c+1] == 0 && b.getMatrix()[r][c+2] == 0) 
				{ 
					// place down a truck 
					int[] position = {c, c+1, c+2};
					b.placeVehicle(1, r, position); // no need to check
					bIsSolvable = b.solve(); b.clearMoves();
					if (!bIsSolvable) { 
						b.unplaceVehicle(1, r, position);
					}
					continue;
				}
				
				else if (b.getMatrix()[r][c] == 0 && b.getMatrix()[r][c+1] == 0) { 
					
					// place down a car 
					int[] position = {c, c+1};
					b.placeVehicle(1, r, position);
					bIsSolvable = b.solve(); b.clearMoves();
					if (!bIsSolvable) { 
						b.unplaceVehicle(1, r, position);
					}
					continue;
				}
			}
		}
		
		return;
		
	}

	/**
	 * Systematically goes row by row and places a single car down.
	 * @param b
	 */
	public void colCrawler(Board b) { 

		boolean bIsSolvable = true;
		
		for (int c = 0; c < b.getN(); c++) { // start from first column
			for (int r = 0; r < b.getN()-2; r++) { 
				if (b.getMatrix()[r][c] == 0 && b.getMatrix()[r+1][c] == 0 && b.getMatrix()[r+2][c] == 0) { 
					int position[] = {r, r+1, r+2}; 
					b.placeVehicle(2, c, position); // no need to check
					//b.printBoard();
					bIsSolvable = b.solve(); b.clearMoves();
					if (!bIsSolvable) { 
						//System.out.println("Not solvable");
						//b.printBoard();
						b.unplaceVehicle(2, c, position);
						//System.out.println("unplaced vehicle now");
						//b.printBoard();
					}
					continue;
				}
				else if (b.getMatrix()[r][c] == 0 && b.getMatrix()[r+1][c] == 0) { 
					int position[] = {r, r+1}; 
					b.placeVehicle(2, c, position); // no need to check
					bIsSolvable = b.solve(); b.clearMoves();
					if (!bIsSolvable) { 
						//System.out.println("Not solvable");
						//b.printBoard();
						b.unplaceVehicle(2, c, position);
						//System.out.println("unplaced vehicle now");
						//b.printBoard();
					}
					continue;
				}
			}
		}
		return;
	}

	
	public int randNoCarsEasy() { // return a random number of cars to set down
		int k = ThreadLocalRandom.current().nextInt(10, 15);
		return k;
	}
	
	// also tests if it is possible to solve. If not, unplaces the car.
	public boolean placeRandCar(Board b) {

		if (b.getSize() != this.n) {
			System.out.println("Generator board size does not match board size");
			return false;
		}
		
		Vehicle v = b.getLastVehicle(); 
		int randOrient = (v.getOrient() == 2) ? 1 : 2; // switch the Orientation
		int randPath = randomCarPath(randOrient);
		int randLen = randomCarLength();
		int position[] = randomPosition(randLen);
		
		if (b.canPlaceVehicle(randOrient, randPath, position)) { 
			b.placeVehicle(randOrient, randPath, position);
			boolean bIsSolvable = b.solve();
			b.clearMoves();
			if (!bIsSolvable) { 
				b.unplaceVehicle(randOrient, randPath, position);
			}
		}
		
		return true;

	}
	
	
	
	public void placeFirstVehicle(Board b) { 
		int orient = 1; // horizontal
		int path = (b.getSize()-1)/2;// (6-1)/2 = 2; (7-1)/2 = 3 --> all good 
		// we need to generate a randomposition - an int array[]
		// if we have n=6 square
		// lets make it safe and let the car be at: {0,1}, {1,2} or {2,3}
		int index = ThreadLocalRandom.current().nextInt(0, b.getN()-3);

		int[] position = {index, index+1};
		b.placeVehicle(orient, path, position);
	}
	
	/**
	 * Places the first vehicle on the Left hand side.
	 * @param b
	 */
	public void placeFirstVehicleEnd(Board b) { 
		int orient = 1; // horizontal
		int path = (b.getN()-1)/2;// (6-1)/2 = 2; (7-1)/2 = 3 --> all good 
		// we need to generate a randomposition - an int array[]
		// if we have n=6 square
		// lets make it safe and let the car be at: {0,1}, {1,2} or {2,3}
		int[] position = {0, 1};
		b.placeVehicle(orient, path, position);
	}
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
	
	// generates a random array of integers for car to lie on.
	// takes in arguments len = length of car; n = dimension of board
	public int[] blockPosition(int len) {
		int position[]; 
		// no matter what, the car must align to block the (n-1)/2 th row. i.e. 2.
		int cars[][] = {{1,2}, {2,3}};
		int trucks[][] = {{0,1,2}, {1,2,3}, {2,3,4}};
		if (len == 2) { // cars 
			position = cars[ThreadLocalRandom.current().nextInt(0, 2)];
		} else { // trucks
			position = trucks[ThreadLocalRandom.current().nextInt(0, 3)];
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
