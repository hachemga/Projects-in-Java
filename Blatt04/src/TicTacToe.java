/**
 * This class implements and evaluates game situations of a TicTacToe game.
 */
public class TicTacToe {

    /**
     * Returns an evaluation for player at the current board state.
     * Arbeitet nach dem Prinzip der Alphabeta-Suche. Works with the principle of Alpha-Beta-Pruning.
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
     * @return          rating of game situation from player's point of view
    **/
    public static int alphaBeta(Board board, int player) {
        // TODO
        if (player == 1) {
            return alphaBeta1(board,-Integer.MAX_VALUE, Integer.MAX_VALUE);
        } else {
            return alphaBeta_1(board,-Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
    }

    public static int alphaBeta1(Board board, int alpha, int beta) {
        if (board.isGameWon() || board.nFreeFields() == 0){
            return bewertung(board);
        }

        for (Position pos : board.validMoves()){
            board.doMove(pos, 1);
            int score =  alphaBeta_1(board, alpha, beta);
            board.undoMove(pos);
            if (score > alpha){
                alpha = score;
                if (alpha >= beta) {
                    break;
                }
            }
        }
        return alpha;
    }
    public static int alphaBeta_1(Board board, int alpha, int beta) {
        if ((board.isGameWon()) || (board.nFreeFields() == 0)) {
            return (-1)*bewertung(board);
        }
        for (Position pos : board.validMoves()) {
            board.doMove(pos, -1);
            int score =  alphaBeta1(board, alpha, beta);
            board.undoMove(pos);
            if (score < beta) {
                beta = score;
                if (alpha >= beta) {
                    break;
                }
            }
        }
        return beta;
    }
    public static int bewertung(Board board) {
        if ( board.isGameWon()) {
            return board.nFreeFields() +1;
        } else {
                return 0;
        }
    }

    
    /**
     * Vividly prints a rating for each currently possible move out at System.out.
     * (from player's point of view)
     * Uses Alpha-Beta-Pruning to rate the possible moves.
     * formatting: See "Beispiel 1: Bewertung aller Zugmöglichkeiten" (Aufgabenblatt 4).
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
    **/
    public static void evaluatePossibleMoves(Board board, int player)
    {
        // TODO
        int n1 = board.getN();
        String [][] spielbrett1 = new String[n1][n1];
        for (Position p : board.validMoves()){
            board.doMove(p,player);
            spielbrett1[p.x][p.y] = Integer.toString(alphaBeta(board,player));
            board.undoMove(p);
        }

        for (int i = 0 ;i < n1 ; i++){
            for (int j = 0 ; j < n1 ; j++ ){
                if (board.spielbrett[i][j]==-1) {
                    spielbrett1[i][j]="o";
                }

                if (board.spielbrett[i][j]==1) {
                    spielbrett1[i][j]= "x";
                }
            }
        }
        for (int i = 0 ;i < n1 ; i++) {
            for (int j = 0; j < n1; j++) {
                System.out.print(spielbrett1[i][j]+" ");
            }
            System.out.print ("\n");
        }
    }

    public static void main(String[] args)
    {
    }
}

