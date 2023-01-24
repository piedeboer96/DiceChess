package learningagent.neuralnetwork.csvhelp;

import java.io.FileWriter;
import java.io.IOException;

public class ArrayToCSV {

    public static void save2DArrayToCSV(double[][] array, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);

            // Write the data
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    writer.append(String.valueOf(array[i][j]));
                    if (j < array[i].length - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }

            writer.flush();
            writer.close();
            System.out.println("Data saved to " + fileName + " successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static void save1DArrayToCSV(double[] array, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);

            // Write the data
            for (int i = 0; i < array.length; i++) {
                writer.append(String.valueOf(array[i]));
                if (i < array.length - 1) {
                    writer.append("\n");
                }
            }

            writer.flush();
            writer.close();
            System.out.println("Data saved to " + fileName + " successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



	