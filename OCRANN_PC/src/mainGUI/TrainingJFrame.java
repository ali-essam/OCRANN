/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainGUI;

import java.awt.BasicStroke;
import java.awt.FlowLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import mnistDataset.MNISTDatasetLoader;
import mnistDataset.OnDataitemLoadListener;
import neuralNetworkLibrary.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Ali
 */
public class TrainingJFrame extends javax.swing.JFrame {

    class TrainingPublishable {

        NeuralNet neuralNet;
        int trainingDataItemIndex;
        double MSE;
        int epochNum;

        public TrainingPublishable(int trainingDataItemIndex) {
            this.trainingDataItemIndex = trainingDataItemIndex;
            neuralNet = null;
        }

        public TrainingPublishable(NeuralNet neuralNet, int epochNum, double MSE) {
            this.neuralNet = neuralNet;
            this.epochNum = epochNum;
            this.MSE = MSE;
            trainingDataItemIndex = -1;
        }
    }

    private ChartPanel chartPanel;
    private XYSeries series;
    private JFreeChart chart;
    private XYSeriesCollection seriesCollection;

    private TrainingDataSet trainingDataSet;
    private NeuralNet neuralNet;
    private TrainingHelper trainingHelper;
    private LearningParameters learningParameters;

    int i = 0;
    int epochNumber = 0;

    private void initLineChartPanel() {
        series = new XYSeries("MSE");
        seriesCollection = new XYSeriesCollection(series);

        chart = ChartFactory.createXYLineChart("Training", "Epoch Number", "MSE", seriesCollection, PlotOrientation.VERTICAL, true, true, true);
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
        renderer.setBaseStroke(new BasicStroke(3));
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setRenderer(renderer);
        
        chartPanel = new ChartPanel(chart);
        chartPanel.setSize(chartContainerPanel.getWidth(), chartContainerPanel.getHeight());

        
        
        chartContainerPanel.add(chartPanel);
    }

    private void add(int i, double val) {
        series.add(i, val);
    }

    private void updateLearningParameters() {
        learningParameters.setLearningRate(Double.parseDouble(learningRateText.getText()));
        learningParameters.setMomentum(Double.parseDouble(momentumText.getText()));
        learningParameters.setWeightDecay(Double.parseDouble(weightDecayText.getText()));
    }

    private void saveNetwork(final NeuralNet net, final String filename) {
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {

                try {
                    // Serialize data object to a file
                    ObjectOutputStream out = new ObjectOutputStream(
                            new FileOutputStream(filename));
                    out.writeObject(net);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
            
            @Override
            protected void done(){
                System.out.println(filename + " saved");
            }
        };
        worker.execute();
        System.out.println("Saving file " + filename);
    }

    private NeuralNet loadNetwork(String filename) {
        NeuralNet net = null;
        try {
            FileInputStream door = new FileInputStream(filename);
            ObjectInputStream reader = new ObjectInputStream(door);
            net = (NeuralNet) reader.readObject();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return net;
    }

    private void addMSE(double mse, int epochNumber) {
        epochNumLabel.setText(epochNumber + "");
        lastMSELabel.setText(mse + "");
        series.add(epochNumber++, mse);
    }

    private void saveNetwork(NeuralNet net, int epochNum) {
        String netName = netFileNameText.getText() + epochNum;
        saveNetwork(net, netName);
    }

    private void epochFinished(NeuralNet net, int epochNum, double mse) {
        if (autoSaveNetCheck.isSelected()) {
            addMSE(mse,epochNum);
            saveNetwork(net, epochNum);
        }
    }

    /**
     * Creates new form TrainingJFrame
     */
    public TrainingJFrame() {
        initComponents();
        learningParameters = new LearningParameters(0.001);
        initLineChartPanel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        chartContainerPanel = new javax.swing.JPanel();
        loadNetPanel = new javax.swing.JPanel();
        netFileNameText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        autoSaveNetCheck = new javax.swing.JCheckBox();
        saveNetButton = new javax.swing.JButton();
        loadNetButton = new javax.swing.JButton();
        newNetButton = new javax.swing.JButton();
        learningParamsPanel = new javax.swing.JPanel();
        learningRateText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        momentumText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        weightDecayText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        changeLearningParamsButton = new javax.swing.JButton();
        statusPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        epochNumLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lastMSELabel = new javax.swing.JLabel();
        epochProgressBar = new javax.swing.JProgressBar();
        jLabel10 = new javax.swing.JLabel();
        startTrainButton = new javax.swing.JButton();
        stopTrainButton = new javax.swing.JButton();
        mnistPanel = new javax.swing.JPanel();
        loadMNISTButton = new javax.swing.JButton();
        mnistProgressBar = new javax.swing.JProgressBar();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MNIST Training");

        chartContainerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        chartContainerPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                chartPanelResizeHandler(evt);
            }
        });

