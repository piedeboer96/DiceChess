package genetic;
import java.util.Random;

public class Chromosome {
    // all data we will have in the chromosome for each function in the heuristic fitness function
    private final int[] DATA;
    // use random to randomize the initial values of the data of the chromosomes
    private final Random RANDOM = new Random();

    // randomise the data inside the chromosome as the genetic algorithm will take care about it later on
    public Chromosome() {
        DATA = chromosomeBodyInitialization();
    }

    // same thing here, but we can sign the data manually.
    public Chromosome(int[] data) {
        DATA = data;
    }

    public int[] getData() {
        return DATA;
    }

    public static int[][] bounds = {
            {-70, 31},
            {0, 16},
            {0, 101},
            {0, 101},
            {220, 421},

            {-50, 1},
            {-50, 1},
            {-30, 71},
            {-50, 1},
            {-100, 01},

            {-50, 51},
            {-100, 1},
            {0, 101},
            {0, 101},
            {0, 101},

            {0, 16},
            {0, 101},
            {-100, 1},
            {-60, 41},
            {-40, 61},

            {-30, 71},
            {200, 401},
            {-20, 101},
            {100, 102},
            {0, 12},

            {800, 1001},
            {-40, 41},
            {0, 101},
            {-60, 31},
            {0, 101},

            {0, 11},
            {0, 101},
            {0, 101},
            {0, 101},
            {400, 601},

            {-30, 1}
    };

    // assign values randomly to the data of the chromosome
    public int[] chromosomeBodyInitialization(){
        int[] array = new int[36];
        array[0] = RANDOM.nextInt(-70, 30); array[1] = RANDOM.nextInt(0, 15); array[2] = RANDOM.nextInt(0, 100); array[3] =RANDOM.nextInt(0, 100); array[4] = RANDOM.nextInt(220, 420);
        array[5] = RANDOM.nextInt(-50, 0); array[6] =RANDOM.nextInt(-50, 0); array[7] =RANDOM.nextInt(-30, 70); array[8] =RANDOM.nextInt(-50, 0); array[9] =RANDOM.nextInt(-100, 0);
        array[10] =RANDOM.nextInt(-50, 50); array[11] =RANDOM.nextInt(-100, 0); array[12] =RANDOM.nextInt(0, 100); array[13] =RANDOM.nextInt(0, 100); array[14] =RANDOM.nextInt(0, 100);
        array[15] =RANDOM.nextInt(0, 15); array[16] =RANDOM.nextInt(0, 100); array[17] =RANDOM.nextInt(-100, 0); array[18] =RANDOM.nextInt(-60, 40); array[19] =RANDOM.nextInt(-40, 60);
        array[20] =RANDOM.nextInt(-30, 70); array[21] =RANDOM.nextInt(200, 400); array[22] =RANDOM.nextInt(-20, 100); array[23] =100; array[24] =RANDOM.nextInt(0, 10);
        array[25] =RANDOM.nextInt(800, 1000); array[26] =RANDOM.nextInt(-40, 40); array[27] =RANDOM.nextInt(0, 100); array[28] =RANDOM.nextInt(-60, 30); array[29] =RANDOM.nextInt(0, 100);
        array[30] =RANDOM.nextInt(0, 10); array[31] =RANDOM.nextInt(0, 100); array[32] =RANDOM.nextInt(0, 100); array[33] =RANDOM.nextInt(0, 100); array[34] =RANDOM.nextInt(400, 600);
        array[35] =RANDOM.nextInt(-30, 0);
        return array;
    }

    // cross over the two chromosomes by changing the first half of the first chromosome with the second half of the other chromosome
    Bot crossoverWith(Chromosome other) {
        int[] merged = new int[36];
        System.arraycopy(DATA, 0, merged, 0, 18);
        System.arraycopy(other.DATA, 18, merged, 18, 18);
        Chromosome chromosome = new Chromosome(merged);
        return new Bot(chromosome);
    }

    // mutate the values of the chromosome randomly if the condition below is met
    public void mutate(double mutateFactor) {
        for (int i = 0; i < DATA.length; i++) {
            double d = RANDOM.nextDouble();
            if (d < mutateFactor) {
                DATA[i] = RANDOM.nextInt(bounds[i][0], bounds[i][1]);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder myString = new StringBuilder();
        for(int i=0; i< DATA.length; i++){
            myString.append(DATA[i]).append("\n");
        }
        return myString.toString();
    }
}
