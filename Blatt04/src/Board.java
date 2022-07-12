import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Stack;

import static java.lang.Math.abs;
/**
 * This class represents a generic TicTacToe game board.
 */
public class Board {
    private int n;
    public int [][] spielbrett;
    public int sumoffree;
    public int winner;
    
    /**
     *  Creates Board object, am game board of size n * n with 1<=n<=10.
     */
     public Board(int n)
    {
        // TODO
        if(n > 10 || n<1) {
            throw new InputMismatchException ("The dimensions canno't make a proper board");
        }
        this.n = n;
        this.spielbrett = new int[n][n];
        this.sumoffree = n*n;
        this.winner= 0;
    }

    /**
     *  @return     length/width of the Board object
     */
    public int getN() { return n; }
    
    /**
     *  @return     number of currently free fields
     */
    public int nFreeFields() {
        // TODO
        return this.sumoffree;
    }
    
    /**
     *  @return     token at position pos
     */
    public int getField(Position pos) throws InputMismatchException
    {
        // TODO
        if ((pos.x > n-1) || (pos.x < 0) || (pos.y <0) || (pos.y> n-1)){
            throw new InputMismatchException("Position to get is not valid");
        }
        return this.spielbrett[pos.x][pos.y];
    }

    /**
     *  Sets the specified token at Position pos.
     */    
    public void setField(Position pos, int token) throws InputMismatchException
    {
        // TODO
        if ((pos.x > n-1) || (pos.x < 0) || (pos.y <0) || (pos.y> n-1)){
            throw new InputMismatchException("Position to be set is not valid");
        }
        if (token != 0 && token != 1 && token != -1 ) {
            throw new InputMismatchException("No valid Value for the token");
        }
        if (token == 0){
            if (this.spielbrett[pos.x][pos.y] != 0){
                this.spielbrett[pos.x][pos.y] = 0;
                this.sumoffree += this.sumoffree;
            }
        } else if (token == 1) {
            if (this.spielbrett[pos.x][pos.y] == 0){
                sumoffree -= sumoffree;
            }
            this.spielbrett[pos.x][pos.y] = 1;
        } else {
            if (this.spielbrett[pos.x][pos.y] == 0){
                sumoffree -= sumoffree;
            }
            this.spielbrett[pos.x][pos.y] = -1;
        }
    }
    
    /**
     *  Places the token of a player at Position pos.
     */
    public void doMove(Position pos, int player)
    {
        if (this.spielbrett[pos.x][pos.y]!=0){
            throw new InputMismatchException("Die Position ist nicht Frei");
        }
        setField(pos,player);

    }

    /**
     *  Clears board at Position pos.
     */
    public void undoMove(Position pos)
    {
        // TODO
        if (getField(pos)==0){
            throw new InputMismatchException("Das geht nicht.");
        }
        setField(pos,0);

    }
    
    /**
     *  @return     true if game is won, false if not
     */
    public boolean isGameWon() {
        // TODO
        for (int i = 0; i < n; i++) {
            int sum1 = 0;
            int sum_1 = 0;
            for (int j = 0; j < n; j++) {
                if (spielbrett[i][j] == 0) {
                    break;
                }
                if (spielbrett[i][j] == 1) {
                    sum1++;
                }
                if (spielbrett[i][j] == -1) {
                    sum_1++;
                }
            }
            if (sum1 == n) {
                winner = 1;
                return true;
            } else if (sum_1 == n) {
                winner = -1;
                return true;
            }
        }

        for (int i = 0; i < n; i++) {
            int sum1 = 0;
            int sum_1 = 0;
            for (int j = 0; j < n; j++) {
                if (spielbrett[j][i] == 0) {
                    break;
                }
                if (spielbrett[j][i] == 1) {
                    sum1++;
                }
                if (spielbrett[j][i] == -1) {
                    sum_1++;
                }
            }
            if (sum1 == n) {
                winner = 1;
                return true;
            } else if (sum_1 == n) {
                winner = -1;
                return true;
            }
        }

        int sum1 = 0;
        int sum_1 = 0;
        for (int a = 0; a < n; a++) {
            if (spielbrett[a][a] == 0) {
                break;
            }
            if (spielbrett[a][a] == 1) {
                sum1++;
            }
            if (spielbrett[a][a] == -1) {
                sum_1++;
            }
        }
        if (sum1 == n) {
            winner = 1;
            return true;
        } else if (sum_1 == n) {
            winner = -1;
            return true;
        }

        sum1 = 0;
        sum_1 = 0;
        for (int a = 0; a<n; a++){
            if (spielbrett[n-a-1][a] == 0) {
                break;
            }
            if (spielbrett[n-a-1][a] == 1) {
                sum1++;
            }
            if (spielbrett[n-a-1][a] == -1) {
                sum_1++;
            }
        }
        if (sum1 == n) {
            winner = 1;
            return true;
        } else if (sum_1 == n) {
            winner = -1;
            return true;
        }

        return false;
    }
    

    /**
     *  @return     set of all free fields as some Iterable object
     */
    public Iterable<Position> validMoves()
    {
        // TODO
        LinkedList<Position> available =new LinkedList<>();
        for (int a=0;a<n ;a++){
            for (int b=0;b<n;b++){
                if (this.spielbrett[a][b]==0){
                    Position somme = new Position(a,b);
                    available.add(somme);
                }
            }
        }
        return available;

    }

    /**
     *  Outputs current state representation of the Board object.
     *  Practical for debugging.
     */
    public void print()
    {
        // TODO
        for (int i = 0; i <n; i++){
            for(int j = 0; j <n; j++){
                System.out.print(this.spielbrett[i][j]);
            }
            System.out.println("/n");
        }
    }


}

