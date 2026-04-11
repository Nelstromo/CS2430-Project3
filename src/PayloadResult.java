/**
 * Team Name: SIX!
 * Team Members: Nelson Long, Chris Reynolds, Carlos Recinos
 * Course: CS 2430
 * Project: Programming Project 3 – Spring 2026
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the output of a payload selection strategy.
 * Keeps track of which experiments were chosen and their combined weight and rating.
 */
public class PayloadResult {

    private List<Experiment> experiments;
    private int totalWeight;
    private int totalRating;

    /** Creates an empty payload with nothing selected yet. */
    public PayloadResult() {
        experiments = new ArrayList<>();
        totalWeight = 0;
        totalRating = 0;
    }

    /** Adds an experiment to the payload and updates the running totals. */
    public void addExperiment(Experiment e) {
        experiments.add(e);
        totalWeight += e.getWeight();
        totalRating += e.getRating();
    }

    public int getTotalWeight()          { return totalWeight; }
    public int getTotalRating()          { return totalRating; }
    public List<Experiment> getExperiments() { return experiments; }

    /** Returns a formatted string listing each chosen experiment along with the totals. */
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("  Experiments selected:\n");
        for (Experiment e : experiments) {
            sb.append("    - ").append(e.toString()).append("\n");
        }
        sb.append(String.format("  Total Weight : %d kg%n", totalWeight));
        sb.append(String.format("  Total Rating : %d%n", totalRating));
        return sb.toString();
    }
}
