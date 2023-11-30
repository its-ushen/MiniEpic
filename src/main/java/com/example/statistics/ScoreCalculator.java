package com.example.statistics;

import com.example.database.ScoreFetcher;
import com.example.model.ScoreStats;
import com.example.model.UserScore;

import java.util.Collections;
import java.util.List;

public class ScoreCalculator {
    private ScoreFetcher scoreFetcher;

    public ScoreCalculator(ScoreFetcher scoreFetcher) {
        this.scoreFetcher = scoreFetcher;
    }

    public ScoreStats calculateUserStats(String username, String difficulty) {
        List<UserScore> userScores = scoreFetcher.fetchUserScoresBydifficulty(username, difficulty);
        return calculateStatistics(userScores);
    }

    public ScoreStats calculateAllUsersStats(String difficulty) {
        List<UserScore> allScores = scoreFetcher.fetchAllScoresBydifficulty(difficulty);
        return calculateStatistics(allScores);
    }

    private ScoreStats calculateStatistics(List<UserScore> scores) {
        double mean = calculateMean(scores);
        double median = calculateMedian(scores);
        double standardDeviation = calculateStandardDeviation(scores);
        return new ScoreStats(mean, median, standardDeviation);
    }

    public static double calculateMean(List<UserScore> scores) {
        if (scores.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (UserScore score : scores) {
            sum += score.getScore();
        }
        return sum / scores.size();
    }

    private static double calculateMedian(List<UserScore> scores) {
        if (scores.isEmpty()) {
            return 0.0;
        }
        Collections.sort(scores, (o1, o2) -> Double.compare(o1.getScore(), o2.getScore()));
        int middle = scores.size() / 2;
        if (scores.size() % 2 == 1) {
            return scores.get(middle).getScore();
        } else {
            return (scores.get(middle - 1).getScore() + scores.get(middle).getScore()) / 2.0;
        }
    }

    private static double calculateStandardDeviation(List<UserScore> scores) {
        if (scores.size() <= 1) {
            return 0.0;
        }
        double mean = calculateMean(scores);
        double varianceSum = 0.0;
        for (UserScore score : scores) {
            varianceSum += Math.pow(score.getScore() - mean, 2);
        }
        double variance = varianceSum / (scores.size() - 1);
        return Math.sqrt(variance);
    }



    public static void main(String[] args) {
        ScoreFetcher scoreFetcher = new ScoreFetcher();
        ScoreCalculator scoreCalculator = new ScoreCalculator(scoreFetcher);
        ScoreStats userScoreStats = scoreCalculator.calculateUserStats("Ushen", "Novice");
        ScoreStats allUserScoreStats = scoreCalculator.calculateAllUsersStats("Novice");
        System.out.println("User Mean: " + userScoreStats.getMean());
        System.out.println("All Users Mean: " + allUserScoreStats.getMean());
    }
    
}
