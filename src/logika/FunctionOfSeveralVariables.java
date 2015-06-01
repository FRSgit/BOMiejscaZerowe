package logika;

import java.util.LinkedList;

/**
 * @author Kamil
 * @version 1.00, 04/20/2015
 */
public class FunctionOfSeveralVariables {
    /**
     * Lista list okreœlaj¹ca ca³¹ funkcjê.
     *
     * W pierwszym wymiarze odpowiada iloœci argumentów funkcji, w drugim wspó³czynnikom dla
     * ka¿dego z argumentu. Np. f(x, y) = x^2 + x + y, to lista function bêdzie dwuelementowa.
     * Pierwszy z jej elementów to bêdzie lista trzyelementowa (x0, x1, x2), a drugi to bêdzie
     * lista dwuelementowa (y0, y1).
     */
    public LinkedList<LinkedList<Double>> function;

    public FunctionOfSeveralVariables(LinkedList<LinkedList<Double>> function) {
        this.function = function;
    }

    public double calculateValue(LinkedList<Double> arguments) {
        double wynik = 0;
        for (int i = 0; i < function.size(); ++i) {
            double x = arguments.get(i);
            LinkedList<Double> wspolczynniki = function.get(i);
            for (int j = 0; j < wspolczynniki.size(); ++j) {
                double wspolczynnik = wspolczynniki.get(j);
                wynik += Math.pow(x, j) * wspolczynnik;
            }
        }
        return  wynik;
    }
}
