package logika;

import java.util.LinkedList;

/**
 * Created by Kamil on 2015-04-20.
 */
public class GeneratorOsobnikow {
    int liczbaOsobnikow;
    int liczbaArgumentow;
    double min;
    double max;

    public GeneratorOsobnikow(int liczbaOsobnikow, int liczbaArgumentow, double min, double max) {
        this.liczbaOsobnikow = liczbaOsobnikow;
        this.liczbaArgumentow = liczbaArgumentow;
        this.min = min;
        this.max = max;
    }

    public LinkedList<Osobnik> generuj() {
        LinkedList<Osobnik> listaOsobnikow = new LinkedList<Osobnik>();
        for (int i = 0; i < liczbaOsobnikow; ++i) {
            LinkedList<Double> argumenty = new LinkedList<Double>();
            for (int j = 0; j < liczbaArgumentow; ++j) {
                argumenty.add(GeneratorLiczbLosowych.generujRzeczywista(min, max));
            }
            listaOsobnikow.add(new Osobnik(argumenty));
        }
        return listaOsobnikow;
    }
}
