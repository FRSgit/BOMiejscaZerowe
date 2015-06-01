package logika;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Beata on 2015-04-21.
 */
public class Population {
	final double probabilityOfSurvival = 0.2;

	public LinkedList<Specimen> specimens = new LinkedList<>();
	public FunctionOfSeveralVariables function;

    public Population(FunctionOfSeveralVariables function, int numberOfGeneratedSpecimens, double min, double max) {
        this.function = function;
        generate(numberOfGeneratedSpecimens, min, max);
    }

    public Population(LinkedList<Specimen> specimens, FunctionOfSeveralVariables function) {
        this.function = function;
        this.specimens = specimens;
    }
	
	public void generate(int numberOfGeneratedSpecimens, double min, double max){
		SpecimenGenerator generator = new SpecimenGenerator(numberOfGeneratedSpecimens, function.function.size(),min,max);
		specimens = generator.generuj();
	}
	
	public void assignValuesToSpecimens(){
		for(Specimen specimen : specimens){
			specimen.value = function.calculateValue(specimen.arguments);
		}
	}

    private void sortSpecimens() {
        Collections.sort(specimens);
    }
	
	public void nextGeneration(){
		LinkedList<Specimen> nowePokolenie = new LinkedList<Specimen>();
        int liczbaOsobnikow = specimens.size();
		for(int i = 0; i < specimens.size(); i++){
			for(int j = 0; j<1/ probabilityOfSurvival; j++){
				int secondParent = RandomNumbersGenerator.generateInteger(0, liczbaOsobnikow - 1);
				while(secondParent == i)
					secondParent = RandomNumbersGenerator.generateInteger(0, liczbaOsobnikow - 1);
				Specimen dziecko = specimens.get(i).crossover(specimens.get(secondParent));
				nowePokolenie.add(dziecko);
			}
		}

        specimens = nowePokolenie;
        assignValuesToSpecimens();
        sortSpecimens();
        clearPopulationOfWeakSpecimens();
	}

	private void killNumberOfSpecimens(double numberToBeKilled) {
		for (int i = 0; i < numberToBeKilled; ++i) {
			specimens.removeFirst();
		}
	}

    public Specimen getBestSpecimen() {
        return specimens.getLast();
    }

	private void clearPopulationOfWeakSpecimens() {
		double numberOfSpecimensToBeKilled = specimens.size() * (1 - probabilityOfSurvival);
		killNumberOfSpecimens(numberOfSpecimensToBeKilled);
	}

	public void printListOfSpecimens(LinkedList<Specimen> list) {
		for (Specimen o : list) {
			o.print();
		}
	}
}
