package sample.impl;

import java.util.List;

public class Stats {

    private double expectation;

    private double dispersion;

    private double meanSquareDeviation;

    private List<Double> list;

    public List<Double> getList() {
        return list;
    }

    public Stats setList(List<Double> list) {
        this.list = list;
        return this;
    }

    public double getMeanSquareDeviation() {
        return meanSquareDeviation;
    }

    public Stats setMeanSquareDeviation(double meanSquareDeviation) {
        this.meanSquareDeviation = meanSquareDeviation;
        return this;
    }

    public double getExpectation() {
        return expectation;
    }

    public Stats setExpectation(double expectation) {
        this.expectation = expectation;
        return this;
    }

    public double getDispersion() {
        return dispersion;
    }

    public Stats setDispersion(double dispersion) {
        this.dispersion = dispersion;
        return this;
    }
}
