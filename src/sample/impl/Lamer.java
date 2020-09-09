package sample.impl;

import sample.RandomGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lamer extends RandomGenerator {

    private double r;

    public Lamer(double a, double m, double r0) {
        super(a, m, r0);
        this.r = r0;
    }

    @Override
    public double next() {
        double tempFirstStep = getA() * r;
        double tempSecondStep = tempFirstStep % getM();
        r = tempSecondStep;
        return tempSecondStep / getM();
    }

    @Override
    public List<Double> getOneSequence() {
        List<Double> array = new ArrayList<>();
        try (FileWriter writer = new FileWriter(UUID.randomUUID().toString() + "_numbers.txt", false)) {

            Double temp = next();
            while (!array.contains(temp)) {
                array.add(temp);
                writer.append(temp.toString());
                writer.append('\n');
                writer.flush();
                temp = next();
            }
        } catch (IOException ex) {

        }
        return array;
    }

}
