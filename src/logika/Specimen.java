package logika;

import java.util.LinkedList;

/**
 * Created by Kamil on 2015-04-20.
 */
public class Specimen implements Comparable<Specimen>{

    public LinkedList<Double> arguments;

    /**
     * O jaką część argumentu argument ulegnie zmianie (zwiększy się lub zmniejszy)
     */
    public Double mutationFactor;

    /**
     * Wartość jaką function przyjmuje dla argumentów osobnika (ustawiana przez klasą Populacja)
     */
    public Double value;
    /**
     * Szansa mutacji wyrażona w ilości procent
     */
    public int probabilityOfMutation;

    private final Double standardMutationFactor = 0.2;
    private final int standardProbabilityOfMutation = 10;

    public Specimen() {
        initialize(new LinkedList<Double>(), standardMutationFactor, standardProbabilityOfMutation);
    }

    public Specimen(LinkedList<Double> arguments) {
        initialize(arguments, standardMutationFactor, standardProbabilityOfMutation);
    }

    public Specimen(LinkedList<Double> arguments, Double mutationFactor, int probabilityOfMutation) {
        initialize(arguments, mutationFactor, probabilityOfMutation);
    }

    private void initialize(LinkedList<Double> arguments, Double mutationFactor, int probabilityOfMutation) {
        this.arguments = arguments;
        this.mutationFactor = mutationFactor;
        this.probabilityOfMutation = probabilityOfMutation;
    }

    public void mutate() {
        for(int i = 0; i < arguments.size(); ++i) {
            if (RandomNumbersGenerator.generateBoolean()) {
                double argument = arguments.get(i);
                if (RandomNumbersGenerator.generateBoolean())
                    arguments.set(i, (argument*(1+ mutationFactor)));
                else
                    arguments.set(i, (argument*(1- mutationFactor)));
            }
        }
    }

    public boolean shouldMutate() {
        int randInt = RandomNumbersGenerator.generateInteger(1, 100);
        return (randInt <= probabilityOfMutation);
    }

    public Specimen crossover(Specimen specimen) {
        LinkedList<Double> childArguments = new LinkedList<Double>();
        for (int i = 0; i < arguments.size(); ++i){
        	String childGenome;
        	String XGenome = Long.toBinaryString(Double.doubleToRawLongBits(this.arguments.get(i)));
        	String YGenome = Long.toBinaryString(Double.doubleToRawLongBits(specimen.arguments.get(i)));
        	int shorterGenome = (YGenome.length()>XGenome.length())?XGenome.length():YGenome.length();
        	double childArg = 0;
        	do{
                    childGenome = "";
                    for(int j = 0; j < shorterGenome; j++)
                        childGenome += (RandomNumbersGenerator.generateBoolean())?XGenome.charAt(j):YGenome.charAt(j);
                    
	    	    if(childGenome.length() == 64 && childGenome.charAt(0) == '1') {
	                String negBinStr = childGenome.substring(1);
	                childArg = -1 * Double.longBitsToDouble(Long.parseLong(negBinStr, 2));
		    }else
                        childArg = Double.longBitsToDouble(Long.parseLong(childGenome, 2));
        	}while(
                    Double.toString(childArg).indexOf('E')!=-1
                    &&
                    Math.abs(Integer.parseInt(Double.toString(childArg).split("E")[1])) > 128
        	);
        	childArguments.add(childArg);
        }
        Specimen child = new Specimen(childArguments);
        if (child.shouldMutate())
            child.mutate();
        return child;
    }

    public int compareTo(Specimen o) {
        double wartoscOsobnika = Math.abs(o.value);
        double wartoscThisOsobnika = Math.abs(this.value);
        if (wartoscThisOsobnika < wartoscOsobnika)
            return 1;
        else if (wartoscThisOsobnika == wartoscOsobnika)
            return 0;
        else
            return -1;
    }

    public void print() {;
        System.out.print("value: " + value + ", arguments: ");
        for (Double argument : arguments) {
            System.out.print(argument);
            if (!arguments.getLast().equals(argument))
                System.out.print(", ");
            else
                System.out.print(".");
        }
        System.out.println();
    }
    
    public String printArguments(){
        String result = new String();
        char tmpLetter = 'A';
        for (Double argument : arguments){
            result += "\n" + tmpLetter + " = " + argument ;
            tmpLetter++;
        }
        return result.substring(1);
    };
}
