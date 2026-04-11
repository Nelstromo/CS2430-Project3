/**
 * Team Name: SIX!
 * Team Members: Nelson Long, Chris Reynolds, Carlos Recinos
 * Course: CS 2430
 * Project: Programming Project 3 – Spring 2026
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for the Space Shuttle Payload Optimizer.
 *[\
 * Goal: select experiments for the shuttle (max 700 kg) to get the highest total rating.
 * Runs all four solution approaches and prints a comparison of their results.
 */
public class Main {

    /** Returns the list of all 12 experiments with their name, weight, and rating. */
    public static List<Experiment> loadExperiments() {
        List<Experiment> experiments = new ArrayList<>();

        //                              Name                         Weight  Rating
        experiments.add(new Experiment("Cloud Patterns",                36,     5));
        experiments.add(new Experiment("Solar Flares",                 264,     9));
        experiments.add(new Experiment("Solar Power",                  188,     6));
        experiments.add(new Experiment("Binary Stars",                 203,     8));
        experiments.add(new Experiment("Relativity",                   104,     8));
        experiments.add(new Experiment("Seed Viability",                 7,     4));
        experiments.add(new Experiment("Sun Spots",                     90,     2));
        experiments.add(new Experiment("Mice Tumors",                   65,     8));
        experiments.add(new Experiment("Microgravity Plant Growth",     75,     5));
        experiments.add(new Experiment("Micrometeorites",              170,     9));
        experiments.add(new Experiment("Cosmic Rays",                   80,     7));
        experiments.add(new Experiment("Yeast Fermentation",            27,     4));

        return experiments;
    }

    public static void main(String[] args) {
        List<Experiment> experiments = loadExperiments();

        // ===================================================================
        // PART 1 — Three Greedy Strategies
        // ===================================================================
        System.out.println("=================================================================");
        System.out.println("  PART 1: GREEDY STRATEGIES");
        System.out.println("=================================================================\n");

        GreedyStrategies greedy = new GreedyStrategies(experiments);

        PayloadResult greedyRating   = greedy.highestRating();
        PayloadResult greedyLightest = greedy.lightestFirst();
        PayloadResult greedyRatio    = greedy.bestRatio();

        System.out.println("Strategy 1: Highest Rating First");
        System.out.print(greedyRating.getSummary());

        System.out.println("Strategy 2: Lightest First");
        System.out.print(greedyLightest.getSummary());

        System.out.println("Strategy 3: Best Rating-to-Weight Ratio First");
        System.out.print(greedyRatio.getSummary());

        // ===================================================================
        // PART 2 — Brute Force / Exhaustive Search
        // ===================================================================
        System.out.println("=================================================================");
        System.out.println("  PART 2: BRUTE FORCE (all 2^12 = 4096 subsets evaluated)");
        System.out.println("=================================================================\n");

        BruteForce brute = new BruteForce(experiments);
        List<PayloadResult> topThree = brute.getTopThree();
        PayloadResult bruteOptimal   = brute.getOptimal();

        System.out.println("Top 3 valid subsets by total rating:\n");
        for (int i = 0; i < topThree.size(); i++) {
            String label = (i == 0) ? " ** OPTIMAL **" : "";
            System.out.printf("  Rank %d%s%n", i + 1, label);
            System.out.print(topThree.get(i).getSummary());
            System.out.println();
        }

        // ===================================================================
        // PART 4 — Dynamic Programming (Extra Credit)
        // ===================================================================
        System.out.println("=================================================================");
        System.out.println("  PART 4: DYNAMIC PROGRAMMING (Extra Credit)");
        System.out.println("=================================================================\n");

        DynamicProgramming dp = new DynamicProgramming(experiments);
        PayloadResult dpResult = dp.solve();

        System.out.println("DP optimal payload:");
        System.out.print(dpResult.getSummary());

        // ===================================================================
        // PART 3 — Comparison Summary (printed last so it reads as a summary)
        // ===================================================================
        System.out.println();
        ComparisonSummary summary = new ComparisonSummary(
                greedyRating, greedyLightest, greedyRatio, bruteOptimal, dpResult);
        summary.printReport();
    }
}
