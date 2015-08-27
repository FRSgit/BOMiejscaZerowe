package logika;

import java.util.LinkedList;

/**
 * @author Kamil
 * @version 1.00, 04/20/2015
 */
public class SpecimenGenerator {
    int numberOfSpecimens;
    int numberOfArguments;
    LinkedList<String> namesOfArguments;
    double min;
    double max;

    public SpecimenGenerator(int numberOfSpecimens, int numberOfArguments, LinkedList<String> namesOfArguments, double min, double max) {
        this.numberOfSpecimens = numberOfSpecimens;
        this.numberOfArguments = numberOfArguments;
        this.namesOfArguments = namesOfArguments;
        this.min = min;
        this.max = max;
    }

    public LinkedList<Specimen> generuj() {
        LinkedList<Specimen> specimensList = new LinkedList<Specimen>();
        for (int i = 0; i < numberOfSpecimens; ++i) {
            LinkedList<Double> argumenty = new LinkedList<Double>();
            for (int j = 0; j < numberOfArguments; ++j) {
                argumenty.add(RandomNumbersGenerator.generateDouble(min, max));
            }
            specimensList.add(new Specimen(argumenty, namesOfArguments));
        }
        return specimensList;
    }
}
