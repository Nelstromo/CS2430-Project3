/**
 * Team Name: SIX!
 * Team Members: Nelson Long, Chris Reynolds, Carlos Recinos
 * Course: CS 2430
 * Project: Programming Project 3 – Spring 2026
 */

/**
 * Takes the results from all four strategies and prints a comparison report.
 * Shows which greedy strategies matched the optimal answer and which did not.
 */
public class ComparisonSummary {

    private PayloadResult greedyRating;    // result from highest-rating-first strategy
    private PayloadResult greedyLightest;  // result from lightest-first strategy
    private PayloadResult greedyRatio;     // result from best-ratio strategy
    private PayloadResult bruteForceOptimal; // true best answer from brute force
    private PayloadResult dpResult;        // result from dynamic programming

    public ComparisonSummary(PayloadResult greedyRating,
                             PayloadResult greedyLightest,
                             PayloadResult greedyRatio,
                             PayloadResult bruteForceOptimal,
                             PayloadResult dpResult) {
        this.greedyRating       = greedyRating;
        this.greedyLightest     = greedyLightest;
        this.greedyRatio        = greedyRatio;
        this.bruteForceOptimal  = bruteForceOptimal;
        this.dpResult           = dpResult;
    }

    /** Builds and returns the full comparison report as a formatted string. */
    public String generateSummary() {
        int optimalRating = bruteForceOptimal.getTotalRating();
        StringBuilder sb = new StringBuilder();

        sb.append("=================================================================\n");
        sb.append("               PAYLOAD SELECTION COMPARISON REPORT\n");
        sb.append("=================================================================\n\n");

        sb.append("--- GREEDY STRATEGY 1: Highest Rating First ---\n");
        sb.append(greedyRating.getSummary()).append("\n");

        sb.append("--- GREEDY STRATEGY 2: Lightest First ---\n");
        sb.append(greedyLightest.getSummary()).append("\n");

        sb.append("--- GREEDY STRATEGY 3: Best Rating-to-Weight Ratio First ---\n");
        sb.append(greedyRatio.getSummary()).append("\n");

        sb.append("--- BRUTE FORCE OPTIMAL ---\n");
        sb.append(bruteForceOptimal.getSummary()).append("\n");

        sb.append("--- DYNAMIC PROGRAMMING RESULT ---\n");
        sb.append(dpResult.getSummary()).append("\n");

        sb.append("=================================================================\n");
        sb.append("VERDICT\n");
        sb.append("=================================================================\n");
        sb.append(String.format("Optimal total rating (brute force): %d%n%n", optimalRating));

        sb.append(verdict("Greedy – Highest Rating First", greedyRating, optimalRating));
        sb.append(verdict("Greedy – Lightest First",       greedyLightest, optimalRating));
        sb.append(verdict("Greedy – Best Ratio First",     greedyRatio, optimalRating));
        sb.append(verdict("Dynamic Programming",           dpResult, optimalRating));

        return sb.toString();
    }

    /** Returns a one-line result for a single strategy showing its rating and whether it matched optimal. */
    private String verdict(String label, PayloadResult result, int optimal) {
        boolean matched = result.getTotalRating() == optimal;
        return String.format("  %-40s rating = %2d  [%s]%n",
                label + ":",
                result.getTotalRating(),
                matched ? "MATCHES OPTIMAL" : "does not match optimal");
    }

    /** Prints the comparison report to the console. */
    public void printReport() {
        System.out.println(generateSummary());
    }
}
