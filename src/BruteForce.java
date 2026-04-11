/**
 * Team Name: SIX!
 * Team Members: Nelson Long, Chris Reynolds, Carlos Recinos
 * Course: CS 2430
 * Project: Programming Project 3 – Spring 2026
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Finds the best payload by checking every possible combination of experiments.
 * With 12 experiments there are 2^12 = 4096 possible subsets.
 * Every valid subset (total weight <= 700 kg) is evaluated and the highest-rated ones are kept.
 * This approach is guaranteed to find the true best answer.
 */
public class BruteForce {

    private List<Experiment> experiments;
    private static final int MAX_WEIGHT = 700;

    /** @param experiments the list of all experiments to choose from */
    public BruteForce(List<Experiment> experiments) {
        this.experiments = experiments;
    }

    /**
     * Generates all 4096 possible subsets using bitmask iteration.
     * Each number from 0 to 4095 represents one unique combination:
     * if bit j is set in the number, experiment j is included in that subset.
     *
     * @return a list of PayloadResults, one for every possible subset
     */
    public List<PayloadResult> generateAllSubsets() {
        int n = experiments.size();
        int totalSubsets = 1 << n;  // same as 2^n
        List<PayloadResult> allSubsets = new ArrayList<>();

        for (int mask = 0; mask < totalSubsets; mask++) {
            List<Experiment> subset = new ArrayList<>();

            // Check each bit to decide if experiment j belongs in this subset
            for (int j = 0; j < n; j++) {
                if ((mask & (1 << j)) != 0) {
                    subset.add(experiments.get(j));
                }
            }

            allSubsets.add(evaluateSubset(subset));
        }

        return allSubsets;
    }

    /** Builds a PayloadResult from a given group of experiments. */
    public PayloadResult evaluateSubset(List<Experiment> subset) {
        PayloadResult result = new PayloadResult();
        for (Experiment e : subset) {
            result.addExperiment(e);
        }
        return result;
    }

    /** Returns only the subsets that are within the 700 kg weight limit. */
    public List<PayloadResult> getValidSubsets() {
        List<PayloadResult> valid = new ArrayList<>();
        for (PayloadResult pr : generateAllSubsets()) {
            if (pr.getTotalWeight() <= MAX_WEIGHT) {
                valid.add(pr);
            }
        }
        return valid;
    }

    /**
     * Returns the top three valid subsets by total rating.
     * Ties are broken by weight (lighter payload wins).
     */
    public List<PayloadResult> getTopThree() {
        List<PayloadResult> valid = getValidSubsets();
        valid.sort(Comparator.comparingInt(PayloadResult::getTotalRating).reversed()
                             .thenComparingInt(PayloadResult::getTotalWeight));
        return valid.subList(0, Math.min(3, valid.size()));
    }

    /** Returns the single best payload found by brute force. */
    public PayloadResult getOptimal() {
        return getTopThree().get(0);
    }
}
