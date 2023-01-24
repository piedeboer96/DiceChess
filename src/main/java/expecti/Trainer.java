package expecti;

import java.io.*;



public class Trainer
{
    static int iterations = 160;
    static double[] collection = new double[39];
    static double[] newValues = new double[38];
    static double[] newCollection = new double[39];
    public static void main(String[] args)
    {

        for(int i = 0; i < iterations; i++)
        {
            try
            {
                File file = new File("src/main/java/expecti/weights.txt");

                BufferedReader read = new BufferedReader(new FileReader(file));
                String value;
                int index = 0;
                while((value = read.readLine()) != null)
                {
                collection[index] = Integer.parseInt(value);
                System.out.println(collection[index]);
                index++;
                }
                read.close();


                genetic.Trainer t = new genetic.Trainer();
                t.train();
                newValues = t.bestBot().getChromosome().getData();
                newCollection = addition(newValues, collection, 1);
                String newCollectionString = toString(newCollection);
                System.out.println(newCollectionString);


                FileWriter fileWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.print(newCollectionString);
                printWriter.close();

            }
            catch (Exception e)
            {
                System.out.println("Did not write");
            }
        }

    }


    public static double[] addition(double[] newValues, double[] collection, double newWeight)
    {
        double currentIteration = collection[38];
        double[] newCollection = new double[39];

        for(int i = 0; i < newCollection.length-1; i++)
        {
        newCollection[i] = (collection[i]*currentIteration+newValues[i]*newWeight)/(currentIteration+newWeight);
            System.out.println("collection: "+collection[i]*currentIteration+"    "+"newValues: "+newValues[i]*newWeight+"    "+"newCollection: "+newCollection[i]);
        }
        newCollection[newCollection.length-1] = currentIteration+newWeight;

        return newCollection;
    }

    public static String toString(double[] newCollection)
    {
        StringBuilder myString = new StringBuilder();
        for(int i=0; i< newCollection.length; i++){
            myString.append(newCollection[i]).append("\n");
        }
        return myString.toString();
    }
}
