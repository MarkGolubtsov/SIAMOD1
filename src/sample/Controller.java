package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.impl.Lamer;
import sample.impl.Stats;

import java.text.DecimalFormat;
import java.util.List;

public class Controller {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private static DecimalFormat df3 = new DecimalFormat("#.######");

    @FXML
    private TextField aInput;

    @FXML
    private TextField mInput;

    @FXML
    private TextField r0Input;

    @FXML
    private BarChart<String, Double> barChart;

    @FXML
    private Text mat;

    @FXML
    private Text disp;

    @FXML
    private Text kN;

    @FXML
    private Text period;

    @FXML
    private Text sredKv;

    @FXML
    private Text apperiodich;

    @FXML
    private TextField inputN;

    @FXML
    private TextField inputA;

    @FXML
    private TextField inputB;

    @FXML
    private TextField inputL;


    private final int K = 20;

    private RandomGenerator randomGenerator;


    private double getA() {
        return Double.parseDouble(aInput.getCharacters().toString());
    }

    private double getM() {
        return Double.parseDouble(mInput.getCharacters().toString());
    }

    private double getR0() {
        return Double.parseDouble(r0Input.getCharacters().toString());
    }


    private double getASecond() {
        return Double.parseDouble(inputA.getCharacters().toString());
    }

    private double getB() {
        return Double.parseDouble(inputB.getCharacters().toString());
    }

    private double getN() {
        return Double.parseDouble(inputN.getCharacters().toString());
    }

    private double getL() {
        return Double.parseDouble(inputL.getCharacters().toString());
    }


    public int Period(RandomGenerator randomGenerator) {
        RandomGenerator rnd = new Lamer(randomGenerator.getA(), randomGenerator.getM(), getR0());//randomGenerator;
        int v = 5000000;
        while (v != 1) {
            rnd.next();
            v--;
        }
        double lastX = rnd.next();

        rnd = new Lamer(randomGenerator.getA(), randomGenerator.getM(), getR0());
        int i1 = 0, i2 = 0;
        int i = 0;
        while (i1 == 0 || i2 == 0) {
            i++;
            if (Math.abs(rnd.next() - lastX) < 0.0000000001) {
                if (i1 == 0) {
                    i1 = i;
                } else {
                    i2 = i;
                }
            }
        }

        int p = i2 - i1;

        return p;
    }


    public int getAperiodic(RandomGenerator randomGenerator, int p) {
        RandomGenerator l1 = new Lamer(randomGenerator.getA(), randomGenerator.getM(), getR0());
        double prevVal = 0;
        for (int i = 0; i < p; i++)
            prevVal = l1.next();

        RandomGenerator l2 = new Lamer(randomGenerator.getA(), randomGenerator.getM(), l1.getR());
        int i3 = 0;
        while (Math.abs(prevVal - l2.next()) < 0.0000000000001) {
            prevVal = l1.next();
            i3++;
        }
        return i3 + p;
    }

    @FXML
    private void write() {
        barChart.getData().clear();
        RandomGenerator randomGenerator = new Lamer(getA(), getM(), getR0());
        Stats stats = randomGenerator.getStats();
        generationGraphics(stats.getList());


        disp.setText(df3.format(stats.getDispersion()));
        sredKv.setText(df3.format(stats.getMeanSquareDeviation()));
        mat.setText(df3.format(stats.getExpectation()));
        int count = checkPriznak(stats.getList());
        kN.setText(df3.format((double) (2 * count) / stats.getList().size()));

        //period.setText(String.valueOf(stats.getList().size()));
        int periodValue = Period(randomGenerator);
        period.setText(String.valueOf(periodValue));
        apperiodich.setText(String.valueOf(getAperiodic(randomGenerator, periodValue)));
    }

    private void generationGraphics(List<Double> list) {

        list.sort(Double::compareTo);
        double xMin = list.get(0);
        double xMax = list.get(list.size() - 1);

        double length = (list.get(list.size() - 1) - list.get(0)) / K;
        XYChart.Series dataSeries = new XYChart.Series();

        int index = 0;
        for (double i = xMin; i < xMax; i += length) {
            double count = index;

            while (index < list.size() && list.get(index) < i + length) {
                index += 1;
            }
            count = index - count;
            String x = "[ " + df2.format(i) + "; " + df2.format((i + length)) + "]";

            dataSeries.getData().add(new XYChart.Data<>(x, count / list.size()));
        }
        barChart.getData().add(dataSeries);
    }

    private int checkPriznak(List<Double> list) {
        int count = 0;
        for (int i = 1; i < list.size(); i = i + 2) {
            if (list.get(i) * list.get(i) + list.get(i - 1) * list.get(i - 1) < 1) {
                count++;
            }
        }
        return count;
    }
}