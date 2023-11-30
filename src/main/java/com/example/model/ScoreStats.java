package com.example.model;

public class ScoreStats {
    private double mean;
    private double median;
    private double standardDeviation;
    
    public ScoreStats(double mean, double median, double standardDeviation) {
        this.mean = mean;
        this.median = median;
        this.standardDeviation = standardDeviation;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
}
