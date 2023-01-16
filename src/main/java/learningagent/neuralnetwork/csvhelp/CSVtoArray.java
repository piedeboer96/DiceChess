package learningagent.neuralnetwork.csvhelp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVtoArray {

    public double[][] readCSVto2DDoubleArray(String fileName) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<double[]> array = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(cvsSplitBy);
                double[] doubleValues = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                    doubleValues[i] = Double.parseDouble(values[i]);
                }
                array.add(doubleValues);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                e.printStackTrace();
	                }
                }
            }
        return array.toArray(new double[array.size()][]);
    }

    public double[] readCSVto1DDoubleArray(String fileName) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<Double> array = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(fileName));
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(cvsSplitBy);
                    for (String value : values) {
                        array.add(Double.parseDouble(value));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return array.stream().mapToDouble(Double::doubleValue).toArray();
    }


}


