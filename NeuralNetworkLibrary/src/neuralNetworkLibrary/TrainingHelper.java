package neuralNetworkLibrary;

public class TrainingHelper {

	private NeuralNet neuralNet;
	private TrainingDataSet trainingDataSet;
	private TrainingDataSet mseTrainingDataSet;
	private LearningParameters learningParameters;
	private int epoch;
	private double epochMSE;

	private OnEpochFinishListener epochFinishListener;
	private OnItemTrainListener itemTrainListener;

	private boolean stopTrainingFlag;
	private boolean datasetShuffle;
	
	private double[] inputVector;
	private double[] expectedOutputVector;

	public TrainingHelper(NeuralNet neuralNet, TrainingDataSet trainingDataSet,
			TrainingDataSet mseTrainingDataSet,
			LearningParameters learningParameters) {
		this.neuralNet = neuralNet;
		this.trainingDataSet = trainingDataSet;
		this.mseTrainingDataSet = mseTrainingDataSet;
		this.learningParameters = learningParameters;
		this.epoch = 1;
		this.datasetShuffle = true;
	}

	public TrainingHelper(NeuralNet neuralNet, TrainingDataSet trainingDataSet,
			TrainingDataSet mseTrainingDataSet) {
		this.neuralNet = neuralNet;
		this.trainingDataSet = trainingDataSet;
		this.mseTrainingDataSet = mseTrainingDataSet;
		this.learningParameters = new LearningParameters();
		this.epoch = 1;
		this.datasetShuffle = true;
	}

	public TrainingHelper(NeuralNet neuralNet, TrainingDataSet trainingDataSet,
			LearningParameters learningParameters) {
		this.neuralNet = neuralNet;
		this.trainingDataSet = trainingDataSet;
		this.mseTrainingDataSet = null;
		this.learningParameters = learningParameters;
		this.epoch = 1;
		this.datasetShuffle = true;
	}

	public TrainingHelper(NeuralNet neuralNet, TrainingDataSet trainingDataSet) {
		super();
		this.neuralNet = neuralNet;
		this.trainingDataSet = trainingDataSet;
		this.mseTrainingDataSet = null;
		this.learningParameters = new LearningParameters();
		this.epoch = 1;
		this.datasetShuffle = true;
	}

	private void copyDataitemToArray(TrainingDataItem dataItem,
			double[] inputVector, double[] expectedOutputVector) {
		for (int i = 0; i < dataItem.getInputVector().size(); i++) {
			inputVector[i] = ((Number)dataItem.getInputVector().get(i)).doubleValue();
		}

		for (int i = 0; i < dataItem.getExpectedOutputVector().size(); i++) {
			expectedOutputVector[i] = (Double) dataItem
					.getExpectedOutputVector().get(i);
		}
	}

	public void train() {
		stopTrainingFlag = false;
		while (!stopTrainingFlag) {
			if (datasetShuffle)
				trainingDataSet.shuffle();

			int ind = 1;
			inputVector = new double[neuralNet.getInputLayer()
					.getNeuronCount()];
			expectedOutputVector = new double[neuralNet
					.getOutputLayer().getNeuronCount()];
			for (TrainingDataItem trainingDataItem : trainingDataSet) {

				copyDataitemToArray(trainingDataItem, inputVector,
						expectedOutputVector);
				neuralNet.train(inputVector, expectedOutputVector);

				if (itemTrainListener != null)
					itemTrainListener.onItemTrain(ind);

				if (stopTrainingFlag)
					break;

				ind++;
			}
			if (stopTrainingFlag)
				break;

			updateEpochMSE();
			if (epochFinishListener != null)
				epochFinishListener.onEpochFinish();

			epoch++;
		}
	}

	private void updateEpochMSE() {
		epochMSE = 0;
		if (mseTrainingDataSet == null)
			return;
		for (TrainingDataItem trainingDataItem : mseTrainingDataSet) {
			copyDataitemToArray(trainingDataItem, inputVector,
					expectedOutputVector);
			
			double[] outputVector = neuralNet.run(inputVector);

			epochMSE += calculateMSE(
					expectedOutputVector, outputVector);
		}
		epochMSE /= mseTrainingDataSet.size();
	}

	private double calculateMSE(double[] expectedOutputVector,
			double[] outputVector) {
		double mse = 0;
		double error;
		for (int i = 0; i < outputVector.length; i++) {
			error = outputVector[i] - expectedOutputVector[i];
			mse += error * error;
		}
		return (mse/outputVector.length) / 2.0;
	}

	public void stopTraining() {
		stopTrainingFlag = true;
	}

	public void setLearningRate(double learningRate) {
		neuralNet.getLearningParameters().setLearningRate(learningRate);
	}

	/**
	 * @return the neuralNet
	 */
	public NeuralNet getNeuralNet() {
		return neuralNet;
	}

	/**
	 * @param neuralNet
	 *            the neuralNet to set
	 */
	public void setNeuralNet(NeuralNet neuralNet) {
		this.neuralNet = neuralNet;
	}

	/**
	 * @return the trainingDataSet
	 */
	public TrainingDataSet getTrainingDataSet() {
		return trainingDataSet;
	}

	/**
	 * @param trainingDataSet
	 *            the trainingDataSet to set
	 */
	public void setTrainingDataSet(TrainingDataSet trainingDataSet) {
		this.trainingDataSet = trainingDataSet;
	}

	/**
	 * @return the mseTrainingDataSet
	 */
	public TrainingDataSet getMseTrainingDataSet() {
		return mseTrainingDataSet;
	}

	/**
	 * @param mseTrainingDataSet
	 *            the mseTrainingDataSet to set
	 */
	public void setMseTrainingDataSet(TrainingDataSet mseTrainingDataSet) {
		this.mseTrainingDataSet = mseTrainingDataSet;
	}

	/**
	 * @return the epoch
	 */
	public int getEpoch() {
		return epoch;
	}

	/**
	 * @param epoch
	 *            the epoch to set
	 */
	public void setEpoch(int epoch) {
		this.epoch = epoch;
	}

	/**
	 * @return the epochFinishListener
	 */
	public OnEpochFinishListener getOnEpochFinishListner() {
		return epochFinishListener;
	}

	/**
	 * @param epochFinishListener
	 *            the epochFinishListener to set
	 */
	public void setOnEpochFinishListner(OnEpochFinishListener epochFinishListner) {
		this.epochFinishListener = epochFinishListner;
	}

	/**
	 * @return the itemTrainListener
	 */
	public OnItemTrainListener getOnItemTrainListener() {
		return itemTrainListener;
	}

	/**
	 * @param itemTrainListener
	 *            the itemTrainListener to set
	 */
	public void setOnItemTrainListener(OnItemTrainListener itemTrainListener) {
		this.itemTrainListener = itemTrainListener;
	}

	/**
	 * @return the datasetShuffle state
	 */
	public boolean isDatasetShuffleOn() {
		return datasetShuffle;
	}

	/**
	 * @param datasetShuffle
	 *            the datasetShuffle to set
	 */
	public void setDatasetShuffle(boolean datasetShuffle) {
		this.datasetShuffle = datasetShuffle;
	}

	/**
	 * @return the epochMSE
	 */
	public double getEpochMSE() {
		return epochMSE;
	}

	public LearningParameters getLearningParameters() {
		return learningParameters;
	}

	public void setLearningParameters(LearningParameters learningParameters) {
		this.learningParameters = learningParameters;
	}
}
