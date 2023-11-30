package com.example;
import com.example.statistics.ScoreCalculator;


import com.example.database.ScoreFetcher;
import com.example.model.ScoreStats;

public class Driver {
    public static void main(String[] args) {
        ScoreCalculator scoreCalculator = new ScoreCalculator(new ScoreFetcher());
        
        ScoreStats myStats = scoreCalculator.calculateUserStats("Ushen", "Novice");
        System.out.println(myStats.getMean());

    }
}
