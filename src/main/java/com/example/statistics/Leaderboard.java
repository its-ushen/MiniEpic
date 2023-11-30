package com.example.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.database.ScoreFetcher;
import com.example.database.UserFetcher;
import com.example.model.UserScore;

public class Leaderboard {

    public Leaderboard() {
        
    }

    public static List<UserScore> orderLeaderboard(List<UserScore> leaderboard) {

        Collections.sort(leaderboard, new Comparator<UserScore>() {
            @Override
            public int compare(UserScore u1, UserScore u2) {
                return Double.compare(u2.getScore(), u1.getScore());
            }
        });
        return leaderboard;
    }

    public static List<UserScore> createLeaderboard() {
        List<UserScore> leaderboard = new ArrayList<>();
        List<String> names = UserFetcher.return_users();

        for (String name: names) {
            List<UserScore> scores = ScoreFetcher.fetchUserScores(name);
            double mean = ScoreCalculator.calculateMean(scores);
            leaderboard.add(new UserScore(name,mean));
        }

        return orderLeaderboard(leaderboard);
    }

    public static void main(String[] args) {
        List<UserScore> leaderboard = orderLeaderboard(createLeaderboard());

        for (UserScore pair :leaderboard) {
            System.out.print(pair.getUsername() + ": " + pair.getScore());
        }
    }
}