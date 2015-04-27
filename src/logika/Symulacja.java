package logika;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Kamil on 2015-04-20.
 */
public class Symulacja {
	private
		int liczbaOsobnikow;
	    double min;
	    double max;
	    double epsilon = 0.1;
	    int liczbaArgumentow;
	    int liczbaPokolen;
	    Scanner scan;
	    
	private String getUserInput(String msg){
		String result = null;
		System.out.print(msg+"\n");
		result = scan.next();
		return result;
	}

	private void getVariables(){
		scan = new Scanner(System.in);
		System.out.print("Symulacja 1.05323c night build BO523212 rev. 3\n");
		liczbaOsobnikow = Integer.parseInt(getUserInput("Podaj pocz¹tkow¹ liczbê osobników:"));
		min = Double.parseDouble(getUserInput("Podaj doln¹ granice badanego przedzia³u:"));
		max = Double.parseDouble(getUserInput("Podaj górn¹ granice badanego przedzia³u:"));
		liczbaPokolen = Integer.parseInt(getUserInput("Podaj liczbê pokoleñ:"));
		liczbaArgumentow = Integer.parseInt(getUserInput("Podaj liczbê zmiennych w funkcji:"));
		scan.close();
	}
	
    public void wlaczMenu(){
    	//getVariables();
        int liczbaOsobnikow = 1000;
        double min = -10000;
        double max = 10000;
        double epsilon = 0.1;
        int liczbaArgumentow = 3;
        int liczbaPokolen = 100;

        Populacja pop = new Populacja(liczbaPokolen, epsilon, liczbaOsobnikow, liczbaArgumentow, min, max);
        LinkedList<Double> x = new LinkedList<>();
        LinkedList<Double> y = new LinkedList<>();
        LinkedList<Double> z = new LinkedList<>();

        x.add(new Double(-5));
        x.add(new Double(12));
        x.add(new Double(-3.29));
        y.add(new Double(7.20));
        y.add(new Double(-103.52));
        z.add(new Double(10242.3));
        z.add(new Double(742.283));
        z.add(new Double(-2112.44));
        z.add(new Double(823.2));
        LinkedList<LinkedList<Double>>  fnc = new LinkedList<>();
        fnc.add(x);
        fnc.add(y);
        fnc.add(z);
        FunkcjaWieluZmiennych fncwz = new FunkcjaWieluZmiennych(fnc);

        //fncwz = wczytajFunkcjeOdUzytkownika();

        pop.funkcjaWieluZmiennych = fncwz;

        Osobnik osId = pop.szukajOsobnikaIdealnego();
        System.out.print("Znaleziony osobnik: ");
        osId.wypisz();
    }

    public FunkcjaWieluZmiennych wczytajFunkcjeOdUzytkownika() {
        LinkedList<LinkedList<Double>> funkcja = new LinkedList<LinkedList<Double>>();
        Scanner in = new Scanner(System.in);
        System.out.print("\nPodaj ilosc zmiennych funkcji: ");
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

    public static void printList(LinkedList<Object> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static void printOsobniks(LinkedList<Osobnik> list) {
        for (Osobnik o : list) {
            System.out.println(o.wartosc);
        }
    }
}
