/**
 * Team Name: SIX!
 * Team Members: Nelson Long, Chris Reynolds, Carlos Recinos
 * Course: CS 2430
 * Project: Programming Project 3 – Spring 2026
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Solves the payload selection problem using dynamic programming (DP).
 *
 * Instead of checking every possible combination like brute force, DP breaks the
 * problem into smaller pieces and saves the answers to those pieces in a table.
 * Each cell in the table stores the best rating achievable with a given number of
 * experiments and a given weight limit. This avoids repeating the same calculations
 * and gives the correct optimal answer much more efficiently.
 *
 * Table definition:
 *   dp[i][w] = best total rating using the first i experiments with a weight budget of w kg
 *
 * For each experiment i and each possible weight w, we decide:
 *   - Skip experiment i: dp[i][w] = dp[i-1][w]
 *   - Include experiment i (if it fits): dp[i][w] = dp[i-1][w - weight] + rating
 *   We take whichever option gives the higher rating.
 *
 * After the table is filled, we trace back through it to find which experiments were chosen.
 */
public class DynamicProgramming {

    private List<Experiment> experiments;
    private static final int MAX_WEIGHT = 700;

    // The DP table — built by buildDPTable() and reused by reconstructSolution()
    private int[][] dp;

    /** @param experiments the list of all experiments to choose from */
    public DynamicProgramming(List<Experiment> experiments) {
        this.experiments = experiments;
    }

    /**
     * Fills the DP table from the bottom up.
     * Row 0 is the base case (no experiments = rating 0 for any weight).
     * Each subsequent row adds one more experiment to consider.
     *
     * @return the completed DP table
     */
    public int[][] buildDPTable() {
        int n = experiments.size();
        dp = new int[n + 1][MAX_WEIGHT + 1];

        for (int i = 1; i <= n; i++) {
            Experiment current = experiments.get(i - 1);
            int w_i = current.getWeight();
            int r_i = current.getRating();

            for (int w = 0; w <= MAX_WEIGHT; w++) {
                // Start by assuming we skip this experiment
                dp[i][w] = dp[i - 1][w];

                // Check if including this experiment would be better
                if (w_i <= w) {
                    int ratingIfIncluded = dp[i - 1][w - w_i] + r_i;
                    if (ratingIfIncluded > dp[i][w]) {
                        dp[i][w] = ratingIfIncluded;
                    }
                }
            }
        }

        return dp;
    }

    /**
     * Traces back through the completed DP table to figure out which
     * experiments were actually included in the optimal solution.
     * If the rating at row i differs from row i-1, experiment i was included.
     *
     * @return list of experiments chosen in the optimal payload
     */
    public List<Experiment> reconstructSolution() {
        List<Experiment> chosen = new ArrayList<>();
        int n = experiments.size();
        int w = MAX_WEIGHT;

        for (int i = n; i > 0; i--) {
            // If the value changed, this experiment was included
            if (dp[i][w] != dp[i - 1][w]) {
                Experiment e = experiments.get(i - 1);
                chosen.add(e);
                w -= e.getWeight();
            }
        }

        return chosen;
    }

    /**
     * Runs the full DP solution and returns the result as a PayloadResult.
     *
     * @return the optimal payload found by dynamic programming
     */
    public PayloadResult solve() {
        buildDPTable();
        List<Experiment> chosen = reconstructSolution();

        PayloadResult result = new PayloadResult();
        for (Experiment e : chosen) {
            result.addExperiment(e);
        }
        return result;
    }
}
