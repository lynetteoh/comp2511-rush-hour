import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

enum orient {
    HORIZONTAL, VERTICAL
}// just let the horizontal be 1, vertical be 2, and unset be 0
/**
 * A Class which contains 3 Generators: Easy, Medium and Hard, along with their required helper functions.
 * @author rubybie
 */
public class Generator {
	
	// The minumum number of moves required to solve is 3. 
	public final int MIN = 2; 
	// an easy puzzle is solvable from 3 - 5 steps inclusive
	public final int EASY = 5;  
	//a medium puzzle is solvable from 6 steps to 9 steps inclusive
	public final int MEDIUM = 9;  
	// a hard puzzle is solvable from 10 steps up 
	
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
	public Board getSizeextEasyBoard() {
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
	public Board getSizeextMediumBoard() {
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
	public Board getSizeextHardBoard() {
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
	
	/**
	 * Constructs a new Generator Object.
	 * @param size
	 */
	public Generator(int size) {
		this.n = size;
		this.currentEasyBoardIndex = 0;
		this.currentHardBoardIndex = 0;
		this.currentMediumBoardIndex = 0;
	}	
	
	/**
	 * Non-deterministic generator which produces a board that can be solved in 3-5 steps inclusive.
	 * @return An Easy-to-solve board.
	 */
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

	/**
	 * Non-deterministic generator which produces a board that can be solved in 6-9 steps inclusive. 
	 * @return A moderately-hard board.
	 */
	public Board RandomMediumGenerator() { 
		long startTime = System.currentTimeMillis(); // aboart if time taken is too long.

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
				b.placeVehicle(orient, path, position); // no need to check if canPlaceVehicle; board blank.
				j++;
			}

			for (int set = 0; set < 9; set++) { // try to set down car 9 times.
				placeRandCar(b);
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
	
	/**
	 * Non-deterministic generator which produces a board that can be solved in above 10 steps.
	 * @return A hard board.
	 */
	public Board RandomHardGenerator() { 
		long startTime = System.currentTimeMillis();
		Board b = new Board(n); // create new board with dimension n
		int nMoves = 0; // the number of moves required to solve b
		do {
			b.clearVehicles(); b = new Board(n);
			placeFirstVehicleEnd(b); // set down most important car
			
			for (int j = 0; j+2 < n; j++) { // set down vertical vehicles to block first car
				int orient = 2; // vertical
				int path = j+2;
				int length = randomCarLength();
				int[] position = blockPosition(length);
				b.placeVehicle(orient, path, position); // no need to check; board is blank
			}
			// b is solvable 
			
			for (int set = 0; set < 14; set++) { // no. of car-setting attempts
				placeRandCar(b); 
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
	 * Systematically places vehicles row by row when there is free space.
	 * @param Board to which vehicles are added.
	 */
	public void rowCrawler(Board b) { 

		boolean bIsSolvable = true;
		
		for (int r = 0; r < b.getSize(); r++) { // start from first column
			for (int c = 0; c < b.getSize()-2; c++) { 
				
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
	 * Systematically places vehicles column by column when there is free space.
	 * @param Board to which vehicles are added.
	 */
	public void colCrawler(Board b) { 

		boolean bIsSolvable = true;
		
		for (int c = 0; c < b.getSize(); c++) { // start from first column
			for (int r = 0; r < b.getSize()-2; r++) { 
				if (b.getMatrix()[r][c] == 0 && b.getMatrix()[r+1][c] == 0 && b.getMatrix()[r+2][c] == 0) { 
					int position[] = {r, r+1, r+2}; 
					b.placeVehicle(2, c, position); // no need to check
					bIsSolvable = b.solve(); b.clearMoves();
					if (!bIsSolvable) { 
						b.unplaceVehicle(2, c, position);
					}
					continue;
				}
				else if (b.getMatrix()[r][c] == 0 && b.getMatrix()[r+1][c] == 0) { 
					int position[] = {r, r+1}; 
					b.placeVehicle(2, c, position); // no need to check
					bIsSolvable = b.solve(); b.clearMoves();
					if (!bIsSolvable) { 
						b.unplaceVehicle(2, c, position);
					}
					continue;
				}
			}
		}
		return;
	}

	/**
	 * Generates a random number from 10 to 14 inclusive, which is used to set down Easy Boards.
	 * @return Integer from 10 to 14 inclusive
	 */
	public int randNoCarsEasy() { // return a random number of cars to set down
		int k = ThreadLocalRandom.current().nextInt(10, 15);
		return k;
	}
	
	// also tests if it is possible to solve. If not, unplaces the car.
	/**
	 * Places a single vehicle onto the board B, returns true if successfully placed, false otherwise.
	 * After placing, checks if board is still solvable. If not, it unplaces the vehicle.
	 * @param b The board to which this vehicle is added.
	 * @return A boolean indicating whether the car was successfully added or not.
	 */
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
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Places the first car onto the board in the correct row and leaning favourably to the left.
	 * @param b The board to which this vehicle is added.
	 */
	public void placeFirstVehicle(Board b) { 
		int orient = 1; // horizontal
		int path = (b.getSize()-1)/2; // (6-1)/2 = 2; (7-1)/2 = 3 --> all good 
		// we need to generate a randomposition - an int array[]
		// if we have n=6 square
		// lets make it safe and let the car be at: {0,1}, {1,2} or {2,3}
		int index = ThreadLocalRandom.current().nextInt(0, b.getSize()-3);

		int[] position = {index, index+1};
		b.placeVehicle(orient, path, position);
	}
	
	/**
	 * Places the first vehicle strictly on the Left hand side.
	 * @param b The board to which this vehicle is added.
	 */
	public void placeFirstVehicleEnd(Board b) { 
		int orient = 1; // horizontal
		int path = (b.getSize()-1)/2;// (6-1)/2 = 2; (7-1)/2 = 3 --> all good 
		// we need to generate a randomposition - an int array[]
		// if we have n=6 square
		// lets make it safe and let the car be at: {0,1}, {1,2} or {2,3}
		int[] position = {0, 1};
		b.placeVehicle(orient, path, position);
	}
	
	/**
	 * Generates a random number from 0 to n-1 which indicates which path to place the vehicle on.
	 * @param orient Whether the car is horizontal or vertical.
	 * @return Returns the index of the random row/column number for the car to be aligned on. 
	 */
	public int randomCarPath(int orient) {
		int randomNum = 0; 
		if (orient == 1) { // horizontal, cannot place on the centerline
			do { 
				randomNum = ThreadLocalRandom.current().nextInt(0, n);
			} while (randomNum == (n-1)/2);
			
		} else { // h
			randomNum = ThreadLocalRandom.current().nextInt(0, n);
		}

		return randomNum;
	}

	/**
	 * Generates either 2 or 3 randomly to be used as the vehicle length.
	 * @return Randomly 2 or 3.
	 */
	public int randomCarLength() {
		int randomNum = ThreadLocalRandom.current().nextInt(2, 4);
		return randomNum;
	}

	// 
	// 
	/**
	 * Generates a random array of integers for car to lie on.
	 * @param len Length of vehicle, either 2 or 3.
	 * @return An integer array representing position of vehicle.
	 */
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
	/**
	 * Generates randomly from a special set of vertical positions which would block the first car.
	 * @param len The length of the vehicle.
	 * @return an Integer Array represeting position of verticle vehicle.
	 */
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

}
