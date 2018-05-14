
public class SolverEasy implements SolverMethod {
    public int compare(Board b1, Board b2) {
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
        return 0;
    }
}
