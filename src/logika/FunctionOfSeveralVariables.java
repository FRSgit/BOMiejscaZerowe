package logika;

import java.util.LinkedList;

/**
 * @author Kamil
 * @version 1.00, 04/20/2015
 */
public class FunctionOfSeveralVariables {
    /**
     * Lista list określająca całą funkcję.
     *
     * W pierwszym wymiarze odpowiada ilości argumentów funkcji, w drugim współczynnikom dla
     * każdego z argumentu. Np. f(x, y) = x^2 + x + y, to lista function będzie dwuelementowa.
     * Pierwszy z jej elementów to będzie lista trzyelementowa (x0, x1, x2), a drugi to będzie
     * lista dwuelementowa (y0, y1).
     */
    public LinkedList<LinkedList<Double>> function;

    public FunctionOfSeveralVariables(LinkedList<LinkedList<Double>> function) {
        this.function = function;
    }
    
    public FunctionOfSeveralVariables(String function) {
        this.function = fromString(function);
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
    
    public LinkedList<LinkedList<Double>> fromString(String func){
        //TODO: fromString
        return new LinkedList<LinkedList<Double>>();
    }
    @Override
    public String toString(){
        //TODO: toString
        return "x+y";
    }
}
