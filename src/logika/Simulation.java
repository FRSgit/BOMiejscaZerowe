package logika;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Kamil on 2015-04-20.
 */
public class Simulation {
    int specimens;
    double min;
    double max;
    double epsilon = 0.1;
    int numberOfArguments;
    int numberOfGenerations;
    Scanner scanner;
    Population population;


    private String getUserInput(String msg) {
        String result = null;
        System.out.print(msg + "\n");
        result = scanner.next();
        return result;
    }

    private void getVariables() {
        scanner = new Scanner(System.in);
        //System.out.print("Symulacja 1.05323c night build BO523212 rev. 3\n");
        specimens = Integer.parseInt(getUserInput("Podaj pocz¹tkow¹ liczbê osobników:"));
        min = Double.parseDouble(getUserInput("Podaj doln¹ granice badanego przedzia³u:"));
        max = Double.parseDouble(getUserInput("Podaj górn¹ granice badanego przedzia³u:"));
        numberOfGenerations = Integer.parseInt(getUserInput("Podaj liczbê pokoleñ:"));
        numberOfArguments = Integer.parseInt(getUserInput("Podaj liczbê zmiennych w funkcji:"));
        scanner.close();
    }

    public void menu() {
        //getVariables();
        specimens = 1000;
        min = -10000;
        max = 10000;
        epsilon = 5 * Math.pow(10, -10);
        numberOfArguments = 3;
        numberOfGenerations = 100;

        //FunctionOfSeveralVariables fncwz = getTestFunction();
        FunctionOfSeveralVariables fncwz = getFunctionFromUser();

        population = new Population(fncwz, specimens, min, max);
        Specimen foundSpecimen = findRoot();
        System.out.print("Znaleziony osobnik: ");
        foundSpecimen.print();
    }

    public FunctionOfSeveralVariables getFunctionFromUser() {
        LinkedList<LinkedList<Double>> funkcja = new LinkedList<LinkedList<Double>>();
        Scanner in = new Scanner(System.in);
        System.out.print("\nPodaj ilosc zmiennych funkcji: ");
        numberOfArguments = in.nextInt();
        for (int i = 0; i < numberOfArguments; ++i) {
            LinkedList<Double> functionFactors = new LinkedList<Double>();
            System.out.print("\nPodaj najwyzsza potege zmiennej nr " + Integer.toString(i + 1) + ": ");
            int numberOfFactors = in.nextInt() + 1;
            for (int j = 0; j < numberOfFactors; ++j) {
                System.out.print("\nPodaj wspolczynnik przy zmiennej nr " + Integer.toString(i + 1) + " do potegi " + j + ": ");
                functionFactors.add(in.nextDouble());
            }
            funkcja.add(functionFactors);
        }
        return new FunctionOfSeveralVariables(funkcja);
    }

    public Specimen findRoot() {
        Specimen currentBestSpecimen = new Specimen();
        for(int i = 1; i < numberOfGenerations; i++){
            population.nextGeneration();
            System.out.print("Pokolenie no " + i + ", aktualny najlepszy osobnik: ");
            currentBestSpecimen = population.getBestSpecimen();
            currentBestSpecimen.print();
            if (Math.abs(currentBestSpecimen.value) < epsilon)
            {
                break;
            }
        }
        return currentBestSpecimen;
    }

    public static void printList(LinkedList<Object> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static void printSpecimens(LinkedList<Specimen> list) {
        for (Specimen o : list) {
            System.out.println(o.value);
        }
    }

    private FunctionOfSeveralVariables getTestFunction() {
        LinkedList<Double> x = new LinkedList<>();
        LinkedList<Double> y = new LinkedList<>();
        LinkedList<Double> z = new LinkedList<>();

        x.add(new Double(-59));
        x.add(new Double(120));
        x.add(new Double(-47.29));
        y.add(new Double(99.20));
        y.add(new Double(-103.52));
        z.add(new Double(100.3));
        z.add(new Double(33.283));
        z.add(new Double(-173.44));
        z.add(new Double(83.2));

        LinkedList<LinkedList<Double>> fnc = new LinkedList<>();
        fnc.add(x);
        fnc.add(y);
        fnc.add(z);
        return new FunctionOfSeveralVariables(fnc);
    }
}
