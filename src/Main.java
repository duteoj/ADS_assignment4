public class Main {
    public static void main(String[] args) {

        Experiment experiment = new Experiment();
        experiment.runMultipleTests();

        System.out.println("\n--- Performance Comparison Table ---");
        experiment.printResults();
    }
}
