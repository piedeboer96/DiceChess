package ai.geneticAI;
import java.util.Random;


public class Chromosome {

    // all data we will have in the chromosome for each function in the heuristic fitness function
    int[] data;
    // use random to randomize the initial values of the data of the chromosomes
    Random random = new Random();


    // randomise the data inside the chromosome as the genetic algorithm will take care about it later on
    Chromosome() {
        this.data = randomNumbers();
    }


    // same thing here, but we can sign the data manually.
    Chromosome(int[] data) {
        this.data = data;
    }


    // assign values randomly to the data of the chromosome
    int[] randomNumbers(){
        Random random = new Random();
        int[] array = new int[32];
        for(int i =0; i < 32; i++){
            array[i] = random.nextInt()*100; // what value should we multiply by? is 1000 a good value to use?
        }
        return array;
    }


    // cross over the two chromosomes by changing the first half of the first chromosome with the second half of the other chromosome
    void crossoverWith(Chromosome other) {
        int[] firstHalfChromosome = this.firstHalf();
        int[] secondHalfChromosome2 = other.lastHalf();
        int[] merge = new int[32];
        int count = 0;

        for(int i=0; i < firstHalfChromosome.length; i++){
            merge[i] = firstHalfChromosome[i];
            count++;
        }
        for (int i : secondHalfChromosome2) {
            merge[count] = i;
        }
        data = merge;
        new Chromosome(data);
    }


    // return the first half of the used chromosome
    private int[] firstHalf() {
        int[] arr1 = data;
        int[] arr2 = new int[16];
        for(int i =0; i < 16;i++){
                arr2[i] = arr1[i];
        }
        return arr2;
    }

    // return the second half of the used chromosome
    private int[] lastHalf() {
        int[] arr = data;
        int[] arr1 = new int[16];
        for (int x = 0; x < 16; x++) {
            arr1[x] = arr[x+16];
        }
        int[] arr2 = new int[16];
        for (int i = 0; i < 16; i++) {
            arr2[i] = arr1[i];
        }
        return arr2;
    }



    // mutate the values of the chromosome randomly if the condition below is met
    void mutate(double mutateFactor) {
        int[] arr = new int[data.length];
        for(int i=0; i < data.length; i++){
            if(random.nextDouble() < 0.007) {
                arr[i] = random.nextInt() * 100; // what value should we multiply by? is 1000 a good value to use?
            } else {
                arr[i] = data[i];
            }
        }
        data = arr;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        String myString = "";
        for(int i=0; i<data.length; i++){
            myString += "value of index "+ i + " is " + data[i] +"\n";
        }
        return myString;
    }



}
