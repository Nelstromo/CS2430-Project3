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
 * Implements three greedy strategies for building a payload.
 * Each strategy sorts the experiments differently and then picks them
 * one by one until the weight limit is reached.
 * Greedy strategies are fast but won't always find the best possible answer.
 */
public class GreedyStrategies {

    private List<Experiment> experiments;
    private static final int MAX_WEIGHT = 700;

    /** @param experiments the list of all experiments to choose from */
    public GreedyStrategies(List<Experiment> experiments) {
        this.experiments = experiments;
    }

    /** Picks experiments with the highest rating first. */
    public PayloadResult highestRating() {
        return greedyFill(sortByRatingDesc());
    }

    /** Picks the lightest experiments first. */
    public PayloadResult lightestFirst() {
        return greedyFill(sortByWeightAsc());
    }

    /** Picks experiments with the best rating-to-weight ratio first. */
    public PayloadResult bestRatio() {
        return greedyFill(sortByRatioDesc());
    }

    /**
     * Goes through a sorted list and adds each experiment to the payload
     * as long as it doesn't push the total weight over 700 kg.
     */
    private PayloadResult greedyFill(List<Experiment> sorted) {
        PayloadResult result = new PayloadResult();
        for (Experiment e : sorted) {
            if (result.getTotalWeight() + e.getWeight() <= MAX_WEIGHT) {
                result.addExperiment(e);
            }
        }
        return result;
    }

    // Sort helpers — each returns a new sorted copy so the original list is unchanged

    /** Sorts by rating high to low. Ties go to the lighter experiment. */
    private List<Experiment> sortByRatingDesc() {
        List<Experiment> copy = new ArrayList<>(experiments);
        copy.sort(Comparator.comparingInt(Experiment::getRating).reversed()
                            .thenComparingInt(Experiment::getWeight));
        return copy;
    }

    /** Sorts by weight low to high. Ties go to the higher-rated experiment. */
    private List<Experiment> sortByWeightAsc() {
        List<Experiment> copy = new ArrayList<>(experiments);
        copy.sort(Comparator.comparingInt(Experiment::getWeight)
                            .thenComparing(Comparator.comparingInt(Experiment::getRating).reversed()));
        return copy;
    }

    /** Sorts by rating/weight ratio high to low. Ties go to the higher-rated experiment. */
    private List<Experiment> sortByRatioDesc() {
        List<Experiment> copy = new ArrayList<>(experiments);
        copy.sort(Comparator.comparingDouble(Experiment::getRatio).reversed()
                            .thenComparing(Comparator.comparingInt(Experiment::getRating).reversed()));
        return copy;
    }
}
