package logika;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Beata on 2015-04-21.
 */
public class Populacja {
	
	final double szansaPrzezycia = 0.2;
	
	int liczbaPokolen;
	double epsilon;
    int liczbaOsobnikow;
    int liczbaArgumentow;
    double min;
    double max;
    Osobnik osobnikIdealny;
	Osobnik najlepszyOsobnik;
    
	public LinkedList<Osobnik> listaOsobnikow = new LinkedList<Osobnik>();
	public FunkcjaWieluZmiennych funkcjaWieluZmiennych;
	
	public Populacja(int liczbaPokolen, double epsilon, int liczbaOsobnikow,int liczbaArgumentow,double min,double max){
		this.liczbaPokolen = liczbaPokolen;
		this.epsilon = epsilon;
		this.liczbaOsobnikow = liczbaOsobnikow;
		this.liczbaArgumentow = liczbaArgumentow;
		this.min = min;
		this.max = max;
		osobnikIdealny = new Osobnik();
		osobnikIdealny.wartosc = 1000 + epsilon;
		generuj();
		najlepszyOsobnik = listaOsobnikow.getFirst();
	}
	
	public void generuj(){
		GeneratorOsobnikow generator = new GeneratorOsobnikow(liczbaOsobnikow,liczbaArgumentow,min,max);
		listaOsobnikow = generator.generuj();
	}
	
	public void przypiszWartosc(LinkedList<Osobnik> lista){
		for(Osobnik osobnik : lista){
			osobnik.wartosc = funkcjaWieluZmiennych.zwrocWartosc(osobnik.argumenty);
			if(Math.abs(osobnik.wartosc) < epsilon){
				osobnikIdealny.wartosc = osobnik.wartosc;
				osobnikIdealny.argumenty = (LinkedList<Double>) osobnik.argumenty.clone();
			} else if (osobnik.compareTo(najlepszyOsobnik) > 0) {
				najlepszyOsobnik = osobnik;
			}
		}
	}
	
	public void sortuj(LinkedList<Osobnik> sortListaOsobnikow) {
		Collections.sort(sortListaOsobnikow);
	}
	
	public LinkedList<Osobnik> krzyzowanie(){
		LinkedList<Osobnik> nowePokolenie = new LinkedList<Osobnik>();
		for(int i = 0; i < listaOsobnikow.size(); i++){
			for(int j = 0; j<1/szansaPrzezycia; j++){
				int secondParent = GeneratorLiczbLosowych.generujCalkowita(0, liczbaOsobnikow - 1);
				while(secondParent == i)
					secondParent = GeneratorLiczbLosowych.generujCalkowita(0, liczbaOsobnikow - 1);
				Osobnik dziecko = listaOsobnikow.get(i).krzyzuj(listaOsobnikow.get(secondParent));
				nowePokolenie.add(dziecko);
			}
		}
		przypiszWartosc(nowePokolenie);
		sortuj(nowePokolenie);
		double liczbaOsobnikowKtorzyZgina = nowePokolenie.size() * (1 - szansaPrzezycia);
		for(int i = 0; i < liczbaOsobnikowKtorzyZgina; ++i){
			nowePokolenie.removeFirst();
		}
		
		return nowePokolenie;
	}
	
	public Osobnik szukajOsobnikaIdealnego(){
		przypiszWartosc(listaOsobnikow);
		for(int i = 1; i < liczbaPokolen; i++){
			System.out.print("Pokolenie no " + i + ", aktualny najlepszy osobnik: ");
			najlepszyOsobnik.wypisz();
			if(osobnikIdealny.wartosc > epsilon){
				listaOsobnikow = (LinkedList) krzyzowanie().clone();
			}else{
				return osobnikIdealny;
			}
		}
		return najlepszyOsobnik;
	}

	public void printOsobniks(LinkedList<Osobnik> list) {
		for (Osobnik o : list) {
			o.wypisz();
		}
	}
}
