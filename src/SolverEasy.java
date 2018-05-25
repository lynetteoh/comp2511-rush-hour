/**
* A Class which implements solver method. Compares 2 boards based on many moves it takes to solve the boards.
*/
public class SolverEasy implements SolverMethod {
    public int compare(Board b1, Board b2) {
        return b1.getnMoves() - b2.getnMoves();
    }
}
