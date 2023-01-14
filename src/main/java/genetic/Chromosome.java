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

    // CHANGED UPPERBOUNDS TO 1 INTEGER HIGHER
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
            {-100, 1},

            {-50, 51},
            {-500, -49},
            {0, 101},
            {0, 101},
            {0, 101},

            {2000, 4001},
            {-50, 1},
            {0, 16},
            {0, 101},
            {-100, 1},

            {-60, 41},
            {-40, 61},
            {-30, 71},
            {200, 401},
            {-20, 101},

            {100, 101},
            {0, 11},
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
    // CHANGED BOUNDS TO CALL bounds SO IT IS NOT FIXED
    public int[] chromosomeBodyInitialization() {
        int[] array = new int[bounds.length];
        for (int i = 0; i < bounds.length; i++) {
            array[i] = RANDOM.nextInt(bounds[i][0], bounds[i][1]);
        }
        return array;
    }

    // cross over the two chromosomes by changing the first half of the first chromosome with the second half of the other chromosome
    // CHANGED FIXED NUMBERS TO VARIABLES, SO IF WE CHANGE THE LENGTH OF THE DATA OF THE CHROMOSOME THERE'S NO PROBLEM.
    Bot crossoverWith(Chromosome other) {
        int[] merged = new int[DATA.length];
        System.arraycopy(DATA, 0, merged, 0, DATA.length / 2);
        System.arraycopy(other.DATA, DATA.length / 2, merged, DATA.length / 2, DATA.length - DATA.length / 2);
        Chromosome chromosome = new Chromosome(merged);
        return new Bot(chromosome);
    }

    // CROSSING OVER WITH SOME RATE (BEST 0.25)
    Bot crossoverRate(Chromosome other, double crossoverFactor) {
        int[] merged = new int[DATA.length];
        int[] CURRENT_DATA = DATA;
        for(int i = 0; i < merged.length; i++) {
            double d = RANDOM.nextDouble();
            if (d < crossoverFactor) {
                if(CURRENT_DATA == DATA) CURRENT_DATA = other.DATA;
                else CURRENT_DATA = DATA;
            }
            merged[i] = CURRENT_DATA[i];
        }
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
