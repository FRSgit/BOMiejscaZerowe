package logika;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Comparator;
/**
 * Created by Beata on 2015-04-21.
 */
public class Populacja {
	
	final double szansaPrzezycia = 0.2;
	
	int liczbaPokolen;
	double epsilion;
    int liczbaOsobnikow;
    int liczbaArgumentow;
    double min;
    double max;
    Osobnik osobnikIdealny;
    
	public LinkedList<Osobnik> listaOsobnikow = new LinkedList<Osobnik>();
	public FunkcjaWieluZmiennych funkcjaWieluZmiennych;
	
	public Populacja(int liczbaPokolen, double epsilion, int liczbaOsobnikow,int liczbaArgumentow,double min,double max){
		this.liczbaPokolen = liczbaPokolen;
		this.epsilion = epsilion;
		this.liczbaOsobnikow = liczbaOsobnikow;
		this.liczbaArgumentow = liczbaArgumentow;
		this.min = min;
		this.max = max;
		generuj();
	}
	
	public void generuj(){
		GeneratorOsobnikow generator = new GeneratorOsobnikow(liczbaOsobnikow,liczbaArgumentow,min,max);
		listaOsobnikow = generator.generuj();
	}
	
	public void przypiszWartosc(LinkedList<Osobnik> lista){
		for(int i = 0; i<lista.size(); i++){
			lista.get(i).wartosc = funkcjaWieluZmiennych.zwrocWartosc(lista.get(i).argumenty);
			if(lista.get(i).wartosc < epsilion){
				osobnikIdealny.wartosc = lista.get(i).wartosc;
				osobnikIdealny.argumenty = (LinkedList<Double>) lista.get(i).argumenty.clone();
				break;
			}
		}
	}
	
	public void sortuj(LinkedList<Osobnik> sortListaOsobnikow){
		//Collections.sort(sortListaOsobnikow.Osobnik.wartosc);

		
	}
	
	public LinkedList<Osobnik> krzyzowanie(){
		LinkedList<Osobnik> nowePokolenie = new LinkedList<Osobnik>();
		for(int i = 0; i < listaOsobnikow.size(); i++){
			for(int j = 0; j<5; j++){
				Osobnik dziecko = listaOsobnikow.get(i).krzyzuj(listaOsobnikow.get(GeneratorLiczbLosowych.generujCalkowita(0, liczbaOsobnikow)));
				nowePokolenie.add(dziecko);
			}
		}
		przypiszWartosc(nowePokolenie);
		sortuj(nowePokolenie);
		for(int i = nowePokolenie.size(); i>(nowePokolenie.size()/szansaPrzezycia);i--){
			nowePokolenie.removeLast();
		}
		
		return nowePokolenie;
	}
	
	public Osobnik szukajOsobnikaIdealnego(){
		przypiszWartosc(listaOsobnikow);
		for(int i = 0; i < liczbaPokolen-1;i++){
			if(osobnikIdealny.wartosc > epsilion){
				listaOsobnikow = (LinkedList) krzyzowanie().clone();
			}else{
				return osobnikIdealny;
			}
		}
		return osobnikIdealny;
	}
}
