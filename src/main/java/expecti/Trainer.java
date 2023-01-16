package expecti;

import java.io.*;



public class Trainer
{
    static int iterations = 160;
    static int[] collection = new int[39];
    static int[] newValues = new int[38];
    static int[] newCollection = new int[39];
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


    public static int[] addition(int[] newValues, int[] collection, int newWeight)
    {
        int currentIteration = collection[38];
        int[] newCollection = new int[39];

        for(int i = 0; i < newCollection.length-1; i++)
        {
        newCollection[i] = (collection[i]*currentIteration+newValues[i]*newWeight)/(currentIteration+newWeight);
            System.out.println("collection: "+collection[i]*currentIteration+"    "+"newValues: "+newValues[i]*newWeight+"    "+"newCollection: "+newCollection[i]);
        }
        newCollection[newCollection.length-1] = currentIteration+newWeight;

        return newCollection;
    }

    public static String toString(int[] newCollection)
    {
        StringBuilder myString = new StringBuilder();
        for(int i=0; i< newCollection.length; i++){
            myString.append(newCollection[i]).append("\n");
        }
        return myString.toString();
    }
}
