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
                File file150 = new File("src/main/java/expecti/weights50.txt");
                File file100 = new File("src/main/java/expecti/weights50.txt");

                BufferedReader read = new BufferedReader(new FileReader(file));
                System.out.println("successfully read");
                String value;
                int index = 0;
                while((value = read.readLine()) != null)
                {
                    System.out.println("successfully reached first while-loop");
                collection[index] = Double.parseDouble(value);
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


                if(i ==15)
                {
                    FileWriter fileWriter = new FileWriter(file100);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    printWriter.print(newCollectionString);
                    printWriter.close();
                }
                if(i ==65)
                {
                    FileWriter fileWriter = new FileWriter(file150);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    printWriter.print(newCollectionString);
                    printWriter.close();
                }
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
