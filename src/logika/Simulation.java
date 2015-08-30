package logika;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Arrays;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import net.objecthunter.exp4j.*;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 *
 * @author FRS
 */
public final class Simulation extends javax.swing.JPanel {
    int specimens;
    double min;
    double max;
    double epsilon;
    int numberOfGenerations;
    Population population;
    MathFunction fnc;
    
    private LinkedList<String> variableNames;
    private final XYSeries series = new XYSeries("Wykres postępu");
    
    private LinkedList<String> findEveryVariable(String func){
        LinkedList<String> allMatches = new LinkedList<String>();
        Matcher m = Pattern
                        .compile("[a-z]+", CASE_INSENSITIVE)
                        .matcher(func);
        
        LinkedList<String> builtInFunctions = new LinkedList<String>(Arrays.asList(
            "e", "pi", "abs", "acos", "asin", "atan",
                "cbrt", "ceil", "cos", "cosh", "exp",
                "floor", "log", "log2", "log10", "sin",
                "sinh", "sqrt", "tan", "tanh"
        ));
        
        while(m.find()){
            String fVar = m.group();
            if(builtInFunctions.indexOf(fVar) == -1 && allMatches.indexOf(fVar) == -1)
                allMatches.add(fVar);
        }
        
        return allMatches;
    }
    
