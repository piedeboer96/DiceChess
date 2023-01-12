package dice;

final class DefaultDieExperiment {
    public static void main(String[] args) {
        Die d = new DefaultDie();
        int limit = 10_000;
        int[] results = new int[7];
        for (int rolls = 0; rolls < limit; rolls++) {
            int roll = d.roll();
            results[roll]++;
        }
        for (int i = 1; i < 7; i++) {
            System.out.println("Rolled a " + i + " " + results[i] + " times.");
        }
    }
}
