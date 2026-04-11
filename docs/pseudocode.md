# Pseudocode – Project 3

ok so we need to pick experiments for the shuttle, max 700kg, maximize rating
12 experiments, each has name weight rating

---

## Experiment (just a data thing)
```
Experiment:
    name, weight, rating
    getRatio() -> rating / weight
```

## PayloadResult (holds what we picked)
```
PayloadResult:
    list of experiments
    totalWeight, totalRating

    addExperiment(e):
        add e to list
        totalWeight += e.weight
        totalRating += e.rating

    getSummary():
        print each experiment
        print totals
```

---

## Part 1 – Greedy (3 ways)

basically all 3 are the same loop just sorted differently

```
greedyFill(sortedList):
    result = empty PayloadResult
    for each experiment in sortedList:
        if result.totalWeight + e.weight <= 700:
            result.add(e)
    return result
```

**strategy 1** – sort by rating DESC then greedyFill

**strategy 2** – sort by weight ASC then greedyFill

**strategy 3** – sort by ratio DESC then greedyFill

---

## Part 2 – Brute Force

2^12 = 4096 subsets, just check all of them

```
generateAllSubsets():
    for mask = 0 to 4095:
        subset = []
        for j = 0 to 11:
            if bit j is set in mask:
                subset.add(experiments[j])
        evaluate subset -> PayloadResult
        add to list

getValid() -> filter where totalWeight <= 700
getTopThree() -> sort by rating desc, take first 3
getOptimal() -> topThree[0]
```

---

## Part 3 – just print everything and compare

which greedy matched optimal? which didnt?
print it all nice

---

## Part 4 – DP (extra credit)

0/1 knapsack

```
dp[i][w] = best rating using first i experiments with weight budget w

base case: dp[0][w] = 0 for all w

for i = 1 to n:
    for w = 0 to 700:
        dp[i][w] = dp[i-1][w]  // skip it
        if experiments[i].weight <= w:
            dp[i][w] = max(dp[i][w], dp[i-1][w - weight] + rating)

// backtrack to find what we picked
w = 700
for i = n down to 1:
    if dp[i][w] != dp[i-1][w]:
        include experiments[i]
        w -= experiments[i].weight
```

way faster than brute force for big n, same answer tho

---

## Main

```
load 12 experiments (hardcode them)
run greedy x3
run brute force
run dp
print comparison
```
