package logika;

import java.util.LinkedList;

/**
 * @author Kamil
 * @version 1.00, 04/20/2015
 */
public class SpecimenGenerator {
    int liczbaOsobnikow;
    int liczbaArgumentow;
    double min;
    double max;

    public SpecimenGenerator(int liczbaOsobnikow, int liczbaArgumentow, double min, double max) {
        this.liczbaOsobnikow = liczbaOsobnikow;
        this.liczbaArgumentow = liczbaArgumentow;
        this.min = min;
        this.max = max;
    }

    public LinkedList<Specimen> generuj() {
        LinkedList<Specimen> listaOsobnikow = new LinkedList<Specimen>();
        for (int i = 0; i < liczbaOsobnikow; ++i) {
            LinkedList<Double> argumenty = new LinkedList<Double>();
            for (int j = 0; j < liczbaArgumentow; ++j) {
                argumenty.add(RandomNumbersGenerator.generateDouble(min, max));
            }
            listaOsobnikow.add(new Specimen(argumenty));
        }
        return listaOsobnikow;
    }
}
