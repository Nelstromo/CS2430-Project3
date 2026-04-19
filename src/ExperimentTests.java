/**
 * Team Name: SIX!
 * Team Members: Nelson Long, Chris Reynolds, Carlos Recinos
 * Course: CS 2430
 * Project: Programming Project 3 – Spring 2026
 */

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;


public class ExperimentTests {

     private static final int MAX_WEIGHT = 700;

    // Loads all 12 experiments from Main for testing.
    private List<Experiment> realExperiments(){
        return Main.loadExperiments();
 }

    // Small set of experiments for testing.
    private List<Experiment> testExperiments(){
        List<Experiment> list = new ArrayList<>();
        //                              Name                         Weight  Rating
        list.add(new Experiment("Cloud Patterns",                36,     5));
        list.add(new Experiment("Solar Flares",                 264,     9));
        list.add(new Experiment("Solar Power",                  188,     6));
        list.add(new Experiment("Binary Stars",                 203,     8));
        list.add(new Experiment("Yeast Fermentation",            27,     4));
        return list;
    }

    //  =================================================================
    //                      GREEDY STRATEGIES TESTS
    //  =================================================================
    private GreedyStrategies greedy;

     @Before
    public void setup() {
        greedy = new GreedyStrategies(testExperiments());
    }

   // Test weight limit is not exceeded
    @Test
    public void highestRating_returnsBestRatingWithinWeightLimit(){
        PayloadResult result = greedy.highestRating();
        assertTrue(result.getTotalWeight() <= MAX_WEIGHT);
    }
    @Test
    public void lightestFirst_returnsLightestWithinWeightLimit(){
        PayloadResult result = greedy.lightestFirst();
        assertTrue(result.getTotalWeight() <= MAX_WEIGHT);
    }
    @Test
    public void bestRatio_returnsBestRatioWithinWeightLimit(){
        PayloadResult result = greedy.bestRatio();
        assertTrue(result.getTotalWeight() <= MAX_WEIGHT);
    }

    // Test fill order of experiments is correct for each strategy
    @Test
    public void highestRating_returnsHighestRatedExperimentsFirst(){
        PayloadResult result = greedy.highestRating();
        List<Experiment> experiments = result.getExperiments();
        for (int i = 1; i < experiments.size(); i++) {
            assertTrue(experiments.get(i-1).getRating() >= experiments.get(i).getRating());
        }
    }
    @Test
    public void lightestFirst_returnsLightestExperimentsFirst(){
        PayloadResult result = greedy.lightestFirst();
        List<Experiment> experiments = result.getExperiments();
        for (int i = 1; i < experiments.size(); i++) {
            assertTrue(experiments.get(i-1).getWeight() >= experiments.get(i).getWeight());
        }
    }
    @Test
    public void bestRatio_returnsBestRatedExperimentsFirst(){
        PayloadResult result = greedy.bestRatio();
        List<Experiment> experiments = result.getExperiments();
        for (int i = 1; i < experiments.size(); i++) {
            assertTrue(experiments.get(i-1).getRatio() <= experiments.get(i).getRatio());
        }
    }

