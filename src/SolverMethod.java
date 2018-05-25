import java.util.Comparator;

/**
* An interface for comparing how easy it is to solve 2 Boards, based on position of red car.
*/
public interface SolverMethod extends Comparator<Board> {
    /**
    * The default heuristic for comparing how easy it is to solve 2 boards.
    * @return an integer indicating is b1 or b2 closer.
    */
    public default int compare(Board b1, Board b2) {
    	int [][] m1 = b1.getMatrix();
    	int [][] m2 = b2.getMatrix();
    	int d1 = 0;
    	int d2 = 0;
    	for (int i = b1.getSize() - 1; i >= 0; i--) {
    		if (m1[2][i] == 1) {
    			d1 = i;
    		}
    		if (m2[2][i] == 1) {
    			d2 = i;
    		}
    	}
        return b1.getnMoves() - b2.getnMoves() + d2 - d1;
    }
}
