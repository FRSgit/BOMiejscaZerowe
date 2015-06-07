package logika;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author FRS
 */
public final class Simulation extends javax.swing.JPanel {
    int specimens;
    double min;
    double max;
    double epsilon;
    int numberOfArguments;
    int numberOfGenerations;
    Population population;
    FunctionOfSeveralVariables fncwz;
    
    /**
     * Creates new form Simulation
     */
    public Simulation() {
        specimens = 1000;
        min = -10000;
        max = 10000;
        epsilon = 5 * Math.pow(10, -10);
        numberOfArguments = 3;
        numberOfGenerations = 100;
        
        fncwz = getFunctionFromUser();
        
        initComponents();
        
    }
    
    /**
     * Creates new form Simulation
     */
    public Simulation(int _specimens, double _min, double _max, double _epsilon, int _numberOfArguments, int _numberOfGenerations, FunctionOfSeveralVariables _fncwz) {
        specimens = 1000;
        min = -10000;
        max = 10000;
        epsilon = 5 * Math.pow(10, -10);
        numberOfArguments = 3;
        numberOfGenerations = 100;
        
        fncwz = getFunctionFromUser();

        initComponents();

    }
    
    /**
     * Start simulation
     */
    public void start(){
        System.out.print(mainScrollPane.getWidth()+"\n\n");
        population = new Population(fncwz, specimens, min, max); 
        
        Specimen foundSpecimen = findRoot();
        
        mainScrollPane.setViewportView(population);
        population.setPreferredSize(new Dimension(population.getWidth(), population.getHeight()));
        mainScrollPane.revalidate();
        mainScrollPane.repaint();
        
        
        System.out.print("Znaleziony osobnik: ");
        foundSpecimen.print();
    }
    
    public void openBestSpecimenModal(Specimen bestSpecimen){
        //TODO:
        JDialog argumentsModal = new JDialog();
        JLabel title = new JLabel("Function arguments", JLabel.CENTER);
        JButton closeButton = new JButton("Close");
        //JTextArea bestArguments = new JTextArea(bestSpecimen.print());

        argumentsModal.setPreferredSize(new java.awt.Dimension(400, 150));
        
        argumentsModal.getContentPane().add(title);
        //argumentsModal.getContentPane().add(bestArguments);
        argumentsModal.getContentPane().add(closeButton, BorderLayout.PAGE_END);
        
        add(argumentsModal);
        revalidate();
        repaint();
    }
    
    public FunctionOfSeveralVariables getFunctionFromUser() {
        /*LinkedList<LinkedList<Double>> function = new LinkedList<LinkedList<Double>>();
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
            function.add(functionFactors);
        }
        return new FunctionOfSeveralVariables(function);*/
        
        LinkedList<Double> x = new LinkedList<Double>();
        LinkedList<Double> y = new LinkedList<Double>();
        LinkedList<Double> z = new LinkedList<Double>();

        x.add(new Double(-59));
        x.add(new Double(120));
        x.add(new Double(-47.29));
        y.add(new Double(99.20));
        y.add(new Double(-103.52));
        z.add(new Double(100.3));
        z.add(new Double(33.283));
        z.add(new Double(-173.44));
        z.add(new Double(83.2));

        LinkedList<LinkedList<Double>> fnc = new LinkedList<LinkedList<Double>>();
        fnc.add(x);
        fnc.add(y);
        fnc.add(z);
        return new FunctionOfSeveralVariables(fnc);
    }

    public Specimen findRoot() {
        Specimen currentBestSpecimen = new Specimen();
        for(int i = 1; i < numberOfGenerations; i++){
            population.nextGeneration(i);
            
            System.out.print("Pokolenie no " + i + ", aktualny najlepszy osobnik: ");
            currentBestSpecimen = population.getBestSpecimen();
            currentBestSpecimen.print();
            if (Math.abs(currentBestSpecimen.value) < epsilon)
                break;
        }
        return currentBestSpecimen;
    }

    public static void printList(LinkedList<Object> list) {
        for (Object obj : list)
            System.out.println(obj);
    }

    public static void printSpecimens(LinkedList<Specimen> list) {
        for (Specimen o : list)
            System.out.println(o.value);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainScrollPane = new javax.swing.JScrollPane();
        jToolBar1 = new javax.swing.JToolBar();
        jProgressBar1 = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();

        setMinimumSize(new java.awt.Dimension(0, 287));
        setPreferredSize(new java.awt.Dimension(580, 287));

        mainScrollPane.setToolTipText("");
        mainScrollPane.setOpaque(false);
        mainScrollPane.setPreferredSize(new java.awt.Dimension(560, 240));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setMinimumSize(new java.awt.Dimension(82, 34));
        jToolBar1.setPreferredSize(new java.awt.Dimension(100, 34));

        jProgressBar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        jProgressBar1.setEnabled(false);
        jProgressBar1.setMaximumSize(new java.awt.Dimension(32767, 34));
        jProgressBar1.setMinimumSize(new java.awt.Dimension(20, 34));
        jProgressBar1.setPreferredSize(new java.awt.Dimension(530, 34));
        jToolBar1.add(jProgressBar1);

        jButton1.setText("◙");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMaximumSize(new java.awt.Dimension(30, 34));
        jButton1.setMinimumSize(new java.awt.Dimension(30, 34));
        jButton1.setPreferredSize(new java.awt.Dimension(30, 34));
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        jToggleButton1.setText(">");
        jToggleButton1.setFocusable(false);
        jToggleButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton1.setMaximumSize(new java.awt.Dimension(30, 34));
        jToggleButton1.setMinimumSize(new java.awt.Dimension(30, 34));
        jToggleButton1.setPreferredSize(new java.awt.Dimension(30, 34));
        jToggleButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jToggleButton1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        start();
    }//GEN-LAST:event_jToggleButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JScrollPane mainScrollPane;
    // End of variables declaration//GEN-END:variables
}