        javax.swing.GroupLayout chartContainerPanelLayout = new javax.swing.GroupLayout(chartContainerPanel);
        chartContainerPanel.setLayout(chartContainerPanelLayout);
        chartContainerPanelLayout.setHorizontalGroup(
            chartContainerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chartContainerPanelLayout.setVerticalGroup(
            chartContainerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
        );

        loadNetPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        loadNetPanel.setEnabled(false);
        loadNetPanel.setPreferredSize(new java.awt.Dimension(180, 114));

        netFileNameText.setText("nets/net");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, loadNetPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), netFileNameText, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        netFileNameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                netFileNameTextActionPerformed(evt);
            }
        });

        jLabel1.setText("Network File Path");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, loadNetPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), jLabel1, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        autoSaveNetCheck.setSelected(true);
        autoSaveNetCheck.setText("Auto Save");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, loadNetPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), autoSaveNetCheck, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        autoSaveNetCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoSaveNetCheckActionPerformed(evt);
            }
        });

        saveNetButton.setText("Save Network");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, loadNetPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), saveNetButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        saveNetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveNetButtonActionPerformed(evt);
            }
        });

        loadNetButton.setText("Load Network");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, loadNetPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), loadNetButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        loadNetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadNetButtonActionPerformed(evt);
            }
        });

        newNetButton.setText("New Network");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, loadNetPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), newNetButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        newNetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newNetButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loadNetPanelLayout = new javax.swing.GroupLayout(loadNetPanel);
        loadNetPanel.setLayout(loadNetPanelLayout);
        loadNetPanelLayout.setHorizontalGroup(
            loadNetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadNetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loadNetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loadNetPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(netFileNameText, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                    .addGroup(loadNetPanelLayout.createSequentialGroup()
                        .addComponent(saveNetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(autoSaveNetCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(loadNetButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newNetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        loadNetPanelLayout.setVerticalGroup(
            loadNetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadNetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loadNetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(netFileNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loadNetButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(newNetButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(loadNetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(autoSaveNetCheck)
                    .addComponent(saveNetButton))
                .addGap(31, 31, 31))
        );

        learningParamsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        learningParamsPanel.setEnabled(false);

        learningRateText.setText("0.001");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, learningParamsPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), learningRateText, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        learningRateText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                learningRateTextActionPerformed(evt);
            }
        });

        jLabel2.setText("Learning Rate");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, learningParamsPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), jLabel2, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        momentumText.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, learningParamsPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), momentumText, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jLabel3.setText("Momentum");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, learningParamsPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), jLabel3, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        weightDecayText.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, learningParamsPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), weightDecayText, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        weightDecayText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weightDecayTextActionPerformed(evt);
            }
        });

        jLabel4.setText("Weight Decay");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, learningParamsPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), jLabel4, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        changeLearningParamsButton.setText("Change Learning Parameters");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, learningParamsPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), changeLearningParamsButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        changeLearningParamsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeLearningParamsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout learningParamsPanelLayout = new javax.swing.GroupLayout(learningParamsPanel);
        learningParamsPanel.setLayout(learningParamsPanelLayout);
        learningParamsPanelLayout.setHorizontalGroup(
            learningParamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, learningParamsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(learningParamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(changeLearningParamsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(learningParamsPanelLayout.createSequentialGroup()
                        .addGroup(learningParamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(learningParamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(learningParamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(momentumText, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(learningRateText, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(weightDecayText))))
                .addContainerGap())
        );
        learningParamsPanelLayout.setVerticalGroup(
            learningParamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(learningParamsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(learningParamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(learningRateText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(learningParamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(momentumText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(learningParamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weightDecayText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(changeLearningParamsButton)
                .addContainerGap())
        );

        statusPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        statusPanel.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, statusPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), statusPanel, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jLabel5.setText("Epoch Number: ");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, statusPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), jLabel5, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        epochNumLabel.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, statusPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), epochNumLabel, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jLabel8.setText("Last MSE: ");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, statusPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), jLabel8, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        lastMSELabel.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, statusPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), lastMSELabel, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        epochProgressBar.setStringPainted(true);

        jLabel10.setText("Epoch Progress");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, statusPanel, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), jLabel10, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        startTrainButton.setText("Start Training");
        startTrainButton.setEnabled(false);
        startTrainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTrainButtonActionPerformed(evt);
            }
        });

        stopTrainButton.setText("Stop Training");
        stopTrainButton.setEnabled(false);
        stopTrainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopTrainButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(epochProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(statusPanelLayout.createSequentialGroup()
                                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lastMSELabel)
                                    .addComponent(epochNumLabel)))
                            .addComponent(jLabel10))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(startTrainButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stopTrainButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(epochNumLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lastMSELabel))
                .addGap(4, 4, 4)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(epochProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startTrainButton)
                    .addComponent(stopTrainButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mnistPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        loadMNISTButton.setText("Load MNIST");
        loadMNISTButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMNISTButtonActionPerformed(evt);
            }
        });

        mnistProgressBar.setStringPainted(true);

        jLabel7.setText("Progress");

        javax.swing.GroupLayout mnistPanelLayout = new javax.swing.GroupLayout(mnistPanel);
        mnistPanel.setLayout(mnistPanelLayout);
        mnistPanelLayout.setHorizontalGroup(
            mnistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mnistPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mnistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loadMNISTButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(mnistProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(mnistPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mnistPanelLayout.setVerticalGroup(
            mnistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mnistPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(loadMNISTButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mnistProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chartContainerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mnistPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loadNetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(learningParamsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chartContainerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(learningParamsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loadNetPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(statusPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mnistPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void autoSaveNetCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoSaveNetCheckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_autoSaveNetCheckActionPerformed

    private void netFileNameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_netFileNameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_netFileNameTextActionPerformed

    private void saveNetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveNetButtonActionPerformed
        saveNetwork(neuralNet, trainingHelper.getEpoch());
    }//GEN-LAST:event_saveNetButtonActionPerformed

    private void learningRateTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_learningRateTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_learningRateTextActionPerformed

    private void weightDecayTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weightDecayTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_weightDecayTextActionPerformed

    private void startTrainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTrainButtonActionPerformed

        series.clear();
        stopTrainButton.setEnabled(true);
        startTrainButton.setEnabled(false);
        
        trainingHelper = new TrainingHelper(neuralNet, trainingDataSet, trainingDataSet.subDataSet(0, 200), learningParameters);
        epochProgressBar.setValue(0);
        epochProgressBar.setMaximum(trainingDataSet.size());

        SwingWorker<Boolean, TrainingPublishable> worker = new SwingWorker<Boolean, TrainingPublishable>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                trainingHelper.setOnItemTrainListener(new OnItemTrainListener() {
                    @Override
                    public void onItemTrain(int trainingDataItemIndex) {
                        publish(new TrainingPublishable(trainingDataItemIndex));
                    }
                });
                trainingHelper.setOnEpochFinishListner(new OnEpochFinishListener() {

                    @Override
                    public void onEpochFinish() {
                        publish(new TrainingPublishable(neuralNet.deepClone(), trainingHelper.getEpoch(), trainingHelper.getEpochMSE()));
                    }
                });
                trainingHelper.train();
                return true;
            }

            @Override
            protected void done() {

            }

            @Override
            protected void process(List<TrainingPublishable> chunks) {
                for (TrainingPublishable chunk : chunks) {
                    if (chunk.neuralNet == null) {
                        if (chunk.trainingDataItemIndex != -1) {
                            epochProgressBar.setValue(chunk.trainingDataItemIndex);
                            epochProgressBar.setString(chunk.trainingDataItemIndex + " of 60000");
                        }
                    } else {
                        epochFinished(chunk.neuralNet, chunk.epochNum, chunk.MSE);
                    }
                }
            }
        };
        worker.execute();
    }//GEN-LAST:event_startTrainButtonActionPerformed

    private void changeLearningParamsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeLearningParamsButtonActionPerformed
        updateLearningParameters();
    }//GEN-LAST:event_changeLearningParamsButtonActionPerformed

    private void chartPanelResizeHandler(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_chartPanelResizeHandler
        chartPanel.setSize(chartContainerPanel.getWidth(), chartContainerPanel.getHeight());
    }//GEN-LAST:event_chartPanelResizeHandler

    private void loadMNISTButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMNISTButtonActionPerformed
        mnistProgressBar.setMaximum(60000);

        SwingWorker<TrainingDataSet, Integer> worker = new SwingWorker<TrainingDataSet, Integer>() {
            @Override
            protected TrainingDataSet doInBackground() throws Exception {
                MNISTDatasetLoader.setOnDataitemLoadListener(new OnDataitemLoadListener() {
                    @Override
                    public void onDataitemLoadListener(int currentProgress) {
                        publish(currentProgress);
                    }
                });
                return MNISTDatasetLoader.loadMNISTDataset("MNIST/train-images.idx3-ubyte", "MNIST/train-labels.idx1-ubyte");
            }

            @Override
            protected void process(List<Integer> chunks) {
                mnistProgressBar.setValue(chunks.get(chunks.size() - 1));
                mnistProgressBar.setString(chunks.get(chunks.size() - 1) + " of 60000");
            }

            protected void done() {
                try {
                    trainingDataSet = get();
                    loadNetPanel.setEnabled(true);
                    System.out.println("MNIST Dataset Loaded");
                } catch (InterruptedException ex) {
                    Logger.getLogger(TrainingJFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(TrainingJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        worker.execute();
    }//GEN-LAST:event_loadMNISTButtonActionPerformed

    private void newNetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newNetButtonActionPerformed
        neuralNet = new NeuralNet(learningParameters);
        neuralNet.addLayer(new InputLayer(784));
        neuralNet.addLayer(new TanhLayer(330));
        neuralNet.addLayer(new TanhLayer(10));
        neuralNet.connectAllLayers();
        learningParamsPanel.setEnabled(true);
        
        System.out.println("Created New Network");
        
        statusPanel.setEnabled(true);
        startTrainButton.setEnabled(true);
    }//GEN-LAST:event_newNetButtonActionPerformed

    private void stopTrainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopTrainButtonActionPerformed
        trainingHelper.stopTraining();
        startTrainButton.setEnabled(true);
        stopTrainButton.setEnabled(false);
    }//GEN-LAST:event_stopTrainButtonActionPerformed

    private void loadNetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadNetButtonActionPerformed
        String netName = netFileNameText.getText();
        neuralNet = loadNetwork(netName);
        System.out.println("Network Loaded");
        learningParamsPanel.setEnabled(true);
        statusPanel.setEnabled(true);
        startTrainButton.setEnabled(true);
    }//GEN-LAST:event_loadNetButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TrainingJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrainingJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrainingJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrainingJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrainingJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox autoSaveNetCheck;
    private javax.swing.JButton changeLearningParamsButton;
    private javax.swing.JPanel chartContainerPanel;
    private javax.swing.JLabel epochNumLabel;
    private javax.swing.JProgressBar epochProgressBar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lastMSELabel;
    private javax.swing.JPanel learningParamsPanel;
    private javax.swing.JTextField learningRateText;
    private javax.swing.JButton loadMNISTButton;
    private javax.swing.JButton loadNetButton;
    private javax.swing.JPanel loadNetPanel;
    private javax.swing.JPanel mnistPanel;
    private javax.swing.JProgressBar mnistProgressBar;
    private javax.swing.JTextField momentumText;
    private javax.swing.JTextField netFileNameText;
    private javax.swing.JButton newNetButton;
    private javax.swing.JButton saveNetButton;
    private javax.swing.JButton startTrainButton;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton stopTrainButton;
    private javax.swing.JTextField weightDecayText;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
