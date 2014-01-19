package neuralNetworkLibrary;

public class TrainingHelper {

	private NeuralNet neuralNet;
	private TrainingDataSet trainingDataSet;
	private TrainingDataSet mseTrainingDataSet;
	private double learningRate;
	private double momentum;
	private int epoch;
	private double epochMSE;

	private OnEpochFinishListener epochFinishListener;
	private OnItemTrainListener itemTrainListener;

	private boolean stopTrainingFlag;
	private boolean datasetShuffle;

	public TrainingHelper(NeuralNet neuralNet, TrainingDataSet trainingDataSet,
			TrainingDataSet mseTrainingDataSet, double learningRate,
			double momentum) {
		this.neuralNet = neuralNet;
		this.trainingDataSet = trainingDataSet;
		this.mseTrainingDataSet = mseTrainingDataSet;
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.epoch = 1;
		this.datasetShuffle = true;
	}

	public TrainingHelper(NeuralNet neuralNet, TrainingDataSet trainingDataSet,
			TrainingDataSet mseTrainingDataSet) {
		this.neuralNet = neuralNet;
		this.trainingDataSet = trainingDataSet;
		this.mseTrainingDataSet = mseTrainingDataSet;
		this.learningRate = 0.1;
		this.momentum = 0;
		this.epoch = 1;
		this.datasetShuffle = true;
	}

	public TrainingHelper(NeuralNet neuralNet, TrainingDataSet trainingDataSet,
			double learningRate, double momentum) {
		this.neuralNet = neuralNet;
		this.trainingDataSet = trainingDataSet;
		this.mseTrainingDataSet = null;
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.epoch = 1;
		this.datasetShuffle = true;
	}

	public TrainingHelper(NeuralNet neuralNet, TrainingDataSet trainingDataSet) {
		super();
		this.neuralNet = neuralNet;
		this.trainingDataSet = trainingDataSet;
		this.mseTrainingDataSet = null;
		this.learningRate = 0.1;
		this.momentum = 0;
		this.epoch = 1;
		this.datasetShuffle = true;
	}

	public void train() {
		stopTrainingFlag = false;
		while (!stopTrainingFlag) {
			if (datasetShuffle)
				trainingDataSet.shuffle();

			int ind = 1;
			for (TrainingDataItem trainingDataItem : trainingDataSet) {
				neuralNet.train(trainingDataItem.getInputVector(),
						trainingDataItem.getExpectedOutputVector(),
						learningRate);

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

	private void updateEpochMSE(){
		epochMSE = 0;
		if(mseTrainingDataSet==null)return;
		for (TrainingDataItem trainingDataItem : mseTrainingDataSet) {
			double[] outputVector = neuralNet.run(trainingDataItem.getInputVector());
			
			epochMSE += calculateMSE(trainingDataItem.getExpectedOutputVector(), outputVector); 
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
		return mse / 2.0;
	}

	public void stopTraining() {
		stopTrainingFlag = true;
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
	 * @return the learningRate
	 */
	public double getLearningRate() {
		return learningRate;
	}

	/**
	 * @param learningRate
	 *            the learningRate to set
	 */
	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	/**
	 * @return the momentum
	 */
	public double getMomentum() {
		return momentum;
	}

	/**
	 * @param momentum
	 *            the momentum to set
	 */
	public void setMomentum(double momentum) {
		this.momentum = momentum;
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

}
