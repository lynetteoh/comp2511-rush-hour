
public class SolverEasy implements SolverMethod {
    public int compare(Board b1, Board b2) {
        return b1.getnMoves() - b2.getnMoves();
    }
}
