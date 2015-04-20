package logika;

import java.util.LinkedList;

/**
 * Created by Kamil on 2015-04-20.
 */
public class Osobnik {

    public LinkedList<Double> argumenty;

    /**
     * O jak¹ czêœæ argumentu argument ulegnie zmianie (zwiêkszy siê lub zmniejszy)
     */
    public Double wspolczynnikMutacji;

    /**
     * Wartoœæ jak¹ funkcja przyjmuje dla argumentów osobnika (ustawiana przez klasê Populacja)
     */
    public Double wartosc;
    /**
     * Szansa mutacji wyra¿ona w iloœci procent
     */
    public int szansaMutacji;

    private final Double standardowyWspolczynnikMutacji = 0.1;
    private final int standardowaSzansaMutacji = 1;

    public Osobnik() {
        inicjalizuj(new LinkedList<Double>(), standardowyWspolczynnikMutacji, standardowaSzansaMutacji);
    }

    public  Osobnik(LinkedList<Double> argumenty) {
        inicjalizuj(argumenty, standardowyWspolczynnikMutacji, standardowaSzansaMutacji);
    }

    public Osobnik(LinkedList<Double> argumenty, Double wspolczynnikMutowania, int szansaMutacji) {
        this.inicjalizuj(argumenty, wspolczynnikMutowania, szansaMutacji);
    }

    private void inicjalizuj(LinkedList<Double> argumenty, Double wspolczynnikMutowania, int szansaMutacji) {
        this.argumenty = argumenty;
        this.wspolczynnikMutacji = wspolczynnikMutowania;
        this.szansaMutacji = szansaMutacji;
    }

    public void mutuj() {
        for(Double argument : argumenty) {
            if (GeneratorLiczbLosowych.generujBoolean()) {
                argument += argument * wspolczynnikMutacji;
            } else {
                argument -= argument * wspolczynnikMutacji;
            }
        }
    }

    public boolean czyMutowac() {
        int rzut = GeneratorLiczbLosowych.generujCalkowita(1, 100);
        return (rzut <= szansaMutacji);
    }

    public Osobnik krzyzuj(Osobnik osobnik) {
        LinkedList<Double> argumentyDziecka = new LinkedList<>();
        for (int i = 0; i < argumenty.size(); ++i) {
            if (GeneratorLiczbLosowych.generujBoolean()) {
                argumentyDziecka.add(this.argumenty.get(i));
            } else {
                argumentyDziecka.add(osobnik.argumenty.get(i));
            }
        }
        Osobnik dziecko = new Osobnik(argumentyDziecka);
        if (dziecko.czyMutowac()) {
            dziecko.mutuj();
        }
        return dziecko;
    }
}
