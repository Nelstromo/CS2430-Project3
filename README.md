# CS2430-Project3
## The Knapsack Problem (Team Project)

Solves an example of the Knapsack Problem using different approaches and algorithms, then compares and alalyzes the results.

TO RUN:

Download and extract the .zip file into a folder.

Run src/Main.java in your IDE of choice

---

# Experiment data

Max load is 700 kg.

Payload experiments:
- Cloud Patterns
  - Weight: 36 kg
  - Rating: 5
- Solar Flares		
  - Weight: 264 kg
  - Rating: 9
- Solar Power		
  - Weight: 188 kg
  - Rating: 6
- Binary Stars		
  - Weight: 203 kg
  - Rating: 8
- Relativity		
  - Weight: 104 kg
  - Rating: 8
- Seed Viability
  - Weight: 7 kg
  - Rating: 4
- Sun Spots
  - Weight: 90 kg
  - Rating: 2
- Mice Tumors		
  - Weight: 65 kg
  - Rating: 8
- Microgravity Plant Growth		
  - Weight: 75 kg
  - Rating: 5
- Micrometeorites		
  - Weight: 170 kg
  - Rating: 9
- Cosmic Rays		
  - Weight: 80 kg
  - Rating: 7
- Yeast Fermentation		
  - Weight: 27 kg
  - Rating: 4

---

# Algorithms/strategies used

- Highest Rating First
  - Selects experiments based on highest rating without exceeding 700 kg.
- Lightest First
  - Selects experiments based on lightest weight without exceeding 700 kg.
- Best Rating-to-Weight Ratio First
  - Computes score = rating / weight for each experiment.
  - Selects experiments based on highest ratio while total weight ≤ 700 kg.
- Brute Force / Exhaustive Search
  - With 12 items, there are 212 = 4096 possible subsets.
  - Generates all 4096 possible subsets of experiments.
  - For each subset, computes total weight and total rating.
  - Identifies and displays the three highest-rated valid subsets (total weight ≤ 700 kg), clearly indicating which one is optimal.
- Dynamic Programming