    // Test edge cases
    @Test
    public void greedyStrategies_returnsEmptyIfNoExperimentsFit(){
        List<Experiment> heavyExperiment = new ArrayList<>();
        heavyExperiment.add(new Experiment("Feathers", 701, 10));
        GreedyStrategies greedyHeavy = new GreedyStrategies(heavyExperiment);
        assertEquals(0, greedyHeavy.highestRating().getTotalWeight());
        assertEquals(0, greedyHeavy.highestRating().getTotalRating());
        assertTrue(greedyHeavy.highestRating().getExperiments().isEmpty());
        assertEquals(0, greedyHeavy.lightestFirst().getTotalWeight());
        assertEquals(0, greedyHeavy.lightestFirst().getTotalRating());
        assert(greedyHeavy.lightestFirst().getExperiments().isEmpty());
        assertEquals(0, greedyHeavy.bestRatio().getTotalWeight());
        assertEquals(0, greedyHeavy.bestRatio().getTotalRating());
        assertTrue(greedyHeavy.bestRatio().getExperiments().isEmpty());
    }
    @Test
    public void greedyStrategies_returnsEmptyResultsIfNoExperiments(){
        GreedyStrategies greedyEmpty = new GreedyStrategies(new ArrayList<>());
        assertEquals(0, greedyEmpty.highestRating().getTotalWeight());
        assertEquals(0, greedyEmpty.highestRating().getTotalRating());
        assertTrue(greedyEmpty.highestRating().getExperiments().isEmpty());
        assertEquals(0, greedyEmpty.lightestFirst().getTotalWeight());
        assertEquals(0, greedyEmpty.lightestFirst().getTotalRating());
        assertTrue(greedyEmpty.lightestFirst().getExperiments().isEmpty());
        assertEquals(0, greedyEmpty.bestRatio().getTotalWeight());
        assertEquals(0, greedyEmpty.bestRatio().getTotalRating());
        assertTrue(greedyEmpty.bestRatio().getExperiments().isEmpty());
    }
    @Test
    public void greedyStrategies_returnsOneExperimentIfOnlyOneFits(){
        List<Experiment> experiments = new ArrayList<>();
        experiments.add(new Experiment("Feathers", 701, 10));
        experiments.add(new Experiment("Lead", 700, 5));
        GreedyStrategies greedyOneFit = new GreedyStrategies(experiments);
        PayloadResult resultRating = greedyOneFit.highestRating();
        assertEquals(700, resultRating.getTotalWeight());
        assertEquals(5, resultRating.getTotalRating());
        assertEquals(1, resultRating.getExperiments().size());
        assertEquals("Lead", resultRating.getExperiments().get(0).getName());
        PayloadResult resultLightest = greedyOneFit.lightestFirst();
        assertEquals(700, resultLightest.getTotalWeight());
        assertEquals(5, resultLightest.getTotalRating());
        assertEquals(1, resultLightest.getExperiments().size());
        assertEquals("Lead", resultLightest.getExperiments().get(0).getName());
        PayloadResult resultRatio = greedyOneFit.bestRatio();
        assertEquals(700, resultRatio.getTotalWeight());
        assertEquals(5, resultRatio.getTotalRating());
        assertEquals(1, resultRatio.getExperiments().size());
        assertEquals("Lead", resultRatio.getExperiments().get(0).getName());
    }
    
    //  =================================================================
    //                      BRUTE FORCE TESTS
    //  =================================================================
    private BruteForce bf;

