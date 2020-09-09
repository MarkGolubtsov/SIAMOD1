package sample;

import sample.impl.Stats;

import java.util.List;

public abstract class RandomGenerator {

    private Double a;
    private Double m;
    private Double ro;

    public RandomGenerator(double a, double m, double r0) {
        this.a = a;
        this.m = m;
        this.ro = r0;
    }

    public abstract double next();

    public abstract double getR();

    public abstract List<Double> getOneSequence();

    public Double getA() {
        return a;
    }

    public RandomGenerator setA(Double a) {
        this.a = a;
        return this;
    }

    public Double getM() {
        return m;
    }

    public RandomGenerator setM(Double m) {
        this.m = m;
        return this;
    }

    public Double getRo() {
        return ro;
    }

    public RandomGenerator setRo(Double ro) {
        this.ro = ro;
        return this;
    }

    public Stats getStats() {
        List<Double> sequence = getOneSequence();
        Double sum = sequence.stream().reduce(Double::sum).get();

        int n = sequence.size();
        Stats stats = new Stats();
        stats.setExpectation(sum / n);
        double temp = 0.0;
        for (Double it : sequence) {
            temp = temp + Math.pow(it - stats.getExpectation(), 2);
        }
        stats.setDispersion(temp / n);
        stats.setMeanSquareDeviation(Math.sqrt(stats.getDispersion()));
        stats.setList(sequence);
        return stats;
    }
}
