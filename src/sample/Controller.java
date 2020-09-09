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

        period.setText(String.valueOf(stats.getList().size()));
        apperiodich.setText(String.valueOf(stats.getList().size()));
    }

    private void generationGraphics(List<Double> list) {

        list.sort(Double::compareTo);
        double xMin = list.get(0);
        double xMax = list.get(list.size() - 1);

        double length = (list.get(list.size() - 1) - list.get(0)) / K ;
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
