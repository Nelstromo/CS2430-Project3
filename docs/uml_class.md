```mermaid

classDiagram
    class Experiment {
	    - name: string
	    - weight: int
	    - rating: int
	    + Experiment(String name, int weight, int rating)
	    + getRatio() double
    }

    class PayloadResult {
	    - List~Experiment~ experiments
	    - totalWeight: int
	    - totalRating: int
	    + addExperiment(Experiment e) void
	    + getSummary() string
    }

    class GreedyStrategies {
	    - experiments: List~Experiment~
	    - MAX_WEIGHT = 700: final int
	    + highestRating() PayloadResult
	    + lightestFirst() PayloadResult
	    + bestRatio() PayloadResult
	    - sortByRatingDesc() List~Experiment~
	    - sortByWeightAsc() List~Experiment~
	    - sortByRatioDesc() List~Experiment~
    }

    class BruteForce {
	    - List~Experiment~ experiments
	    - MAX_WEIGHT = 700: final int
	    + generateAllSubsets() List~PayloadResult~
	    + evaluateSubset(List~Experiment~ subset) PayloadResult
	    + getValidSubsets() List~PayloadResult~
	    + getTopThree() List~PayloadResult~
	    + getOptimal() PayloadResult
    }

    class ComparisonSummary {
	    - greedyRating: PayloadResult 
	    - greedyLightest: PayloadResult
	    - greedyRatio: PayloadResult
	    - bruteForceOptiomal: PayloadResult
	    + printReport() void
	    + generateSummary() string
    }

    class Main {
	    + main(String[] args)
	    + loadExperiments() List~Experiment~
    }

    class DynamicProgramming {
	    - experiments: List~Experiment~
	    - MAX_WEIGHT = 700: final int
	    + solve() PayloadResult
	    + buildDPTable() int[][]
	    + reconstructSolution() List~Experiment~
    }

    GreedyStrategies --> Experiment
    BruteForce --> Experiment
    PayloadResult --> "*" Experiment
    Main --> GreedyStrategies
    Main --> BruteForce
    Main --> DynamicProgramming
    ComparisonSummary --> PayloadResult
    Main --> ComparisonSummary
