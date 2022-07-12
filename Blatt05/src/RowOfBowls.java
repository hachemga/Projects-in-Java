import java.util.LinkedList;

/**
 * This class implements a game of Row of Bowls.
 * For the games rules see Blatt05. The goal is to find an optimal strategy.
 */
public class RowOfBowls {

    public int[] values;
    public int[][] matrix;
    public RowOfBowls() {
    }
    
    /**
     * Implements an optimal game using dynamic programming
     * 
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGain(int[] values) {
        // TODO
        this.values = values;
        this.matrix = new int[values.length][values.length];
        for (int a = 0; a < values.length; a++){
            matrix[a][a] = values[a];
        }
        for (int a = 1; a < values.length; a++){
            int start = 0;
            for (int end = a; end < values.length; end++){
                matrix[start][end] = Math.max(matrix[start][end - a] - matrix[start + 1][end],
                                matrix[ start + a][end] - matrix[start][end - 1]);
                start++;
            }
        }
        return matrix[0][values.length - 1];
    }


    /**
     * Implements an optimal game recursively.
     *
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGainRecursive(int[] values) {
        // TODO
        this.values = values;
        int start = 0;
        int end = values.length - 1;
        return maxGainRecursiveLittle(values, start, end);
    }

    public int maxGainRecursiveLittle(int[] values, int start, int end){
        if (start == (end-1)){
            return (Math.max(values[start],values[end])-Math.min(values[start],values[end]));
        }else {
            int sum1= values [start] - maxGainRecursiveLittle(values, start+1, end);
            int sum2 = values [end]- maxGainRecursiveLittle(values, start,end-1);
            return Math.max(sum1, sum2);
        }
    }

    
    /**
     * Calculates an optimal sequence of bowls using the partial solutions found in maxGain(int values)
     * @return optimal sequence of chosen bowls (represented by the index in the values array)
     */
    public Iterable<Integer> optimalSequence() {
        // TODO
        LinkedList <Integer> sequence = new LinkedList<>();
        int i = 0;
        int j = values.length-1;
        while (sequence.size()<values.length){
            if (i==j){
                sequence.add(i);
            }else if (matrix[i][j] == (matrix[i][i]-matrix[i+1][j])){
                sequence.add(i);
                i++;
            }
            else {
                sequence.add(j);
                j--;
            }
        }
        return sequence;
        /*start = 0;
        end = values.length - 1;
        int sum1 = 0;
        int sum2 = 0;
        while (start != end - 1) {
            sum1 = values[start];
            start++;
            sum1 = sum1 - maxGain(values);
            start--;
            sum2 = values[end];
            end--;
            sum2 = sum2 - maxGain(values);
            end++;
            if (sum1 >= sum2) {
                sequence.addLast(start);
                start++;
            } else {
                sequence.addLast(end);
                end--;
            }
            if (start == end - 1) {
                if (values[start] >= values[end]) {
                    sequence.addLast(start);
                    sequence.addLast(end);
                } else {
                    sequence.addLast(end);
                    sequence.addLast(start);
                }
            }

        }
        return sequence;*/
    }


    public static void main(String[] args)
    {
        // For Testing
        int[] values= {4,7,2,3};
        RowOfBowls row = new RowOfBowls();
        System.out.println(row.maxGainRecursive(values));
        System.out.println(row.maxGain(values));
        for (int i=0; i < row.values.length;i++){
            for (int j=0; j < row.values.length;j++){
                System.out.print(row.matrix[i][j] + " ");
            }
            System.out.println();
        }
        for (Integer e : row.optimalSequence()){
            System.out.print(Integer.toString(e));
        }
    }
}

