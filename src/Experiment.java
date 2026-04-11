/**
 * Team Name: SIX!
 * Team Members: Nelson Long, Chris Reynolds, Carlos Recinos
 * Course: CS 2430
 * Project: Programming Project 3 – Spring 2026
 */

/**
 * Stores the data for a single science experiment.
 * Each experiment has a name, a weight in kg, and a rating for scientific value.
 */
public class Experiment {

    private String name;
    private int weight;  // kilograms
    private int rating;  // scientific value (higher is better)

    /** Creates an experiment with the given name, weight, and rating. */
    public Experiment(String name, int weight, int rating) {
        this.name = name;
        this.weight = weight;
        this.rating = rating;
    }

    public String getName()  { return name; }
    public int getWeight()   { return weight; }
    public int getRating()   { return rating; }

    /**
     * Returns the rating divided by weight.
     * Used by the best-ratio greedy strategy to find the most valuable experiment per kg.
     */
    public double getRatio() {
        return (double) rating / weight;
    }

    /** Returns a readable description, e.g. "Cloud Patterns (36 kg, rating 5)" */
    @Override
    public String toString() {
        return String.format("%s (%d kg, rating %d)", name, weight, rating);
    }
}
