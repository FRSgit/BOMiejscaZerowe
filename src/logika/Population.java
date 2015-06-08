package logika;

import java.awt.Component;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author FRS
 */
public final class Population extends javax.swing.JPanel {

    final double probabilityOfSurvival = 0.2;

    public LinkedList<Specimen> specimens = new LinkedList<Specimen>();
    public FunctionOfSeveralVariables function;
        
    /**
     * Creates new object & form Population
     * @param function function of several variables which root values we're looking for
     * @param numberOfGeneratedSpecimens int - how many specimens should be created
     * @param min double - min limit of range in which we are looking for root value
     * @param max double - max limit of range in which we are looking for root value
     */
    
    public Population(FunctionOfSeveralVariables function, int numberOfGeneratedSpecimens, double min, double max) {
        this.function = function;
        generate(numberOfGeneratedSpecimens, min, max);
        initComponents();
    }

    public Population(LinkedList<Specimen> specimens, FunctionOfSeveralVariables function) {
        this.function = function;
        this.specimens = specimens;
        initComponents();
    }
	
    public void generate(int numberOfGeneratedSpecimens, double min, double max){
        SpecimenGenerator generator = new SpecimenGenerator(numberOfGeneratedSpecimens, function.function.size(),min,max);
        specimens = generator.generuj();
    }

    public void assignValuesToSpecimens(){
        for(Specimen specimen : specimens)
           specimen.value = function.calculateValue(specimen.arguments);
    }

    private void sortSpecimens() {
        Collections.sort(specimens);
    }
	
    public void nextGeneration(int noOfGeneration){
        LinkedList<Specimen> newGeneration = new LinkedList<Specimen>();
        int numberOfSpecimens = specimens.size();
        for(int i = 0; i < specimens.size(); i++){
            for(int j = 0; j<1/ probabilityOfSurvival; j++){
                int secondParent = RandomNumbersGenerator.generateInteger(0, numberOfSpecimens - 1);
                while(secondParent == i)
                    secondParent = RandomNumbersGenerator.generateInteger(0, numberOfSpecimens - 1);
                Specimen child = specimens.get(i).crossover(specimens.get(secondParent));
                newGeneration.add(child);
            }
        }
        specimens = newGeneration;
        assignValuesToSpecimens();
        sortSpecimens();
        clearPopulationOfWeakSpecimens();
        GenerationGraphic generationGraphic = new GenerationGraphic(noOfGeneration, specimens.size(), getBestSpecimen());

        setSize(noOfGeneration * (generationGraphic.getWidth() + 20), generationGraphic.getHeight() + 40);

        Component addedGeneration = add(generationGraphic);
        addedGeneration.setLocation(noOfGeneration * 10 + (noOfGeneration - 1) * (generationGraphic.getWidth() + 10), 20 );
    }

    private void killNumberOfSpecimens(double numberToBeKilled) {
        for (int i = 0; i < numberToBeKilled; ++i)
            specimens.removeFirst();
    }

    public Specimen getBestSpecimen() {
        return specimens.getLast();
    }

    private void clearPopulationOfWeakSpecimens() {
        double numberOfSpecimensToBeKilled = specimens.size() * (1 - probabilityOfSurvival);
        killNumberOfSpecimens(numberOfSpecimensToBeKilled);
    }

    public void printListOfSpecimens(LinkedList<Specimen> list) {
        for (Specimen o : list)
            o.print();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(32767, 240));
        setMinimumSize(new java.awt.Dimension(580, 240));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
