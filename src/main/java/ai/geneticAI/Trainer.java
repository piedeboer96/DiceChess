package ai.geneticAI;

public class Trainer {

    Bot[] bots; // chromosomes
    final int population = 20; // population
    final int generations = 200; // generation
    final double mutateFactor = 0.007; // mutation factor


    Trainer() {
        bots = new Bot[population];
        for(int i = 0; i < bots.length; i++) {
            bots[i] = new Bot();
        }
    }

    void train() {
        for(int i = 0; i < generations-1; i++) {
            nextGeneration();
        }
    }

    void nextGeneration() {
        selection();
        crossover();
        mutation();
    }


    private void selection() {
        int[] wins = new int[bots.length];
        for (int i = 0; i < bots.length; i++){
            for(int j = i + 1; j < bots.length; j++){
                if(i != j) {
                    int winner = matchBots(bots[i], bots[j]);
                    wins[winner]++;
                }
            }
        }
        // select top 10 bots (chromosomes)
        int[] top10 = new int[10];
        for(int i =0 ; i < wins.length; i++){
            for(int j = i + 1; j < wins.length; j++){
                if(i!=j && !(i >= top10.length)){
                    top10[i] = Math.max(wins[i], wins[j]);
                    // TODO: after choosing the best 10, how do we add them to the bots array?
                }
            }
        }
    }

    // TODO: match the two bots
    private int matchBots(Bot bot1, Bot bot2) {
        //game.setPlayerBlack(bot1)
        //game.setPlayerWhite(bot2)
        //result = game.run()
        // return winner based on result
        return 0;
    }


    void crossover() {
        for (int i = 0; i < bots.length - 1; i+=2) {
            Bot first = bots[i];
            Bot second = bots[i + 1];
            first.chromosome.crossoverWith(second.chromosome);
        }
    }


    void mutation() {
        for(Bot bot : bots) {
            bot.chromosome.mutate(mutateFactor);
        }
    }


    // TODO
    public Bot bestBot() {
        int[] wins = new int[bots.length];
        //if (int x = generations;){ // TODO: make sure its the last generation!
            for (int i = 0; i < bots.length; i++) {
                for (int j = i + 1; j < bots.length; j++) {
                    if (i != j) {
                        int winner = matchBots(bots[i], bots[j]);
                        wins[winner]++;
                    }
                }
            }
        // select top bot (chromosome)
        int max = largest(wins);
        int index = wins[max];
        return bots[index];
    }

    // util function
    private int largest(int[] arr) {
        int i;
        int max = arr[0];
        for (i = 1; i < arr.length; i++)
            if (arr[i] > max)
                max = arr[i];
        return max;
    }




}
