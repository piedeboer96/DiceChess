package learningagent.database;

import java.util.Arrays;

/**
 * This class contains a method to encode the simplified FEN into an input vector of 0s and 1s
 * to feed the neural network.
 */
public class OneHotEncoding {



    /**
     * This method takes a board FEN as input
     * and brings it into 1s and 0s to be used
     * as input to the neural network.
     * @param fen FEN representing the board position
     * @return encoded array of 0s and 1s
     */
    public double[] oneHotEncodeSimplifiedFEN(String fen) {
        double[] encoded = new double[384];
        int index = 0;

        for (char c : fen.toCharArray()) {
            if (c == 'p') {
                int offset = 0;
                encoded[index + offset] = 1;
                encoded[64 + index] = 1;
                index++;
            } else if (c == 'P') {
                int offset = 12;
                encoded[index + offset] = 1;
                encoded[64 + index] = 1;
                index++;
            } else if (c == 'n') {
                int offset = 24;
                encoded[index + offset] = 1;
                encoded[64 + index] = 1;
                index++;
            } else if (c == 'N') {
                int offset = 36;
                encoded[index + offset] = 1;
                encoded[64 + index] = 1;
                index++;
            } else if (c == 'b') {
                int offset = 48;
                encoded[index + offset] = 1;
                encoded[64 + index] = 1;
                index++;
            } else if (c == 'B') {
                int offset = 60;
                encoded[index + offset] = 1;
                encoded[64 + index] = 1;
                index++;
            } else if (c == 'r') {
                int offset = 72;
                encoded[index + offset] = 1;
                encoded[64 + index] = 1;
                index++;
            } else if (c == 'R') {
                int offset = 84;
                encoded[index + offset] = 1;
                encoded[64 + index] = 1;
                index++;
            } else if (c == 'q') {
                int offset = 96;
                encoded[index + offset] = 1;
                encoded[64 + index] = 1;
                index++;
            } else if (c == 'Q') {
                int offset = 108;
                encoded[index + offset] = 1;
                encoded[64 + index] = 1;
                index++;
            } else if (c == 'k') {
                int offset = 120;
                encoded[index + offset] = 1;
                encoded[64 + index] = 1;
                index++;
            } else if (c == 'K') {
                int offset = 132;
                encoded[index + offset] = 1;
                encoded[64 + index] = 1;
                index++;
            } else if (c == '/') {
                // Do nothing
            } else if (Character.isDigit(c)) {
                int numEmpty = Integer.parseInt(c + "");
                // Skip the empty spaces by incrementing the index by the number of empty spaces
                index += numEmpty;
            } else {
                // Do Nothing
            }
        }
        return encoded;
    }


}