    /**
     * Creates new form Simulation
     */
    public Simulation() {
        
        progressChartBtn = new javax.swing.JButton();
        progressChartBtn.setText("Show a progress chart");
        progressChartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                progressChartBtnActionPerformed(evt);
            }
        });
         
        initComponents();
        
    }
    
    /**
     * Creates new form Simulation
     */
    public Simulation(int _specimens, double _min, double _max, double _epsilon, int _numberOfGenerations, MathFunction _fnc) {
        specimens = _specimens;
        min = _min;
        max = _max;
        epsilon = _epsilon;
        numberOfGenerations = _numberOfGenerations;
        
        fnc = _fnc;
        
        progressChartBtn = new javax.swing.JButton();
        progressChartBtn.setText("Show a progress chart");
        progressChartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                progressChartBtnActionPerformed(evt);
            }
        });

        initComponents();

    }
    
    /**
     * Start simulation
     */
    public void start(){
        population = new Population(fnc, specimens, variableNames, min, max);
        
        
        add(progressChartBtn);
        progressChartBtn.setBounds(0, 286, 1000, 1000);
        progressChartBtn.setSize(590, 28);
        
        mainScrollPane.setViewportView(population);

        
        Specimen foundSpecimen = findRoot();
        
        population.setPreferredSize(new Dimension(population.getWidth(), population.getHeight()));
        
        int width = (int)population.getPreferredSize().getWidth();
        Rectangle rect = new Rectangle(width,0,10,10);
        population.scrollRectToVisible(rect);
        
        System.out.print("Znaleziony osobnik: ");
        
        
        foundSpecimen.print();

    }

    
    /*public MathFunction getFunctionFromUser() {
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
        return new MathFunction(function);*/
        
        /*LinkedList<Double> x = new LinkedList<Double>();
        LinkedList<Double> y = new LinkedList<Double>();
        LinkedList<Double> z = new LinkedList<Double>();

        x.add(new Double(-59));
        x.add(new Double(120));
        x.add(-47.29);
        y.add(99.20);
        y.add(-103.52);
        z.add(100.3);
        z.add(33.283);
        z.add(-173.44);
        z.add(83.2);

        LinkedList<LinkedList<Double>> fnc = new LinkedList<LinkedList<Double>>();
        fnc.add(x);
        fnc.add(y);
        fnc.add(z);
        return new MathFunction(fnc);
    }*/
    
    public void setArguments(){
        specimens = Integer.parseInt(specimensArg.getText().replaceAll("\\D", ""));
        min = Integer.parseInt(minValue.getText().replaceAll("\\D", ""));
        if(minValue.getText().charAt(0) == '-')
            min *= -1;
        max = Integer.parseInt(maxValue.getText().replaceAll("\\D", ""));
        if(maxValue.getText().charAt(0) == '-')
            max *= -1;
        epsilon = Double.parseDouble(precisionArg.getText().replaceAll(",", ".").replaceAll("\\s", ""));
        numberOfGenerations = Integer.parseInt(generations.getText().replaceAll("\\D", ""));
        String userFunction = mathFunction.getText();
        ExpressionBuilder rawExpression = new ExpressionBuilder(userFunction);
        /*
        boolean flag;
        LinkedList<LinkedList<Double>> fnc = new LinkedList<LinkedList<Double>>();
        for(int i = 1; i < argumentsTable.getColumnCount(); i++){
            flag = false;
            LinkedList<Double> tmpArg = new LinkedList<Double>();
            for(int j = 0; j < argumentsTable.getRowCount(); j++){
                Object tmpObj = argumentsTableModel.getValueAt(j, i);
                if(tmpObj != null){
                    tmpArg.add(new Double(tmpObj.toString()));
                    flag = true;
                } else
                    tmpArg.add((double)0);
            }
            if(flag)
                fnc.add(tmpArg);
        }*/
        variableNames = findEveryVariable(userFunction);
        
        for(String var : variableNames)
            rawExpression.variable(var);
        
        
        fnc = new MathFunction(rawExpression.build(), variableNames);
    }

    public Specimen findRoot() {
        Specimen currentBestSpecimen = new Specimen();

        for(int i = 1; i < numberOfGenerations; i++){
            population.nextGeneration(i);
            System.out.print("Pokolenie no " + i + ", aktualny najlepszy osobnik: ");
            currentBestSpecimen = population.getBestSpecimen();
            series.add(i, currentBestSpecimen.value);
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
    
    
    public void LineChart() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        
        
        //tworzenie wykres XY
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Wykres postępu funkcji\n\r" + mathFunction.getText(),   //tytuł, mało kreatywny
                "Populacja",       //oś x
                "Wartość funkcji", //oś y
                dataset,           //dane
                PlotOrientation.VERTICAL,
                false,             //legenda
                true,
                false
        );
        
        //dodanie wykresu do okna
        ChartFrame frame1 = new ChartFrame("wykres", chart);
        frame1.setVisible(true);
        frame1.setSize(600, 500);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        minValue = new javax.swing.JFormattedTextField();
        maxValue = new javax.swing.JFormattedTextField();
        precisionArg = new javax.swing.JFormattedTextField();
        specimensArg = new javax.swing.JFormattedTextField();
        generations = new javax.swing.JFormattedTextField();
        mathFunction = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();

        setMinimumSize(new java.awt.Dimension(0, 320));
        setPreferredSize(new java.awt.Dimension(580, 320));

        mainScrollPane.setBorder(null);
        mainScrollPane.setToolTipText("");
        mainScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        mainScrollPane.setMinimumSize(new java.awt.Dimension(6, 260));
        mainScrollPane.setOpaque(false);
        mainScrollPane.setPreferredSize(new java.awt.Dimension(560, 260));

        jLabel1.setFont(new java.awt.Font("Open Sans", 1, 24)); // NOI18N
        jLabel1.setText("Simulation settings");

        jLabel2.setFont(new java.awt.Font("Open Sans", 0, 13)); // NOI18N
        jLabel2.setText("Variable's min value:");
        jLabel2.setToolTipText("Minimum value of variable that we are looking for");

        jLabel3.setFont(new java.awt.Font("Open Sans", 0, 13)); // NOI18N
        jLabel3.setText("Variable's max value:");
        jLabel3.setToolTipText("Maximum value of variable that we are looking for");

        jLabel4.setFont(new java.awt.Font("Open Sans", 0, 13)); // NOI18N
        jLabel4.setText("Result's precision:");
        jLabel4.setToolTipText("Maximum value of variable that we are looking for");

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

        jLabel5.setFont(new java.awt.Font("Open Sans", 0, 13)); // NOI18N
        jLabel5.setText("Max generations:");
        jLabel5.setToolTipText("Maximum number of generations");

        jLabel6.setFont(new java.awt.Font("Open Sans", 0, 13)); // NOI18N
        jLabel6.setText("Number of specimens:");
        jLabel6.setToolTipText("Maximum number of generations");

        minValue.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        minValue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        minValue.setText("-10000");
        minValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minValueActionPerformed(evt);
            }
        });

        maxValue.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        maxValue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        maxValue.setText("10000");
        maxValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxValueActionPerformed(evt);
            }
        });

        precisionArg.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.#######"))));
        precisionArg.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        precisionArg.setText("0,0005");
        precisionArg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precisionArgActionPerformed(evt);
            }
        });

        specimensArg.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        specimensArg.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        specimensArg.setText("1000");
        specimensArg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specimensArgActionPerformed(evt);
            }
        });

        generations.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        generations.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        generations.setText("1000");
        generations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generationsActionPerformed(evt);
            }
        });

        mathFunction.setText("x^2 + 7 * x - 3 * sin(2 * x) - 3 * y^3 + cos(y / 2)");
        mathFunction.setMargin(new java.awt.Insets(2, 10, 2, 10));
        mathFunction.setMaximumSize(new java.awt.Dimension(510, 2147483647));
        mathFunction.setMinimumSize(new java.awt.Dimension(510, 20));
        mathFunction.setName(""); // NOI18N
        mathFunction.setPreferredSize(new java.awt.Dimension(510, 20));
        mathFunction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mathFunctionActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Open Sans", 1, 24)); // NOI18N
        jLabel7.setText("Math function");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(specimensArg)
                                    .addComponent(generations))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(precisionArg, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maxValue, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(minValue, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mathFunction, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(minValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(maxValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specimensArg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(precisionArg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mathFunction, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        mainScrollPane.setViewportView(jPanel1);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setMinimumSize(new java.awt.Dimension(82, 34));
        jToolBar1.setPreferredSize(new java.awt.Dimension(100, 34));

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
                .addComponent(mainScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void generationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generationsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_generationsActionPerformed

    private void specimensArgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specimensArgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_specimensArgActionPerformed

    private void precisionArgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precisionArgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precisionArgActionPerformed

    private void maxValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maxValueActionPerformed

    private void minValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minValueActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        setArguments();
        start();
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void mathFunctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mathFunctionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mathFunctionActionPerformed
    
    private void progressChartBtnActionPerformed(java.awt.event.ActionEvent evt) {                                               
        LineChart();
    }
    
    
    private javax.swing.JButton progressChartBtn;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField generations;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JScrollPane mainScrollPane;
    private javax.swing.JTextField mathFunction;
    private javax.swing.JFormattedTextField maxValue;
    private javax.swing.JFormattedTextField minValue;
    private javax.swing.JFormattedTextField precisionArg;
    private javax.swing.JFormattedTextField specimensArg;
    // End of variables declaration//GEN-END:variables
}
