package learningagent.datapipeline;

public class OneHotEncoding {

    // TODO: (optional) colour encode using positive and negative values, (future) use full FEN and compare perfomance


    // Encoding of simplified FEN that takes into account colors
    // TODO: this one probably useless since it cannot work with all acitivation functions becuase of the range of the values
    public double[] EncodeSimpleFEN(String fen) {
        double[] encoded = new double[64];
        int index = 0;
        for (char c : fen.toCharArray()) {
            if (c == 'p') {
                encoded[index] = 0;
                index++;
            } else if (c == 'P') {
                encoded[index] = 1;
                index++;
            } else if (c == 'n') {
                encoded[index] = 2;
                index++;
            } else if (c == 'N') {
                encoded[index] = 3;
                index++;
            } else if (c == 'b') {
                encoded[index] = 4;
                index++;
            } else if (c == 'B') {
                encoded[index] = 5;
                index++;
            } else if (c == 'r') {
                encoded[index] = 6;
                index++;
            } else if (c == 'R') {
                encoded[index] = 7;
                index++;
            } else if (c == 'q') {
                encoded[index] = 8;
                index++;
            } else if (c == 'Q') {
                encoded[index] = 9;
                index++;
            } else if (c == 'k') {
                encoded[index] = 10;
                index++;
            } else if (c == 'K') {
                encoded[index] = 11;
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


    // TODO: needs to be verified to be working correctly
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
    static OneHotEncoding hot = new OneHotEncoding();


}








