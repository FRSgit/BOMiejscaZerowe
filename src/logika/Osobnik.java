package logika;

import java.util.LinkedList;

/**
 * Created by Kamil on 2015-04-20.
 */
public class Osobnik implements Comparable<Osobnik>{

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

    private final Double standardowyWspolczynnikMutacji = 0.2;
    private final int standardowaSzansaMutacji = 80;

    public Osobnik() {
        inicjalizuj(new LinkedList<>(), standardowyWspolczynnikMutacji, standardowaSzansaMutacji);
    }

    public  Osobnik(LinkedList<Double> argumenty) {
        inicjalizuj(argumenty, standardowyWspolczynnikMutacji, standardowaSzansaMutacji);
    }

    public Osobnik(LinkedList<Double> argumenty, Double wspolczynnikMutowania, int szansaMutacji) {
        inicjalizuj(argumenty, wspolczynnikMutowania, szansaMutacji);
    }

    private void inicjalizuj(LinkedList<Double> argumenty, Double wspolczynnikMutowania, int szansaMutacji) {
        this.argumenty = argumenty;
        this.wspolczynnikMutacji = wspolczynnikMutowania;
        this.szansaMutacji = szansaMutacji;
    }

    public void mutuj() {
        for(int i = 0; i < argumenty.size(); ++i) {
            if (GeneratorLiczbLosowych.generujBoolean()) {
                double argument = argumenty.get(i);
                if (GeneratorLiczbLosowych.generujBoolean()) {
                    argumenty.set(i, (argument*(1+wspolczynnikMutacji)));
                } else {
                    argumenty.set(i, (argument*(1-wspolczynnikMutacji)));
                }
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

    public int compareTo(Osobnik o) {
        double wartoscOsobnika = Math.abs(o.wartosc);
        double wartoscThisOsobnika = Math.abs(this.wartosc);
        if (wartoscThisOsobnika < wartoscOsobnika) {
            return 1;
        } else if (wartoscThisOsobnika == wartoscOsobnika) {
            return 0;
        } else {
            return -1;
        }
    }

    public void wypisz() {
        System.out.print("wartosc: " + wartosc + ", argumenty: ");
        for (Double argument : argumenty) {
            System.out.print(argument);
            if (!argumenty.getLast().equals(argument)) {
                System.out.print(", ");
            } else {
                System.out.print(".");
            }
        }
        System.out.println();
    }
}
