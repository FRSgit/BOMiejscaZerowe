package logika;

import java.util.LinkedList;

/**
 * Created by Kamil on 2015-04-20.
 */
public class FunkcjaWieluZmiennych {
    /**
     * Lista list okre�laj�ca ca�� funkcj�.
     *
     * W pierwszym wymiarze odpowiada ilo�ci argument�w funkcji, w drugim wsp�czynnikom dla
     * ka�dego z argumentu. Np. f(x, y) = x^2 + x + y, to lista funkcja b�dzie dwuelementowa.
     * Pierwszy z jej element�w to b�dzie lista trzyelementowa (x0, x1, x2), a drugi to b�dzie
     * lista dwuelementowa (y0, y1).
     */
    public LinkedList<LinkedList<Double>> funkcja;

    public FunkcjaWieluZmiennych(LinkedList<LinkedList<Double>> funkcja) {
        this.funkcja = funkcja;
    }

    public double zwrocWartosc(LinkedList<Double> argumenty) {
        double wynik = 0;
        for (int i = 0; i < funkcja.size(); ++i) {
            double x = argumenty.get(i);
            LinkedList<Double> wspolczynniki = funkcja.get(i);
            for (int j = 0; j < wspolczynniki.size(); ++j) {
                double wspolczynnik = wspolczynniki.get(j);
                wynik += Math.pow(x, j) * wspolczynnik;
            }
        }
        return  wynik;
    }
}
