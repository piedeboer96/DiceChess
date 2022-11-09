package ai.geneticAI;
import java.util.Random;


public class Chromosome {

    // all data we will have in the chromosome for each function in the heuristic fitness function
    int[] data;

    // use random to randomize the initial values of the data of the chromosomes
    Random random;


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
        int[] array = new int[36];
        for(int i =0; i <= data.length; i++){
            array[i] = random.nextInt()*100; // what value should we multiply by? is 1000 a good value to use?
        }
        return array;
    }


    // cross over the two chromosomes by changing the first half of the first chromosome with the second half of the other chromosome
    void crossoverWith(Chromosome other) {
        int[] chromosome1 = this.firstHalf();
        int[] chromosome2 = other.lastHalf();
        int[] merge = new int[36];
        int count = 0;

        for(int i=0; i < chromosome1.length; i++){
            merge[i] = chromosome1[i];
            count++;
        }
        for (int i : chromosome2) {
            merge[count] = i;
        }
        data = merge;
        new Chromosome(data);
    }


    // return the first half of the used chromosome
    private int[] firstHalf() {
        int[] arr1 = new int[36];
        int[] arr2 = new int[18];
        for(int i =0; i < arr1.length;i++){
            while(i<18){
                arr2[i] = arr1[i];
            }
        }
        return arr2;
    }

    // return the second half of the used chromosome
    private int[] lastHalf() {
        int[] arr1 = new int[36];
        int[] arr2 = new int[18];
        for(int i =0; i < arr1.length;i++){
            while(i>17){
                arr2[i] = arr1[i];
            }
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



}