    @Before
    public void setupBruteForce() {
        bf = new BruteForce(realExperiments());
    }
    // test all subsets are generated correctly
    @Test
    public void bruteForce_generates4096Subsets() {
        assertEquals(4096, bf.generateAllSubsets().size());
    }
    @Test
    public void bruteForce_generatesValidSubsets() {
        List<PayloadResult> validSubsets = bf.getValidSubsets();
        for (PayloadResult subset : validSubsets) {
            assertTrue(subset.getTotalWeight() <= MAX_WEIGHT);
        }
    }
    // Tests brute force works as expected
    @Test
    public void bruteForce_sumsCorrectly() {
        List<PayloadResult> validSubsets = bf.getValidSubsets();
        for (PayloadResult subset : validSubsets) {
            int calculatedWeight = 0;
            int calculatedRating = 0;
            for (Experiment e : subset.getExperiments()) {
                calculatedWeight += e.getWeight();
                calculatedRating += e.getRating();
            }
            assertEquals(calculatedWeight, subset.getTotalWeight());
            assertEquals(calculatedRating, subset.getTotalRating());
        }
    }
    @Test
    public void bruteForce_returnsExactlyThreeTopSubsets() {
        List<PayloadResult> topThree = bf.getTopThree();
        assertEquals(3, topThree.size());
    }
    @Test
    public void bruteForce_sorstByRatingDesc() {
        List<PayloadResult> topThree = bf.getTopThree();
        for (int i = 1; i < topThree.size(); i++) {
            assertTrue(topThree.get(i-1).getTotalRating() >= topThree.get(i).getTotalRating());
        }
    }
    @Test
    public void bruteForce_tieBreakerByWeightAsc() {
        List<Experiment> list = new ArrayList<>();
            list.add(new Experiment("Lead", 199, 10));
            list.add(new Experiment("Feathers", 200, 10));
            list.add(new Experiment("Lead Feathers", 399, 10));
        BruteForce tiedBF = new BruteForce(list);
        List<PayloadResult> topThree = tiedBF.getTopThree();
        assertEquals("Lead", topThree.get(0).getExperiments().get(0).getName());
    }
    @Test
    public void bruteForce_optimalBeatsGreedy() {
        PayloadResult optimal = bf.getOptimal();
        GreedyStrategies greedy = new GreedyStrategies(realExperiments());
        assertTrue(optimal.getTotalRating() >= greedy.highestRating().getTotalRating());
        assertTrue(optimal.getTotalRating() >= greedy.lightestFirst().getTotalRating());
        assertTrue(optimal.getTotalRating() >= greedy.bestRatio().getTotalRating());
        assertTrue(optimal.getTotalWeight() <= MAX_WEIGHT);
    }

    //  =================================================================
    //                      DYNAMIC PROGRAMMING TESTS
    //  =================================================================
    private DynamicProgramming dp;

    @Before
    public void setupDP() {
        dp = new DynamicProgramming(realExperiments()); 
    }
    // tests DP talbe built correctly and matches brute force optimal rating
    @Test
    public void buildDPTable_returnsArrayOfCorrectDimensions() {
        int[][] table = dp.buildDPTable();
        assertEquals(realExperiments().size() + 1, table.length);
        assertEquals(MAX_WEIGHT + 1, table[0].length);
    }
    @Test
    public void buildDPTable_fillsBaseCaseCorrectly() {
        int[][] table = dp.buildDPTable();
        for (int w = 0; w <= MAX_WEIGHT; w++) {
            assertEquals(0, table[0][w]);
        }
    }
    @Test
    public void buildDPTable_matchesBruteForceOptimal() {
        int[][] table = dp.buildDPTable();
        PayloadResult optimalBrute = new BruteForce(realExperiments()).getOptimal();
        int optimalRating = optimalBrute.getTotalRating();
        int optimalWeight = optimalBrute.getTotalWeight();
        assertEquals(optimalRating, table[realExperiments().size()][optimalWeight]);
    }
    @Test
    public void buildDPTable_returnsZeroForEmptyExperimentList() {
        DynamicProgramming emptyDP = new DynamicProgramming(new ArrayList<>());
        int[][] table = emptyDP.buildDPTable();
        for (int w = 0; w <= MAX_WEIGHT; w++) {
            assertEquals(0, table[0][w]);
        }
    }

    //  =================================================================
    //                      PAYLOAD RESULT TESTS
    //  =================================================================
    private PayloadResult pr;

    @Before
    public void setupPayloadResult() {
        pr = new PayloadResult();
    }
    @Test
    public void payloadResult_empty() {
        assertEquals(0, pr.getTotalWeight());
        assertEquals(0, pr.getTotalRating());
        assertTrue(pr.getExperiments().isEmpty());
    }
    @Test
    public void payloadResult_resolvesToString() {
        pr.addExperiment(new Experiment("Feathers", 100, 10));
        String summary = pr.getSummary();
        assertTrue(summary.contains("Feathers"));
        assertTrue(summary.contains("100 kg"));
        assertTrue(summary.contains("10"));
    }
}