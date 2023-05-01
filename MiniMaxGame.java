import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MiniMaxGame {
    public String miniMaxEst;

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: ProgramName input.txt output.txt depthNo");
        }

        String inputFile = args[0];
        String outputFile = args[1];
        int depth = Integer.parseInt(args[2]);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            var moveGenerator = new MoveGenerator();

            while ((line = reader.readLine()) != null) {
                MorrisBoard board = new MorrisBoard(line);
                stringToBitboards(line, board);
                System.out.println(line);
                board.showBoard(board);
                ArrayList<MorrisBoard> L = moveGenerator.GenerateMovesMidgameEndgame(board);
                for (MorrisBoard b : L) {
                    System.out.println("Possible move for white and black: \n ");
                    moveGenerator.showBoard(b);
                }
                int miniMaxEst = MiniMaxGame(board, depth, true, L);
                System.out.println("MiniMax value: " + miniMaxEst);

                var nextMove = getNextMove(board, L, depth);
                String nextMoveBoard = convertBitBoardToStandardString(nextMove);
                System.out.println("\n Try next move: " + nextMoveBoard);
                // System.out.println("\n Try bestmove:" + "\n white: " + testNextMove.white +
                // "\n black: " + testNextMove.black);

                writer.write("Input State: " + line + "\n");
                writer.write("Output State: " + nextMoveBoard + "\n");
                writer.write("States evaluated by static estimation: " + staticEvaluated + "\n");
                writer.write("MINIMAX estimate: " + miniMaxEst);
            }

            System.out.println("File has been successfully read and written to output.txt");

        } catch (IOException e) {
            System.out.println("An error occurred while reading or writing the file.");
            e.printStackTrace();
        }
    }

    public static MorrisBoard getNextMove(MorrisBoard board, ArrayList<MorrisBoard> validMoves, int depth) {
        int bestValue = Integer.MIN_VALUE;
        var moveGenerator = new MoveGenerator();
        MorrisBoard bestMove = null;
        for (var b : validMoves) {
            int value = MiniMaxGame(b, depth - 1, false, validMoves);
            if (value > bestValue) {
                bestValue = value;
                bestMove = b;
            }
        }
        return bestMove;
    }

    public int getStaticEvaluated(int value) {
        return value;
    }

    static int staticEvaluated = 0;

    public static int MiniMaxGame(MorrisBoard board, int depth, boolean maximizingPlayer, ArrayList<MorrisBoard> L) {
        staticEvaluated = 0;
        if (depth == 0) {
            staticEvaluated += 1;
            return gameStaticEst(board, L);

        }
        var moveGenerator = new MoveGenerator();
        var moveGameList = moveGenerator.GenerateMovesMidgameEndgame(board);
        int bestValue;
        if (maximizingPlayer == true) {
            bestValue = Integer.MIN_VALUE;
            for (MorrisBoard b : moveGameList) {
                int value = MiniMaxGame(b, depth - 1, false, L);
                bestValue = Math.max(bestValue, value);
            }
        } else {
            bestValue = Integer.MAX_VALUE;
            for (MorrisBoard b : moveGameList) {
                int value = MiniMaxGame(b, depth - 1, true, L);
                bestValue = Math.min(bestValue, value);
            }
        }

        return bestValue;
    }

    public static String convertBitBoardToStandardString(MorrisBoard board) {
        StringBuilder sb = new StringBuilder();
        String state = "xWB";
        for (int i = 0; i < 24; i++) {
            long mask = 1L << i;
            char c = 'x';
            if ((board.black & mask) != 0) {
                c = 'B';
            } else if ((board.white & mask) != 0) {
                c = 'W';
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static int gameStaticEst(MorrisBoard board, ArrayList<MorrisBoard> L) {
        if(Long.bitCount(board.black) <= 2 ) return 10000;
        else if(Long.bitCount(board.white) <= 2) return -10000;
        else if(L.size() == 0) return 10000;
        else return 1000 * (Long.bitCount(board.white) - Long.bitCount(board.black)) - L.size();
    }

    public static void stringToBitboards(String input, MorrisBoard board) {
        long whiteBoard = 0L;
        long blackBoard = 0L;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == 'W') {
                whiteBoard |= 1L << i;
            } else if (c == 'B') {
                blackBoard |= 1L << i;
            }
        }
        board.black = blackBoard;
        board.white = whiteBoard;
    }
}
