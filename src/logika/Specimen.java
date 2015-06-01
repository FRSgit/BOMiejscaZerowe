package logika;

import java.util.LinkedList;

/**
 * Created by Kamil on 2015-04-20.
 */
public class Specimen implements Comparable<Specimen>{

    public LinkedList<Double> arguments;

    /**
     * O jak¹ czêœæ argumentu argument ulegnie zmianie (zwiêkszy siê lub zmniejszy)
     */
    public Double mutationFactor;

    /**
     * Wartoœæ jak¹ function przyjmuje dla argumentów osobnika (ustawiana przez klasê Populacja)
     */
    public Double value;
    /**
     * Szansa mutacji wyra¿ona w iloœci procent
     */
    public int probabilityOfMutation;

    private final Double standardMutationFactor = 0.2;
    private final int standardProbabilityOfMutation = 10;

    public Specimen() {
        initialize(new LinkedList<>(), standardMutationFactor, standardProbabilityOfMutation);
    }

    public Specimen(LinkedList<Double> arguments) {
        initialize(arguments, standardMutationFactor, standardProbabilityOfMutation);
    }

    public Specimen(LinkedList<Double> arguments, Double wspolczynnikMutowania, int probabilityOfMutation) {
        initialize(arguments, wspolczynnikMutowania, probabilityOfMutation);
    }

    private void initialize(LinkedList<Double> argumenty, Double wspolczynnikMutowania, int szansaMutacji) {
        this.arguments = argumenty;
        this.mutationFactor = wspolczynnikMutowania;
        this.probabilityOfMutation = szansaMutacji;
    }

    public void mutate() {
        for(int i = 0; i < arguments.size(); ++i) {
            if (RandomNumbersGenerator.generateBoolean()) {
                double argument = arguments.get(i);
                if (RandomNumbersGenerator.generateBoolean()) {
                    arguments.set(i, (argument*(1+ mutationFactor)));
                } else {
                    arguments.set(i, (argument*(1- mutationFactor)));
                }
            }
        }
    }

    public boolean shouldMutate() {
        int rzut = RandomNumbersGenerator.generateInteger(1, 100);
        return (rzut <= probabilityOfMutation);
    }

    public Specimen crossover(Specimen specimen) {
        LinkedList<Double> argumentyDziecka = new LinkedList<>();
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
		        }else{
		        	childArg = Double.longBitsToDouble(Long.parseLong(childGenome, 2));
		        }
        	}while(
        		new Double(childArg).toString().indexOf('E')!=-1
        		&&
        		Math.abs(Integer.parseInt(new Double(childArg).toString().split("E")[1])) > 128
        	);
        	argumentyDziecka.add(childArg);
        }
        Specimen dziecko = new Specimen(argumentyDziecka);
        if (dziecko.shouldMutate()) {
            dziecko.mutate();
        }
        return dziecko;
    }

    public int compareTo(Specimen o) {
        double wartoscOsobnika = Math.abs(o.value);
        double wartoscThisOsobnika = Math.abs(this.value);
        if (wartoscThisOsobnika < wartoscOsobnika) {
            return 1;
        } else if (wartoscThisOsobnika == wartoscOsobnika) {
            return 0;
        } else {
            return -1;
        }
    }

    public void print() {
        System.out.print("value: " + value + ", arguments: ");
        for (Double argument : arguments) {
            System.out.print(argument);
            if (!arguments.getLast().equals(argument)) {
                System.out.print(", ");
            } else {
                System.out.print(".");
            }
        }
        System.out.println();
    }
}
