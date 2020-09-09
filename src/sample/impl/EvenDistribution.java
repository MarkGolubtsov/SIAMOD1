package sample.impl;

import sample.RandomGenerator;

import java.util.List;

public class EvenDistribution extends RandomGenerator {
    private int current = 0;
    double aEven;
    double bEven;
    double r;

    public EvenDistribution(double a, double m, double r0) {
        super(a, m, r0);
    }

    @Override
    public double next() {
        this.r = (current - aEven) / (bEven - aEven);
        current++;
        return aEven + (bEven - aEven) * r;
    }

    @Override
    public double getR() {
        return r;
    }

    @Override
    public List<Double> getOneSequence() {
        //RandomGenerator rnd = new Lamer();
        //while () {
        //    xn = (x-a)/(b-a);
        //}
        return null;
    }
}
