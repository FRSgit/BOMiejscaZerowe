package logika;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Kamil on 2015-04-20.
 */
public class Symulacja {
    public FunkcjaWieluZmiennych wczytajFunkcjeOdUzytkownika() {
        LinkedList<LinkedList<Double>> funkcja = new LinkedList<LinkedList<Double>>();
        Scanner in = new Scanner(System.in);
        System.out.print("\nPodaj ilosc zmiennych fukcji: ");
        int iloscArgumentow = in.nextInt();
        for (int i = 0; i < iloscArgumentow; ++i) {
            LinkedList<Double> wspolczynniki = new LinkedList<Double>();
            System.out.print("\nPodaj najwyzsza potege zmiennej nr " + Integer.toString(i + 1) + ": ");
            int iloscWspolczynnikow = in.nextInt() + 1;
            for (int j = 0; j < iloscWspolczynnikow; ++j) {
                System.out.print("\nPodaj wspolczynnik przy zmiennej nr " + Integer.toString(i+1) + " do potegi " + j + ": ");
                wspolczynniki.add(in.nextDouble());
            }
            funkcja.add(wspolczynniki);
        }
        return new FunkcjaWieluZmiennych(funkcja);
    }
}
